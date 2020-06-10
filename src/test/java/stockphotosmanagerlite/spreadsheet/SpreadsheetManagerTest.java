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
		photo.setComments("los commentarios");
		photo.setDateSent("la fecha de envio");
		photo.setDescEnglish("la desc english");
		photo.setDescSpanish("la desc spanish");

		//photo.setImage(Long.toString(System.currentTimeMillis()));
		URL urlImage = Thread.currentThread().getContextClassLoader().getResource("perfil.jpg");
		photo.setImage(urlImage.getPath());
		
		photo.setPath("el path");
		photo.setSentSites("los sites");
		photo.setStatus("el status");
		photo.setTagsEnglish("los tags english");
		photo.setTagsSpanish("los tags spanish");
		photo.setTitleEnglish("el title english");
		photo.setTitleSpanish("el title spanish");
		
		manager.writeRow(photo);
	}

}
