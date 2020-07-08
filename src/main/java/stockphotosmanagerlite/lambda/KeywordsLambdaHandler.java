package stockphotosmanagerlite.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.s3.model.S3Object;
import stockphotosmanagerlite.image.KeywordsManager;
import stockphotosmanagerlite.model.Photo;
import stockphotosmanagerlite.s3.S3Manager;
import stockphotosmanagerlite.util.Constants;

public class KeywordsLambdaHandler implements RequestHandler<Map<String,String>, List<Photo>> {

	@Override
	public List<Photo> handleRequest(Map<String, String> input, Context context) {
		try {
			System.err.println("[KeywordsLambdaHandler - handleRequest]input:" + input);
			
			String bucket = System.getenv("bucket");
			String pendingImagesFolder = System.getenv("pendingImagesFolder");
			
			return processImages(bucket, pendingImagesFolder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<Photo> processImages(String bucket, String pendingImagesFolder) throws Exception {
		S3Manager  s3Manager = new S3Manager(bucket, pendingImagesFolder);
		List<S3Object> objects = s3Manager.getS3Objects();
		List<Photo> photos = new ArrayList<Photo>(); 
		KeywordsManager keywordsManager = new KeywordsManager();
		for (S3Object object : objects) {
            System.err.println("[KeywordsLambdaHandler - processImages]s3Object.key(): " + object.key());
            
            List<String> keywords = keywordsManager.getKeywords(bucket, object.key(), Constants.MAX_KEYWORDS);
            
            //nombre de la imagen
            String[] keySplitted = object.key().split("/");
            String imageName = keySplitted[keySplitted.length - 1];
            
            Photo photo = new Photo();
            photo.setImage(imageName);
            photo.setPath(object.key());
            photo.setTagsEnglish(String.join(",", keywords));
            photos.add(photo);
		}
		return photos;
	}

}
