package stockphotosmanagerlite.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import stockphotosmanagerlite.image.ThumbManager;
import stockphotosmanagerlite.util.Constants;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;

//https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/s3-examples.html
public class S3Manager {
	
	String bucket;
	S3Client s3;
	
	public S3Manager(String bucket) {
		 this.bucket = bucket;
		 s3 = S3Client.builder().region(Constants.AWS_REGION).build();
	}
	
	//TODO
	public void processImages() throws Exception {
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(bucket)
                .build();

        ListObjectsResponse res = s3.listObjects(listObjects);
        List<S3Object> objects = res.contents();

        for (ListIterator<S3Object> it = objects.listIterator(); it.hasNext(); ) {
            S3Object s3Object = it.next();
            System.err.println("[S3Manager - processImages]s3Object.key(): " + s3Object.key());
            String imagePath = downloadImage(s3Object.key());
            String thumbPath = null;//TODO
            ThumbManager.generateThumb(imagePath, thumbPath, Constants.THUMB_MAX_WIDTH, Constants.THUMB_MAX_HEIGHT);
            String thumbKey = null;//TODO
            uploadThumb(thumbPath, thumbKey);
            deleteObject(s3Object.key());
        }
	}
	
	//TODO
	private String downloadImage(String key) {
		String path = "/tmp/" + Long.toString(System.currentTimeMillis());
		s3.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build(),
		        ResponseTransformer.toFile(new File(path)));//TODO ruta correcta
		return path;
	}
	
	//TODO
	private void uploadThumb(String thumbPath, String key) {
		s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                .build(),
                RequestBody.fromFile(new File(thumbPath)));
	}
	
	//TODO quizas no este en ese bucket
	private void deleteObject(String key) {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
		s3.deleteObject(deleteObjectRequest);
	}

}
