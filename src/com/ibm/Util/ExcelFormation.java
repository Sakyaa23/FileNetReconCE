package com.ibm.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelFormation {
	//private static ReadProperties props = new ReadProperties();
	public static void ObjectStoreData(ResultSet rs1, String ObjStr, String str, String Const_Path) throws IOException, SQLException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
		//String Const_Path = Paths.get(props.readPropertiesFile().getProperty("Shared_Path")).toString();
		System.out.println(Const_Path);
		//String Const_Path="\\\\dc04dwvfns306\\Shared\\ECM_Data\\FileNet_Data\\";
		//String Const_Path = "\\\\va10vnas007b\\ECM_Prod\\FileNetReconCE";
		
		if(ObjStr=="west") {
			writeHeaderLine(sheet, ObjStr);
			writeDataLines(rs1, workbook, sheet, ObjStr);
			//String excelFilePath = "C:\\Automation_L1\\Automated_FileNetReconCE\\WestClaims_"+str+".xlsx";
			String excelFilePath = Const_Path+"/WestClaims_"+str+".xlsx";
			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
		}else if(ObjStr=="central1") {
			writeHeaderLine(sheet, ObjStr);
			writeDataLines(rs1, workbook, sheet, ObjStr);
			//String excelFilePath = "C:\\Automation_L1\\Automated_FileNetReconCE\\CentralClaims_"+str+".xlsx";
			String excelFilePath = Const_Path+"/CentralClaims1_"+str+".xlsx";
			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
		}else if(ObjStr=="east") {
			writeHeaderLine(sheet, ObjStr);
			writeDataLines(rs1, workbook, sheet, ObjStr);
			//String excelFilePath = "C:\\Automation_L1\\Automated_FileNetReconCE\\EastClaims_"+str+".xlsx";
			String excelFilePath = Const_Path+"/EastClaims_"+str+".xlsx";
			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
		}else if(ObjStr=="central2") {
			writeHeaderLine(sheet, ObjStr);
			writeDataLines(rs1, workbook, sheet, ObjStr);
			//String excelFilePath = "C:\\Automation_L1\\Automated_FileNetReconCE\\CentralClaims_"+str+".xlsx";
			String excelFilePath = Const_Path+"/CentralClaims2_"+str+".xlsx";
			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
		}else if(ObjStr=="fep") {
			writeHeaderLine(sheet, ObjStr);
			writeDataLines(rs1, workbook, sheet, ObjStr);
			//String excelFilePath = "C:\\Automation_L1\\Automated_FileNetReconCE\\FEP_"+str+".xlsx";
			String excelFilePath = Const_Path+"/FEP_"+str+".xlsx";
			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
		}
		System.out.println("Done");
	}

	private static void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet, String ObjStr) throws SQLException {
		int rowCount = 1;
		int counter = 1;
		String firstcolumn = "U5F_DCN";
		String secondcolumn = "FROM_TZ(CAST(CREATE_DATEASTIMESTAMP),'GMT')ATTIMEZONE'US/EASTERN'";
		String thirdcolumn = "'MM/DD/YYYYHH24:MI:SS'";
		String fourthcolumn = "U8B_RECEIVEFAXNUM";
		String fifthcolumn = "UE3E8_SOURCEDOCUMENTID";
		String sixthcolumn = "U9E_F_PAGES";
		if(ObjStr=="central1" || ObjStr=="central2") {
			firstcolumn = "U64_DCN";
			fourthcolumn = "U90_RECEIVEFAXNUM";
			sixthcolumn = "UA4_F_PAGES";
		}
		if(ObjStr=="fep") {
			firstcolumn = "UCED8_DCN";
			fourthcolumn = "UCCE8_RECEIVEFAXNUM";
			sixthcolumn = "UE156_F_PAGES";
		}
		while (result.next()) {
			String column1 = result.getString(firstcolumn);
			String column2 = result.getString(secondcolumn);
			String column3 = result.getString(thirdcolumn);
			String column4 = result.getString(fourthcolumn);
			String column5 = result.getString(fifthcolumn);
			String column6 = result.getString(sixthcolumn);
			System.out.println(counter+" set row");
			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(column1);

			cell = row.createCell(columnCount++);
			cell.setCellValue(column2);

			cell = row.createCell(columnCount++);
			cell.setCellValue(column3);

			cell = row.createCell(columnCount++);
			cell.setCellValue(column4);

			cell = row.createCell(columnCount++);
			cell.setCellValue(column5);

			cell = row.createCell(columnCount);
			cell.setCellValue(column6);

			System.out.println(counter+" @ result : "+column1+"@"+column2+"@"+column3+"@"+column4+"@"+column5+"@"+column6);
			counter++;

		}
	}

	private static void writeHeaderLine(XSSFSheet sheet, String ObjStr) {
		Row headerRow = sheet.createRow(0);
		String firstcolumn = "U5F_DCN";
		String secondcolumn = "FROM_TZ(CAST(CREATE_DATEASTIMESTAMP),'GMT')ATTIMEZONE'US/EASTERN'";
		String thirdcolumn = "'MM/DD/YYYYHH24:MI:SS'";
		String fourthcolumn = "U8B_RECEIVEFAXNUM";
		String fifthcolumn = "UE3E8_SOURCEDOCUMENTID";
		String sixthcolumn = "U9E_F_PAGES";
		if(ObjStr=="central1") {
			firstcolumn = "U64_DCN";
			fourthcolumn = "U90_RECEIVEFAXNUM";
			sixthcolumn = "UA4_F_PAGES";
		} else if(ObjStr=="central2") {
			firstcolumn = "U64_DCN";
			fourthcolumn = "U90_RECEIVEFAXNUM";
			sixthcolumn = "UA4_F_PAGES";
		} else if (ObjStr=="fep") {
			firstcolumn = "UCED8_DCN";
			fourthcolumn = "UCCE8_RECEIVEFAXNUM";
			sixthcolumn = "UE156_F_PAGES";
		} 
		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue(firstcolumn);

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue(secondcolumn);

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue(thirdcolumn);

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue(fourthcolumn);

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue(fifthcolumn);

		headerCell = headerRow.createCell(5);
		headerCell.setCellValue(sixthcolumn);
	}
}
