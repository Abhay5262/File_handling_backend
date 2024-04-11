package com.File_Excel.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.File_Excel.Entity.Excel_Entity;
import com.File_Excel.dto.Excel_dto;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class Excel_helper {
	
	private static ObjectMapper mapper;
	//for check file format is excel type or not
	public static boolean checkformat(MultipartFile mulfile) {
		String contentType =  mulfile.getContentType();
		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\r\n")) {
			return true;
		}
		else {
		return false;
		}
	}
	
	//Following Method convert Excel Sheet To List
	public static List<Excel_Entity>convertExcelToList(InputStream is) throws IOException{
			List<Excel_Entity> list=new ArrayList<>();
			try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is)) {
				
				XSSFSheet sheet = xssfWorkbook.getSheet("Order");
				
				int rownumber=0;
				Iterator<Row> iterator = sheet.iterator();
				while (iterator.hasNext()) {
					Row next = iterator.next();
					
					if (rownumber==0) {
						rownumber++;
						continue;
					}
					
					Iterator<Cell> cell = next.iterator();
					
					int cid=0;
					
					Excel_Entity entity=new Excel_Entity(); 
					
					while (cell.hasNext()) {
						Cell cells = cell.next();
						
						switch(cid)
						{
						case 0:
							entity.setOrder_no(cells.getStringCellValue());
							break;
						case 1:
							entity.setOrderquantity(cells.getStringCellValue());
							break;
						case 2:
							entity.setSale(cells.getStringCellValue());
							break;
						default:
							break;
						}
						cid++;
					}
					list.add(entity);
				}
			}
				return list;
	}
	public static String[] HEADERS= {
			"Order_no",
			"Sale",
			"Orderquantity"
	};
	
//	public static String SHEET_NAME="Order";
	
	public static Resource getexcelfile(){
		
		return 	new ClassPathResource("static/Excelsheet/order.xlsx");
		
		
		
		
		
		
		
		
//		//Create work book
//		Workbook workbook=new XSSFWorkbook();
//		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		
//		
//		try {
//			
//			//Create sheet
//			Iterable<Row> sheet = workbook.createSheet(SHEET_NAME);
//			
//			
//			//Create row : header row
//			Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(0);
//			
//			for(int i=0; i < HEADERS.length; i++)
//			{
//				Cell cell = row.createCell(i);
//				cell.setCellValue(HEADERS[i]);
//			}
//			
//			workbook.write(out);
//			
//			return new ByteArrayInputStream(out.toByteArray());
//			
//			
//		}		
//		
//		finally
//		{
//			workbook.close();
//			out.close();
//		}
		
	}
	
	public static String SHEET="order";
	
	public static ByteArrayInputStream dowmloadExcel(List<Excel_Entity> list) throws IOException
	{
		
		//Create work book
		Workbook workbook=new XSSFWorkbook();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		
		try {
			
			//Create sheet
			Iterable<Row> sheet = workbook.createSheet(SHEET);
			
			
			//Create row : header row
			Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(0);
			
			for(int i=0; i < HEADERS.length; i++)
			{
				Cell cell = row.createCell(i);
				cell.setCellValue(HEADERS[i]);
			}
			
			
//			Value rows
			int rowIndex=0;
			
			for(Excel_Entity en : list)
			{
				Row dataRow = ((XSSFSheet) sheet).createRow(rowIndex);
				
				rowIndex++;
				
				dataRow.createCell(0).setCellValue(en.getOrder_no());
				dataRow.createCell(1).setCellValue(en.getSale());
				dataRow.createCell(2).setCellValue(en.getOrderquantity());
			}
			
			workbook.write(out);
			
			return new ByteArrayInputStream(out.toByteArray());
			
			
		}		
		
		finally
		{
			workbook.close();
			out.close();
		}
		
	}
	
	public static Excel_Entity dtotoentity(Excel_dto e){
	return  mapper.convertValue(e, Excel_Entity.class);
	}
	
	public static Excel_dto entitytodto(Excel_Entity en) {
		return mapper.convertValue(en, Excel_dto.class);
	}

	
	
}
