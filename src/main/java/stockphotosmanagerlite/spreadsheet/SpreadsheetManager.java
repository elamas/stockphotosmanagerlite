package stockphotosmanagerlite.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;

import stockphotosmanagerlite.model.Photo;

//me baso en https://poi.apache.org/components/spreadsheet/quick-guide.html
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
	
	public void writeRow(Photo photo) throws Exception{
		Workbook workbook;
		try (InputStream is = new FileInputStream(excelFile)) {//es un try with resources que los cierra solo
			workbook = WorkbookFactory.create(is);
			//System.err.println("[SpreadsheetManager - writeRow]workbook.getClass(): " + workbook.getClass());
			Sheet sheet = workbook.getSheetAt(0);
			int nextRowNum = sheet.getLastRowNum() + 1;
			Row row = sheet.createRow(nextRowNum);
			row.setHeightInPoints(100);
			
			String image = photo.getImage();
			if (image != null) {
				Cell cell = row.createCell(IMAGE_CELL_NUM);
				sheet.setColumnWidth(IMAGE_CELL_NUM, 20*256);//prueba y error. "Set the width (in units of 1/256th of a character width)"
//				cell.setCellValue(image);
				insertImage(workbook, sheet, image, nextRowNum, IMAGE_CELL_NUM);
			}
			
			String path = photo.getPath();
			if (path != null) {
				Cell cell = row.createCell(PATH_CELL_NUM);
				cell.setCellValue(path);
			}

			String titleSpanish = photo.getTitleSpanish();
			if (titleSpanish != null) {
				Cell cell = row.createCell(TITLE_SPANISH_CELL_NUM);
				cell.setCellValue(titleSpanish);
			}

			String titleEnglish = photo.getTitleEnglish();
			if (titleEnglish != null) {
				Cell cell = row.createCell(TITLE_ENGLISH_CELL_NUM);
				cell.setCellValue(titleEnglish);
			}

			String descSpanish = photo.getDescSpanish();
			if (descSpanish != null) {
				Cell cell = row.createCell(DESC_SPANISH_CELL_NUM);
				cell.setCellValue(descSpanish);
			}

			String descEnglish = photo.getDescEnglish();
			if (descEnglish != null) {
				Cell cell = row.createCell(DESC_ENGLISH_CELL_NUM);
				cell.setCellValue(descEnglish);
			}

			String tagsSpanish = photo.getTagsSpanish();
			if (tagsSpanish != null) {
				Cell cell = row.createCell(TAGS_SPANISH_CELL_NUM);
				cell.setCellValue(tagsSpanish);
			}

			String tagsEnglish = photo.getTagsEnglish();
			if (tagsEnglish != null) {
				Cell cell = row.createCell(TAGS_ENGLISH_CELL_NUM);
				cell.setCellValue(tagsEnglish);
			}

			String status = photo.getStatus();
			if (status != null) {
				Cell cell = row.createCell(STATUS_CELL_NUM);
				cell.setCellValue(status);
			}

			String sentSites = photo.getSentSites();
			if (sentSites != null) {
				Cell cell = row.createCell(SENT_SITES_CELL_NUM);
				cell.setCellValue(sentSites);
			}

			String dateSent = photo.getDateSent();
			if (dateSent != null) {
				Cell cell = row.createCell(DATE_SENT_CELL_NUM);
				cell.setCellValue(dateSent);
			}

			String comments = photo.getComments();
			if (comments != null) {
				Cell cell = row.createCell(COMMENTS_CELL_NUM);
				cell.setCellValue(comments);
			}
		}
		
		// Write the output to a file
		try (OutputStream fileOut = new FileOutputStream(excelFile)) {
			
			//System.err.println("[SpreadsheetManager - writeRow]excelFile: " + excelFile);
			
			workbook.write(fileOut);
		}
	}
	
	private static void insertImage(Workbook workbook, Sheet sheet, String imagePath, int rowNum, int cellNum) throws Exception {
		try (InputStream is = new FileInputStream(imagePath)) {
			//add picture data to this workbook.
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			
			CreationHelper helper = workbook.getCreationHelper();
			
			// Create the drawing patriarch.  This is the top level container for all shapes.
			Drawing drawing = sheet.createDrawingPatriarch();
			
			//add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			//set top-left corner of the picture,
			//subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(cellNum);
			anchor.setRow1(rowNum);
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			
			//auto-size picture relative to its top-left corner
			pict.resize();
			
			//sheet.autoSizeColumn(cellNum);
		}
	}
	
}
