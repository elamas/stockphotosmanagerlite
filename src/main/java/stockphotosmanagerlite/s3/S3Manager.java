package stockphotosmanagerlite.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
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
import stockphotosmanagerlite.model.Photo;
import stockphotosmanagerlite.util.Constants;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

//https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/s3-examples.html
public class S3Manager {
	
	String bucket;
	String pendingImagesFolder;//folder en el s3
	S3Client s3;
	SdkHttpClient httpClient;
	
	public S3Manager(String bucket, String pendingImagesFolder) {
		 this.bucket = bucket;
		 this.pendingImagesFolder = pendingImagesFolder;
		 
		 System.err.println("[S3Manager - constructor]bucket: " + bucket);
		 System.err.println("[S3Manager - constructor]pendingImagesFolder: " + pendingImagesFolder);
		 
		 httpClient = ApacheHttpClient.builder()
                 .maxConnections(50)
                 .build();		 
		 s3 = S3Client.builder().httpClient(httpClient).region(Constants.AWS_REGION).build();
	}
	
	public List<S3Object> getS3Objects() throws Exception {
		System.err.println("[S3Manager - getS3Objects]Begin");
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(bucket)
                .prefix(pendingImagesFolder)
                .build();

        ListObjectsResponse res = s3.listObjects(listObjects);
        List<S3Object> objects = res.contents();
        List<S3Object> returnObjects = new ArrayList<S3Object>();
        for (ListIterator<S3Object> it = objects.listIterator(); it.hasNext(); ) {
            S3Object s3Object = it.next();
            if (s3Object.key().toLowerCase().endsWith(".jpg")) {//para que no entre con la carpeta, que viene en el listado
                System.err.println("[S3Manager - processImages]s3Object.key(): " + s3Object.key());
                returnObjects.add(s3Object);
            }
        }
        System.err.println("[S3Manager - getS3Objects]End");
        return returnObjects;
	}
	
	//descargamos la imagen a /tmp/dvdxx-nombre.jpg
	public String downloadImage(String key) {
		System.err.println("[S3Manager - downloadImage]Begin");
		String path = "/tmp/" + key.replaceAll("/", "-");
		System.err.println("[S3Manager - downloadImage]path: " + path);
		File outputFile = new File(path);
	    if (outputFile.exists()) {
	    	outputFile.delete();
	    }
		s3.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build(),
		        ResponseTransformer.toFile(outputFile));
		System.err.println("[S3Manager - downloadImage]End");
		return path;
	}
	
	public void uploadThumb(String thumbPath, String key) {
		System.err.println("[S3Manager - uploadThumb]Begin");
		System.err.println("[S3Manager - uploadThumb]thumbPath: " + thumbPath);
		System.err.println("[S3Manager - uploadThumb]key: " + key);
		s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                .build(),
                RequestBody.fromFile(new File(thumbPath)));
		System.err.println("[S3Manager - uploadThumb]End");
	}
	
	private void deleteObject(String key) {
		System.err.println("[S3Manager - deleteObject]Begin");
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
		s3.deleteObject(deleteObjectRequest);
		System.err.println("[S3Manager - deleteObject]End");
	}

	@Override
	protected void finalize() throws Throwable {
		httpClient.close();
	}
	
	

}
