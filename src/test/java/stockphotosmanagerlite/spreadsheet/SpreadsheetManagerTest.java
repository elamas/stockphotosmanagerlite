package stockphotosmanagerlite.spreadsheet;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import stockphotosmanagerlite.model.Photo;

public class SpreadsheetManagerTest {
	
	@Test
	//pasara el test si no se lanza excepcion, no compruebo nada
	public void writeRowTest() throws Exception {
//		URL url = Thread.currentThread().getContextClassLoader().getResource("test.xlsx");
//		SpreadsheetManager manager = new SpreadsheetManager(new File(url.getPath()));
		
		//lo pongo cutre a capon para que me escriba en el /test/resources y no en el /bin/test
		SpreadsheetManager manager = new SpreadsheetManager(new File("/quique/proyectos/mios/java/stockphotosmanagerlite/src/test/resources/test.xlsx"));
		
		Photo photo = new Photo();
		//TODO rellenar el objeto Photo
		photo.setImage(Long.toString(System.currentTimeMillis()));
		
		manager.writeRow(photo);
	}

}
