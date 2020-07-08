package unit.stockphotosmanagerlite.image;

import java.net.URL;

import org.junit.Test;

import stockphotosmanagerlite.image.ThumbManager;

public class ThumbManagerTest {
	
	@Test
	public void generateThumbTestHorizontal() throws Exception {
		URL urlImage = Thread.currentThread().getContextClassLoader().getResource("horizontal.jpg");
		ThumbManager.generateThumb(urlImage.getPath(), "/quique/tmp/horizontal-scaled", 100, 75);
	}

	@Test
	public void generateThumbTestVertical() throws Exception {
		URL urlImage = Thread.currentThread().getContextClassLoader().getResource("vertical.jpg");
		ThumbManager.generateThumb(urlImage.getPath(), "/quique/tmp/vertical-scaled", 100, 75);
	}

}
