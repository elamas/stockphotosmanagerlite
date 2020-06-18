package stockphotosmanagerlite.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import stockphotosmanagerlite.s3.S3Manager;

public class ThumbsLambdaHandler implements RequestHandler<Map<String,String>, String> {

	@Override
	//TODO ver que retorno
	public String handleRequest(Map<String, String> input, Context context) {
		try {
			System.err.println("[ThumbsLambdaHandler - handleRequest]input:" + input);
			
			String bucket = System.getenv("bucket");
			String pendingImagesFolder = System.getenv("pendingImagesFolder");
			String thumbsFolder = System.getenv("thumbsFolder");
			
			S3Manager  s3Manager = new S3Manager(bucket, pendingImagesFolder, thumbsFolder);
			s3Manager.processImages();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
	

}
