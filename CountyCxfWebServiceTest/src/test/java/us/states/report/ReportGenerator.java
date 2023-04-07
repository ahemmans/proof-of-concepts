package us.states.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import us.states.cucumber.Context;
import us.states.cucumber.ScenarioContext;

@Slf4j
@Service
public class ReportGenerator {

	static String WORKING_DIR_REPORTS;
	
	static Workbook workbook;
	static Sheet sheet;
	static Row header, row;
	static Cell headerCell, cell;
	static CellStyle headerStyle, style, styleDiff;
	
	static Context[] dataArray = { Context.TESTCASE_ID, Context.REQUEST_FILE, Context.FILE_ATTACHMENT, 
			Context.ELEMENTS, Context.REMOVE_EMPTY, 
			Context.EXPECTED_RESPONSE, Context.ACTUAL_RESPONSE,
			Context.TESTCASE_STATUS};
	
	static int[] colWidths = { 4, 10, 10, 
			6, 4, 
			6, 8
			}; //,4
	
	public static void createReport(List<ScenarioContext> listOfScenarios, String fileName) throws IOException {		
		
		workbook = new XSSFWorkbook();		
		sheet = workbook.createSheet("TestScenarios");
		for(int i=0;i<colWidths.length;i++) {
			sheet.setColumnWidth(i, colWidths[i]*1000);
		}

		createHeader();
		createDataRows(listOfScenarios);
		saveWorkbook(fileName);
	}
	
	private static void createHeader() {
		header = sheet.createRow(0);

		headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		headerStyle.setFont(font);

		for(int i=0;i<dataArray.length-1;i++) {
			headerCell = header.createCell(i);
			headerCell.setCellValue(dataArray[i].getDesc());
			headerCell.setCellStyle(headerStyle);
		}

	}
	
	private static void createDataRows(List<ScenarioContext> listOfScenarios) {
		style = workbook.createCellStyle();
		style.setWrapText(true);

		styleDiff = workbook.createCellStyle();
		styleDiff.setWrapText(true);
		styleDiff.setFillForegroundColor(IndexedColors.ROSE.getIndex());
		styleDiff.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		for(int rownum=1;rownum<=listOfScenarios.size();rownum++) {
			row = sheet.createRow(rownum);
			
			for(int cellnum=0;cellnum<dataArray.length-1;cellnum++) {
				cell = row.createCell(cellnum);
				cell.setCellValue(listOfScenarios.get(rownum-1).getContext(dataArray[cellnum]).toString());
				if((boolean) listOfScenarios.get(rownum-1).getContext(dataArray[dataArray.length-1].TESTCASE_STATUS)) {
					cell.setCellStyle(style);
				} else {
					cell.setCellStyle(styleDiff);
				}				
			}
		}

	}
	
	private static void saveWorkbook(String fileName) throws IOException {
		createWorkingDir();
		hideColumns(fileName);		
		FileOutputStream fos = new FileOutputStream(WORKING_DIR_REPORTS + fileName);
		workbook.write(fos);
		workbook.close();
	}

	
	private static void hideColumns(String fileName) {
		if(!fileName.contains("Fault")) {
			sheet.setColumnHidden(3, true);
			sheet.setColumnHidden(4, true);
		}
	}
	
	private static void createWorkingDir() {
		File directory = new File(WORKING_DIR_REPORTS);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}
	
	@Value("${workingdir.reports}") 
	public void setWorkingDirReports(String workingDirReports) {
		WORKING_DIR_REPORTS = workingDirReports;
	}
	
}
