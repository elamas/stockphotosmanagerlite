package stockphotosmanagerlite.lambda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.s3.model.S3Object;
import stockphotosmanagerlite.image.ThumbManager;
import stockphotosmanagerlite.model.Photo;
import stockphotosmanagerlite.s3.S3Manager;
import stockphotosmanagerlite.util.Constants;

public class ThumbsLambdaHandler implements RequestHandler<Map<String,String>, List<Photo>> {

	@Override
	public List<Photo> handleRequest(Map<String, String> input, Context context) {
		try {
			System.err.println("[ThumbsLambdaHandler - handleRequest]input:" + input);
			
			String bucket = System.getenv("bucket");
			String pendingImagesFolder = System.getenv("pendingImagesFolder");
			String thumbsFolder = System.getenv("thumbsFolder");
			
			return processImages(bucket, pendingImagesFolder, thumbsFolder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<Photo> processImages(String bucket, String pendingImagesFolder, String thumbsFolder) throws Exception {
		S3Manager  s3Manager = new S3Manager(bucket, pendingImagesFolder);;
		List<S3Object> objects = s3Manager.getS3Objects();
		List<Photo> photos = new ArrayList<Photo>(); 
		for (S3Object object : objects) {
            System.err.println("[S3Manager - processImages]s3Object.key(): " + object.key());
            
            //nombre de la imagen
            String[] keySplitted = object.key().split("/");
            String imageName = keySplitted[keySplitted.length - 1];
            
            Photo photo = new Photo();
            photo.setImage(imageName);
            photo.setPath(object.key());
            photos.add(photo);
            
            String imagePath = s3Manager.downloadImage(object.key());
            String thumbPath = imagePath.replace(".", "-thumb.");// /tmp/dvdxx-nombre-thumb.jpg
            System.err.println("[S3Manager - processImages]thumbPath: " + thumbPath);
            ThumbManager.generateThumb(imagePath, thumbPath, Constants.THUMB_MAX_WIDTH, Constants.THUMB_MAX_HEIGHT);
            System.err.println("[S3Manager - processImages]exists: " + new File(thumbPath).exists());
            String thumbKey = thumbsFolder + "/"  + imageName;
            s3Manager.uploadThumb(thumbPath, thumbKey);
		}
		return photos;
	}
	
	

}
