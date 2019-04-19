package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadExcel {

	
	@SuppressWarnings("resource")
	public void readWholeExcel(String filePath, String fileName, String sheetName) throws IOException {

		File file = new File(filePath + "\\" + fileName);
		System.out.println(file.getAbsolutePath());
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = null;
		Workbook = new HSSFWorkbook(inputStream);
		Sheet Sheet = Workbook.getSheet(sheetName);
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = Sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				System.out.print(row.getCell(j).getStringCellValue());
			}
			System.out.println();

		}
	}
		
		@SuppressWarnings("resource")
		public void readParticularRow(String filePath, String fileName, String sheetName, int RowNumber) throws IOException {

				File file = new File(filePath + "\\" + fileName);
				System.out.println(file.getAbsolutePath());
				FileInputStream inputStream = new FileInputStream(file);
				Workbook Workbook = null;
				Workbook = new HSSFWorkbook(inputStream);
				Sheet Sheet = Workbook.getSheet(sheetName);
				
					Row row = Sheet.getRow(RowNumber);
					for (int j = 1; j < row.getLastCellNum(); j++) {
						System.out.print(row.getCell(j).getStringCellValue());
					}
					System.out.println();

				}
		

@SuppressWarnings("resource")
public List<String> readParticularColumn(String filePath, String fileName, String sheetName, int ColumnNumber) throws IOException {

		File file = new File(filePath + "\\" + fileName);
		//System.out.println(file.getAbsolutePath());
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = null;
		Workbook = new HSSFWorkbook(inputStream);
		Sheet Sheet = Workbook.getSheet(sheetName);
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();
		List<String> list= new ArrayList<String>();
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = Sheet.getRow(i);
			String value = row.getCell(ColumnNumber).getStringCellValue();
			//System.out.print(value);
			list.add(value);
			
			

		}
		return list;
}
	}
	
	


