package integration.stockphotosmanagerlite.image;

import java.util.List;
import org.junit.Test;

import stockphotosmanagerlite.image.KeywordsManager;

import static org.junit.Assert.*;

public class KeywordsManagerTest {

	@Test
	public void getKeywordsTest() throws Exception {
		String bucketName = System.getenv("QQ_STOCK_BUCKET"); 
		String keyNormalized = System.getenv("QQ_STOCK_KEY");
		System.err.println("[KeywordsManagerTest - getKeywordsTest]bucketName: " + bucketName);
		System.err.println("[KeywordsManagerTest - getKeywordsTest]keyNormalized: " + keyNormalized);
		
		KeywordsManager keywordsManager = new KeywordsManager();
		List<String> keywords = keywordsManager.getKeywords(bucketName, keyNormalized, 10);
		System.err.println("[KeywordsManagerTest - getKeywordsTest]keywords: " + keywords);
		assertNotNull(keywords);
		assertTrue(keywords.size() > 0);
	}
}
