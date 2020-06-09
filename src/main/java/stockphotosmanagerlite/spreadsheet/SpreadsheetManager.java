package stockphotosmanagerlite.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import stockphotosmanagerlite.model.Photo;

public class SpreadsheetManager {
	
	File excelFile;
	
	private final static int IMAGE_CELL_NUM = 0;
	private final static int PATH_CELL_NUM = 1;
	private final static int TITLE_SPANISH_CELL_NUM = 2;
	private final static int TITLE_ENGLISH_CELL_NUM = 3;
	private final static int DESC_SPANISH_CELL_NUM = 4;
	private final static int DESC_ENGLISH_CELL_NUM = 5;
	private final static int TAGS_SPANISH_CELL_NUM = 6;
	private final static int TAGS_ENGLISH_CELL_NUM = 7;
	private final static int STATUS_CELL_NUM = 8;
	private final static int SENT_SITES_CELL_NUM = 9;
	private final static int DATE_SENT_CELL_NUM = 10;
	private final static int COMMENTS_CELL_NUM = 11;
	
	
	public SpreadsheetManager(File excelFile) {
		this.excelFile = excelFile;
	}
	
	//TODO implementar todo
	public void writeRow(Photo photo) throws Exception{
		Workbook workbook;
		try (InputStream is = new FileInputStream(excelFile)) {//es un try with resources que los cierra solo
			workbook = WorkbookFactory.create(is);
			//System.err.println("[SpreadsheetManager - writeRow]workbook.getClass(): " + workbook.getClass());
			Sheet sheet = workbook.getSheetAt(0);
			int nextRowNum = sheet.getLastRowNum() + 1;
			Row row = sheet.createRow(nextRowNum);
			
			String image = photo.getImage();
			if (image != null) {
				Cell cell = row.createCell(IMAGE_CELL_NUM);
				cell.setCellValue(image);
			}
			
			/*
			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}

			String xxx = photo.getxxx();
			if (xxx != null) {
				Cell cell = row.createCell(xxxx_CELL_NUM);
				cell.setCellValue(xxx);
			}
			*/

			/*
	private String ;
	private String path;
	private String titleSpanish;
	private String titleEnglish;
	private String descSpanih;
	private String descEnglish;
	private String tagsSpanish;
	private String tagsEnglish;
	private String status;
	private String sentSites;
	private String dateSent;
	private String comments;
			 */
			
//		    for (Row row : sheet) {
//		    	System.err.println("[SpreadsheetManager - writeRow]**** Row ****");
//		        for (Cell cell : row) {
//		        	System.err.println("[SpreadsheetManager - writeRow]cell.getStringCellValue(): " + cell.getStringCellValue());
//		        }
//		    }	
		}
		
		// Write the output to a file
		try (OutputStream fileOut = new FileOutputStream(excelFile)) {
			
			System.err.println("[SpreadsheetManager - writeRow]cexcelFile: " + excelFile);
			
			workbook.write(fileOut);
		}
	}
	
}
