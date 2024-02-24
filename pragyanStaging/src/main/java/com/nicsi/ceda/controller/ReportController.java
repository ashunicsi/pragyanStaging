package com.nicsi.ceda.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.repository.ListOfSchemeForReportRepo;
import com.nicsi.ceda.services.IDataService;


@CrossOrigin(origins = {"${settings.cors_origin}"})
@RestController
public class ReportController 
{
	
	@Autowired
	private IDataService dataService;
	@Autowired
    private Environment environment;
	
	
	
	@GetMapping("listOfSchemeInPdf")
	public ResponseEntity<ByteArrayResource> generateReportInPdf(int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		String pdf = "";
		if(status == 1)
		{
			pdf = "listOfActiveScheme"+".pdf";
		}
		else
		{
			pdf = "listOfInactiveScheme"+".pdf";
		}
		
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(environment.getProperty("pdfPath")+pdf));
		document.open();
		
		PdfPTable table1 = new PdfPTable(1); 
		table1.setSpacingBefore(1f);
		document.setMargins(20, 1, 1, 0);
		 
	    table1.setWidthPercentage(100);
	    table1.getDefaultCell().setBorderWidth(0f);
	    table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    Phrase p1 ;
	    Font f1 =FontFactory.getFont("Mangal", BaseFont.IDENTITY_H, true);
	    

       
	    p1 = new Phrase("Pragyan Data Integration & Exploration Platform", FontFactory.getFont(FontFactory.TIMES_BOLD, 20));
	    table1.addCell(p1);
	    p1 = new Phrase("(Designed & Developed by CENTRE OF EXCELLENCE FOR DATA ANALYTICS)", FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
	    table1.addCell(p1);
	    p1 = new Phrase("National Informatics Centre Services Inc. (NICSI)", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
	    table1.addCell(p1);
	    table1.setSpacingAfter(10f);
	    document.add(table1);
	    document.topMargin();
	    PdfPTable table2 = new PdfPTable(1);
	    table2.setWidthPercentage(100f);
	    
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	   
	    
	    PdfPTable table3 = new PdfPTable(1);
	    table3.setWidthPercentage(100f);
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
	    LocalDateTime now1 = LocalDateTime.now();  
	    String date = dtf1.format(now1);
	    Phrase p3 = new Phrase("Date:-"+date, FontFactory.getFont(FontFactory.TIMES, 11));
	    table3.addCell(p3);
	    document.add(table3);
	    
	    PdfPTable table4 = new PdfPTable(1);
	    table4.setWidthPercentage(100f);
	    Phrase p4 = null;
	    if(status == 1)
		{
	    	  p4 = new Phrase("LIST OF ACTIVE SCHEME", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
		else
		{
			  p4 = new Phrase("LIST OF INACTIVE SCHEME", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
	  
	    PdfPCell cell;
	    cell = new PdfPCell(new Paragraph(p4));
	    cell.setHorizontalAlignment(1);
	    cell.setBackgroundColor(Color.lightGray);
	    table4.addCell(cell);
	    table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    document.add(table4);
	    PdfPTable tableCart = new PdfPTable(new float[] { (float) 1, 5, (float) 5, (float) 5, (float) 2, (float) 2,  (float) 2 });
	    tableCart.setWidthPercentage(100f);
	    List<SchemeDetailsDTO> data =dataService.findAllScheme(status, map, request, sessions);
	    
	    tableCart.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableCart.addCell("SN");
	    tableCart.addCell("MINISTRY NAME (CODE)");
	    tableCart.addCell("SCHEME NAME (CODE)");
	    tableCart.addCell("DEPT. NAME (CODE)");
	    tableCart.addCell("ADMIN EMAIL");
	    tableCart.addCell("CORD EMAIL");
	    tableCart.addCell("HEAD EMAIL");
	    table1.setHeaderRows(1);
	    int i = 1;
	   
	   
	    for(SchemeDetailsDTO scheme : data)
	    {
	    	 tableCart.addCell(String.valueOf(i++));
			 tableCart.addCell(scheme.getMinistryName() +" ("+scheme.getMinistryCode()+")");
			 tableCart.addCell(scheme.getSchemeName() +" ("+scheme.getSchemeCode()+")");
			 tableCart.addCell(scheme.getDepartmentName() +" ("+scheme.getDepartmentCode()+")");
			 tableCart.addCell(scheme.getAdminEmail());
			 tableCart.addCell(scheme.getCordEmail());
			 tableCart.addCell(scheme.getHeadEmail());
			  
	    }
	    document.add(tableCart);
	    document.close();
	 // Path to the PDF file
        String filePath = environment.getProperty("pdfPath")+pdf;

        // Load the PDF file into a byte array
        Path path = Paths.get(filePath);
        byte[] pdfBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveScheme.pdf");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveScheme.pdf");
		}
       
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return the ResponseEntity with the PDF content and headers
        return ResponseEntity.ok().headers(headers).contentLength(pdfBytes.length).body(resource);
		
	}
	@GetMapping("listOfSchemeInExcel")
	public ResponseEntity<ByteArrayResource> generateReportInExcel(int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		List<SchemeDetailsDTO> data =dataService.findAllScheme(status, map, request, sessions);
		String file = "";
		if(status == 1)
		{
			file = "listOfActiveScheme"+".xlsx";
		}
		else
		{
			file = "listOfInactiveScheme"+".xlsx";
		}
		
		String filePath = environment.getProperty("pdfPath")+file;
		File f = new File(filePath);
		Workbook wb = new HSSFWorkbook(); 
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("SCHEME DETAILS");
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 15000);
		sheet.setColumnWidth(2, 15000);
		sheet.setColumnWidth(3, 15000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 8000);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
	  	
	  	XSSFFont font = workbook.createFont();
	  	font.setFontHeightInPoints((short) 12); 
	  	cellStyle.setFont(font);  
	  	cellStyle.setWrapText(true);
	    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	  	int rowCount = 0;
	  	Row row = sheet.createRow(rowCount++);
	    int columnCount = 0;
	    Cell cell = row.createCell(0);
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SN");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "MINISTRY NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SCHEME NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "DEPT.NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "ADMIN EMAIL");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "CORDINATOR EMAIL");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "HEAD EMAIL");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    
	   rowCount = 1;
	   int i = 1;
	    for(SchemeDetailsDTO dto : data)
	    { 	
		    row = sheet.createRow(rowCount++);
		    
		    columnCount = 0;
		    cell = row.createCell(columnCount++);
		    cell.setCellValue((Integer) i++);
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getMinistryName()+" ("+dto.getMinistryCode()+")");
		    
		    cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getSchemeName()+" ("+dto.getSchemeCode()+")");
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getDepartmentName()+" ("+dto.getDepartmentCode()+")");
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getAdminEmail());
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getCordEmail());
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getHeadEmail());
	    }    
	    
	    try (FileOutputStream outputStream = new FileOutputStream(filePath)) 
	    {
	        workbook.write(outputStream); 
	    }
	    Path path = Paths.get(filePath);
        byte[] excelBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
       
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveScheme.xlsx");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveScheme.xlsx");
		}
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Return the ResponseEntity with the Excel content and headers
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(excelBytes.length)
                .body(resource);
                
		
        
	}
	@GetMapping("listOfKPIInPdf")
	public ResponseEntity<ByteArrayResource> generateKPIReportInPdf(int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		String pdf = "";
		if(status == 1)
		{
			pdf = "listOfActiveKPI"+".pdf";
		}
		else
		{
			pdf = "listOfInactiveKPI"+".pdf";
		}
		
		Document document = new Document(PageSize.A4);
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/var/www/tomcat/webapps/PINEW/pdf/generated/"+pdf));
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(environment.getProperty("pdfPath")+pdf));
		//map.addAttribute("referenceNo", attributeValue)
		document.open();
		PdfPTable table1 = new PdfPTable(1); 
		table1.setSpacingBefore(1f);
		document.setMargins(20, 1, 1, 0);
		 
	    table1.setWidthPercentage(100);
	    table1.getDefaultCell().setBorderWidth(0f);
	    table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    Phrase p1 ;
	    Font f1 =FontFactory.getFont("Mangal", BaseFont.IDENTITY_H, true);
	    

       
	    p1 = new Phrase("Pragyan Data Integration & Exploration Platform", FontFactory.getFont(FontFactory.TIMES_BOLD, 20));
	    table1.addCell(p1);
	    p1 = new Phrase("(Designed & Developed by CENTRE OF EXCELLENCE FOR DATA ANALYTICS)", FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
	    table1.addCell(p1);
	    p1 = new Phrase("National Informatics Centre Services Inc. (NICSI)", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
	    table1.addCell(p1);
	    table1.setSpacingAfter(10f);
	    document.add(table1);
	    PdfPTable table2 = new PdfPTable(1);
	    table2.setWidthPercentage(100f);
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	   
	    
	    PdfPTable table3 = new PdfPTable(1);
	    table3.setWidthPercentage(100f);
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
	    LocalDateTime now1 = LocalDateTime.now();  
	    String date = dtf1.format(now1);
	    Phrase p3 = new Phrase("Date:-"+date, FontFactory.getFont(FontFactory.TIMES, 11));
	    table3.addCell(p3);
	    document.add(table3);
	    
	    PdfPTable table4 = new PdfPTable(1);
	    table4.setWidthPercentage(100f);
	    Phrase p4 = null;
	    if(status == 1)
		{
	    	  p4 = new Phrase("LIST OF ACTIVATE KPI", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
		else
		{
			  p4 = new Phrase("LIST OF ACTIVATE KPI", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
	  
	    PdfPCell cell;
	    cell = new PdfPCell(new Paragraph(p4));
	    cell.setHorizontalAlignment(1);
	    cell.setBackgroundColor(Color.lightGray);
	    table4.addCell(cell);
	    table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    document.add(table4);
	    PdfPTable tableCart = new PdfPTable(new float[] { (float) 1, 5, (float) 5, (float) 5, (float) 2, (float) 2});
	    tableCart.setWidthPercentage(100f);

	    tableCart.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableCart.addCell("SN");
	    tableCart.addCell("MINISTRY NAME (CODE)");
	    tableCart.addCell("SCHEME NAME (CODE)");
	    tableCart.addCell("DEPT. NAME (CODE)");
	    tableCart.addCell("KPI NAME");
	    tableCart.addCell("DISPLAY TYPE NAME");
	    table1.setHeaderRows(1);
	    int i = 1;
	   
	    List<KPIDetailsDTO> data =dataService.findAllKPI(status, map, request, sessions);
	    for(KPIDetailsDTO scheme : data)
	    {
	    	 tableCart.addCell(String.valueOf(i++));
			 tableCart.addCell(scheme.getMinistryName() +" ("+scheme.getMinistryCode()+")");
			 tableCart.addCell(scheme.getSchemeName() +" ("+scheme.getSchemeCode()+")");
			 tableCart.addCell(scheme.getDeptName() +" ("+scheme.getDeptCode()+")");
			 tableCart.addCell(scheme.getKpiName());
			 tableCart.addCell(scheme.getKpi_data_display_type_name());
			  
	    }
	    document.add(tableCart);
	    document.close();
	 // Path to the PDF file
        String filePath = environment.getProperty("pdfPath")+pdf;

        // Load the PDF file into a byte array
        Path path = Paths.get(filePath);
        byte[] pdfBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveKPI.pdf");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveKPI.pdf");
		}
       
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return the ResponseEntity with the PDF content and headers
        return ResponseEntity.ok().headers(headers).contentLength(pdfBytes.length).body(resource);
		
	}
	@GetMapping("listOfKPIInExcel")
	public ResponseEntity<ByteArrayResource> generateKPIReportInExcel(int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		List<KPIDetailsDTO> data =dataService.findAllKPI(status, map, request, sessions);
		String file = "";
		if(status == 1)
		{
			file = "listOfActiveKPI"+".xlsx";
		}
		else
		{
			file = "listOfInactiveKPI"+".xlsx";
		}
		
		String filePath = environment.getProperty("pdfPath")+file;
		File f = new File(filePath);
		Workbook wb = new HSSFWorkbook(); 
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("KPI DETAILS");
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 15000);
		sheet.setColumnWidth(2, 15000);
		sheet.setColumnWidth(3, 15000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 8000);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
	  	
	  	XSSFFont font = workbook.createFont();
	  	font.setFontHeightInPoints((short) 12); 
	  	cellStyle.setFont(font);  
	  	cellStyle.setWrapText(true);
	    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	  	int rowCount = 0;
	  	Row row = sheet.createRow(rowCount++);
	    int columnCount = 0;
	    Cell cell = row.createCell(0);
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SN");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "MINISTRY NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SCHEME NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "DEPT.NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "KPI NAME");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "DISPLAY TYPE NAME");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    
	   rowCount = 1;
	   int i = 1;
	   
	    for(KPIDetailsDTO dto : data)
	    { 
		    row = sheet.createRow(rowCount++);
			
		    columnCount = 0;
		    cell = row.createCell(columnCount++);
		   
		    cell.setCellValue((Integer) i++);
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getMinistryName()+" ("+dto.getMinistryCode()+")");
		    
		    cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getSchemeName()+" ("+dto.getSchemeCode()+")");
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getDeptName()+" ("+dto.getDeptCode()+")");
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getKpiName());
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getKpi_data_display_type_name());
		    
		   
	    }    
	    
	    try (FileOutputStream outputStream = new FileOutputStream(filePath)) 
	    {
	        workbook.write(outputStream); 
	    }
	    Path path = Paths.get(filePath);
        byte[] excelBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
       
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveKPI.xlsx");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveKPI.xlsx");
		}
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Return the ResponseEntity with the Excel content and headers
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(excelBytes.length)
                .body(resource);
                
		
        
	}
	//For User Data
	
	@GetMapping("listOfSchemeInPdfForUser")
	public ResponseEntity<ByteArrayResource> generateReportInPdfForUser(@RequestParam("username") String username, int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		String pdf = "";
		if(status == 1)
		{
			pdf = "listOfActiveScheme_"+getCurrentDate()+".pdf";
		}
		else
		{
			pdf = "listOfInactiveScheme"+".pdf";
		}
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(environment.getProperty("pdfPath")+pdf));
		document.open();
		
		PdfPTable table1 = new PdfPTable(1); 
		table1.setSpacingBefore(1f);
		document.setMargins(20, 1, 1, 0);
		 
	    table1.setWidthPercentage(100);
	    table1.getDefaultCell().setBorderWidth(0f);
	    table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    Phrase p1 ;
	    Font f1 =FontFactory.getFont("Mangal", BaseFont.IDENTITY_H, true);
	    

       
	    p1 = new Phrase("Pragyan Data Integration & Exploration Platform", FontFactory.getFont(FontFactory.TIMES_BOLD, 20));
	    table1.addCell(p1);
	    p1 = new Phrase("(Designed & Developed by CENTRE OF EXCELLENCE FOR DATA ANALYTICS)", FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
	    table1.addCell(p1);
	    p1 = new Phrase("National Informatics Centre Services Inc. (NICSI)", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
	    table1.addCell(p1);
	    table1.setSpacingAfter(10f);
	    document.add(table1);
	    document.topMargin();
	    PdfPTable table2 = new PdfPTable(1);
	    table2.setWidthPercentage(100f);
	    
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	   
	    
	    PdfPTable table3 = new PdfPTable(1);
	    table3.setWidthPercentage(100f);
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
	    LocalDateTime now1 = LocalDateTime.now();  
	    String date = dtf1.format(now1);
	    Phrase p3 = new Phrase("Date:-"+date, FontFactory.getFont(FontFactory.TIMES, 11));
	    table3.addCell(p3);
	    document.add(table3);
	    
	    PdfPTable table4 = new PdfPTable(1);
	    table4.setWidthPercentage(100f);
	    Phrase p4 = null;
	    if(status == 1)
		{
	    	  p4 = new Phrase("LIST OF ACTIVE SCHEME", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
		else
		{
			  p4 = new Phrase("LIST OF INACTIVE SCHEME", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
	  
	    PdfPCell cell;
	    cell = new PdfPCell(new Paragraph(p4));
	    cell.setHorizontalAlignment(1);
	    cell.setBackgroundColor(Color.lightGray);
	    table4.addCell(cell);
	    table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    document.add(table4);
	    PdfPTable tableCart = new PdfPTable(new float[] { (float) 1, 5, (float) 5, (float) 5, (float) 2, (float) 2,  (float) 2 });
	    tableCart.setWidthPercentage(100f);
	    List<SchemeDetailsDTO> data =dataService.findByUsernameAndStatus(username, status, map, request, sessions);
	    
	    tableCart.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableCart.addCell("SN");
	    tableCart.addCell("MINISTRY NAME (CODE)");
	    tableCart.addCell("SCHEME NAME (CODE)");
	    tableCart.addCell("DEPT. NAME (CODE)");
	    tableCart.addCell("ADMIN EMAIL");
	    tableCart.addCell("CORD EMAIL");
	    tableCart.addCell("HEAD EMAIL");
	    table1.setHeaderRows(1);
	    int i = 1;
	   
	   
	    for(SchemeDetailsDTO scheme : data)
	    {
	    	 tableCart.addCell(String.valueOf(i++));
			 tableCart.addCell(scheme.getMinistryName() +" ("+scheme.getMinistryCode()+")");
			 tableCart.addCell(scheme.getSchemeName() +" ("+scheme.getSchemeCode()+")");
			 tableCart.addCell(scheme.getDepartmentName() +" ("+scheme.getDepartmentCode()+")");
			 tableCart.addCell(scheme.getAdminEmail());
			 tableCart.addCell(scheme.getCordEmail());
			 tableCart.addCell(scheme.getHeadEmail());
			  
	    }
	    document.add(tableCart);
	    document.close();
	 // Path to the PDF file
        String filePath = environment.getProperty("pdfPath")+pdf;

        // Load the PDF file into a byte array
        Path path = Paths.get(filePath);
        byte[] pdfBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveScheme.pdf");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveScheme.pdf");
		}
       
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return the ResponseEntity with the PDF content and headers
        return ResponseEntity.ok().headers(headers).contentLength(pdfBytes.length).body(resource);
		
	}
	@GetMapping("listOfSchemeInExcelForUser")
	public ResponseEntity<ByteArrayResource> generateReportInExcelForUser(@RequestParam("username") String username, int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		List<SchemeDetailsDTO> data =dataService.findByUsernameAndStatus(username, status, map, request, sessions);
		String file = "";
		if(status == 1)
		{
			file = "listOfActiveScheme"+".xlsx";
		}
		else
		{
			file = "listOfInactiveScheme"+".xlsx";
		}
		
		String filePath = environment.getProperty("pdfPath")+file;
		File f = new File(filePath);
		Workbook wb = new HSSFWorkbook(); 
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("SCHEME DETAILS");
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 15000);
		sheet.setColumnWidth(2, 15000);
		sheet.setColumnWidth(3, 15000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 8000);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
	  	
	  	XSSFFont font = workbook.createFont();
	  	font.setFontHeightInPoints((short) 12); 
	  	cellStyle.setFont(font);  
	  	cellStyle.setWrapText(true);
	    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	  	int rowCount = 0;
	  	Row row = sheet.createRow(rowCount++);
	    int columnCount = 0;
	    Cell cell = row.createCell(0);
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SN");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "MINISTRY NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SCHEME NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "DEPT.NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "ADMIN EMAIL");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "CORDINATOR EMAIL");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "HEAD EMAIL");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    
	   rowCount = 1;
	   int i = 1;
	    for(SchemeDetailsDTO dto : data)
	    { 	
		    row = sheet.createRow(rowCount++);
		    
		    columnCount = 0;
		    cell = row.createCell(columnCount++);
		    cell.setCellValue((Integer) i++);
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getMinistryName()+" ("+dto.getMinistryCode()+")");
		    
		    cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getSchemeName()+" ("+dto.getSchemeCode()+")");
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getDepartmentName()+" ("+dto.getDepartmentCode()+")");
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getAdminEmail());
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getCordEmail());
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getHeadEmail());
	    }    
	    
	    try (FileOutputStream outputStream = new FileOutputStream(filePath)) 
	    {
	        workbook.write(outputStream); 
	    }
	    Path path = Paths.get(filePath);
        byte[] excelBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
       
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveScheme.xlsx");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveScheme.xlsx");
		}
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Return the ResponseEntity with the Excel content and headers
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(excelBytes.length)
                .body(resource);
                
		
        
	}
	@GetMapping("listOfKPIInPdfForUser")
	public ResponseEntity<ByteArrayResource> generateKPIReportInPdfForUser(@RequestParam("username") String username, int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		String pdf = "";
		if(status == 1)
		{
			pdf = "listOfActiveKPI"+".pdf";
		}
		else
		{
			pdf = "listOfInactiveKPI"+".pdf";
		}
		
		Document document = new Document(PageSize.A4);
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/var/www/tomcat/webapps/PINEW/pdf/generated/"+pdf));
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(environment.getProperty("pdfPath")+pdf));
		//map.addAttribute("referenceNo", attributeValue)
		document.open();
		PdfPTable table1 = new PdfPTable(1); 
		table1.setSpacingBefore(1f);
		document.setMargins(20, 1, 1, 0);
		 
	    table1.setWidthPercentage(100);
	    table1.getDefaultCell().setBorderWidth(0f);
	    table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    Phrase p1 ;
	    Font f1 =FontFactory.getFont("Mangal", BaseFont.IDENTITY_H, true);
	    

       
	    p1 = new Phrase("Pragyan Data Integration & Exploration Platform", FontFactory.getFont(FontFactory.TIMES_BOLD, 20));
	    table1.addCell(p1);
	    p1 = new Phrase("(Designed & Developed by CENTRE OF EXCELLENCE FOR DATA ANALYTICS)", FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
	    table1.addCell(p1);
	    p1 = new Phrase("National Informatics Centre Services Inc. (NICSI)", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
	    table1.addCell(p1);
	    table1.setSpacingAfter(10f);
	    document.add(table1);
	    PdfPTable table2 = new PdfPTable(1);
	    table2.setWidthPercentage(100f);
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	   
	    
	    PdfPTable table3 = new PdfPTable(1);
	    table3.setWidthPercentage(100f);
	    //table1.getDefaultCell().setBorderWidth(0f);
	    table3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
	    LocalDateTime now1 = LocalDateTime.now();  
	    String date = dtf1.format(now1);
	    Phrase p3 = new Phrase("Date:-"+date, FontFactory.getFont(FontFactory.TIMES, 11));
	    table3.addCell(p3);
	    document.add(table3);
	    
	    PdfPTable table4 = new PdfPTable(1);
	    table4.setWidthPercentage(100f);
	    Phrase p4 = null;
	    if(status == 1)
		{
	    	  p4 = new Phrase("LIST OF ACTIVATE KPI", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
		else
		{
			  p4 = new Phrase("LIST OF ACTIVATE KPI", FontFactory.getFont(FontFactory.TIMES_BOLD, 13));
		}
	  
	    PdfPCell cell;
	    cell = new PdfPCell(new Paragraph(p4));
	    cell.setHorizontalAlignment(1);
	    cell.setBackgroundColor(Color.lightGray);
	    table4.addCell(cell);
	    table4.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    document.add(table4);
	    PdfPTable tableCart = new PdfPTable(new float[] { (float) 1, 5, (float) 5, (float) 5, (float) 2, (float) 2});
	    tableCart.setWidthPercentage(100f);

	    tableCart.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableCart.addCell("SN");
	    tableCart.addCell("MINISTRY NAME (CODE)");
	    tableCart.addCell("SCHEME NAME (CODE)");
	    tableCart.addCell("DEPT. NAME (CODE)");
	    tableCart.addCell("KPI NAME");
	    tableCart.addCell("DISPLAY TYPE NAME");
	    table1.setHeaderRows(1);
	    int i = 1;
	   
	    List<KPIDetailsDTO> data =dataService.findKpiByUsernameAndStatus(username, status, map, request, sessions);
	    for(KPIDetailsDTO scheme : data)
	    {
	    	 tableCart.addCell(String.valueOf(i++));
			 tableCart.addCell(scheme.getMinistryName() +" ("+scheme.getMinistryCode()+")");
			 tableCart.addCell(scheme.getSchemeName() +" ("+scheme.getSchemeCode()+")");
			 tableCart.addCell(scheme.getDeptName() +" ("+scheme.getDeptCode()+")");
			 tableCart.addCell(scheme.getKpiName());
			 tableCart.addCell(scheme.getKpi_data_display_type_name());
			  
	    }
	    document.add(tableCart);
	    document.close();
	 // Path to the PDF file
        String filePath = environment.getProperty("pdfPath")+pdf;

        // Load the PDF file into a byte array
        Path path = Paths.get(filePath);
        byte[] pdfBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveKPI.pdf");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveKPI.pdf");
		}
       
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return the ResponseEntity with the PDF content and headers
        return ResponseEntity.ok().headers(headers).contentLength(pdfBytes.length).body(resource);
		
	}
	@GetMapping("listOfKPIInExcelForUser")
	public ResponseEntity<ByteArrayResource> generateKPIReportInExcelForUser(@RequestParam("username") String username, int status, ModelMap map, HttpServletRequest request, HttpSession sessions) throws DocumentException, IOException
	{
		List<KPIDetailsDTO> data =dataService.findKpiByUsernameAndStatus(username, status, map, request, sessions);
		String file = "";
		if(status == 1)
		{
			file = "listOfActiveKPI"+".xlsx";
		}
		else
		{
			file = "listOfInactiveKPI"+".xlsx";
		}
		
		String filePath = environment.getProperty("pdfPath")+file;
		File f = new File(filePath);
		Workbook wb = new HSSFWorkbook(); 
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("KPI DETAILS");
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 15000);
		sheet.setColumnWidth(2, 15000);
		sheet.setColumnWidth(3, 15000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 8000);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
	  	
	  	XSSFFont font = workbook.createFont();
	  	font.setFontHeightInPoints((short) 12); 
	  	cellStyle.setFont(font);  
	  	cellStyle.setWrapText(true);
	    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	  	int rowCount = 0;
	  	Row row = sheet.createRow(rowCount++);
	    int columnCount = 0;
	    Cell cell = row.createCell(0);
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SN");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "MINISTRY NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "SCHEME NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "DEPT.NAME (CODE)");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "KPI NAME");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    cell.setCellValue((String) "DISPLAY TYPE NAME");
	    cell.setCellStyle(cellStyle );
	    cell = row.createCell(columnCount++);
	    
	   rowCount = 1;
	   int i = 1;
	   
	    for(KPIDetailsDTO dto : data)
	    { 
		    row = sheet.createRow(rowCount++);
			
		    columnCount = 0;
		    cell = row.createCell(columnCount++);
		    cell.setCellValue((Integer) i++);
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getMinistryName()+" ("+dto.getMinistryCode()+")");
		    
		    cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getSchemeName()+" ("+dto.getSchemeCode()+")");
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(dto.getDeptName()+" ("+dto.getDeptCode()+")");
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getKpiName());
		    
		    cell = row.createCell(columnCount++);
		    cell.setCellValue(dto.getKpi_data_display_type_name());
		    
		   
	    }    
	    
	    try (FileOutputStream outputStream = new FileOutputStream(filePath)) 
	    {
	        workbook.write(outputStream); 
	    }
	    Path path = Paths.get(filePath);
        byte[] excelBytes = Files.readAllBytes(path);

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
       
        if(status == 1)
		{
        	 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfActiveKPI.xlsx");
		}
		else
		{
			 headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listOfInactiveKPI.xlsx");
		}
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Return the ResponseEntity with the Excel content and headers
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(excelBytes.length)
                .body(resource);
                
		
        
	}
	public static String getCurrentDate()
	{
		 LocalDate currentDate = LocalDate.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
		 String formattedDate = currentDate.format(formatter);
		 
		 return formattedDate;
	      
	}
}
