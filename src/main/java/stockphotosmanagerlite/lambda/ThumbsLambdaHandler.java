package stockphotosmanagerlite.lambda;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import stockphotosmanagerlite.model.Photo;
import stockphotosmanagerlite.s3.S3Manager;

public class ThumbsLambdaHandler implements RequestHandler<Map<String,String>, List<Photo>> {

	@Override
	//TODO ver que retorno
	public List<Photo> handleRequest(Map<String, String> input, Context context) {
		List<Photo> photos = null;
		try {
			System.err.println("[ThumbsLambdaHandler - handleRequest]input:" + input);
			
			String bucket = System.getenv("bucket");
			String pendingImagesFolder = System.getenv("pendingImagesFolder");
			String thumbsFolder = System.getenv("thumbsFolder");
			
			S3Manager  s3Manager = new S3Manager(bucket, pendingImagesFolder, thumbsFolder);
			photos = s3Manager.processImages();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return photos;
	}
	
	

}
