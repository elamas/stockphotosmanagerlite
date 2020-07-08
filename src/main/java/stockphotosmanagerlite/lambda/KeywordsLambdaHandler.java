package stockphotosmanagerlite.lambda;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import stockphotosmanagerlite.image.KeywordsManager;
import stockphotosmanagerlite.model.Photo;
import stockphotosmanagerlite.s3.S3Manager;

public class KeywordsLambdaHandler implements RequestHandler<Map<String,String>, List<Photo>> {

	@Override
	public List<Photo> handleRequest(Map<String, String> input, Context context) {
		List<Photo> photos = null;
		try {
			System.err.println("[KeywordsLambdaHandler - handleRequest]input:" + input);
			
			String bucket = System.getenv("bucket");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return photos;
	}
	
	

}
