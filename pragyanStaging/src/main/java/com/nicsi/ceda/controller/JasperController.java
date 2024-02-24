package com.nicsi.ceda.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@ApiIgnore
public class JasperController 
{
	@Autowired
	Tbl_Project_Detail_IntrimRepo projectRepo;
	
	@GetMapping("generatePDF")
	public void generatePDF(HttpServletResponse response) throws JRException, IOException
	{
		
		List<Tbl_Project_Detail_Intrim> project = projectRepo.findByStatus(1);
		
		InputStream jasperStream = (InputStream) this.getClass().getResourceAsStream("/pragyanListOfActiveScheme.jasper");
		Map params = new HashMap<>();
	    
	    
	    //System.out.println("All Activated Scheme = "+projectRepo.findByStatus(1));
	    
	    final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(project);
        
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);

	    response.setContentType("application/x-pdf");
	    response.setHeader("Content-disposition", "inline; filename=SchemeList.pdf");

	    final ServletOutputStream outStream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	 
	}
	@GetMapping("generateExcel")
	public void generateExcel(HttpServletResponse response) throws JRException, IOException
	{
		
		List<Tbl_Project_Detail_Intrim> project = projectRepo.findByStatus(1);
		
		InputStream jasperStream = (InputStream) this.getClass().getResourceAsStream("/pragyanListOfActiveScheme.jasper");
		Map params = new HashMap<>();
	    params.put("Sector CodeAshu ","sec_code");
	    params.put("ministry_code","ministry_code");
	    params.put("dept_code","dept_code");
	    params.put("project_name_e","project_name_e");
	    params.put("project_code","project_code");
	    params.put("project_cord_email","project_cord_email");
	    params.put("project_head_email","project_head_email");
	    
	    //System.out.println("All Activated Scheme = "+projectRepo.findByStatus(1));
	    
	    final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(project);
        
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=jasperReport.xlsx");

	    final ServletOutputStream outStream = response.getOutputStream();
	   // JasperExportManager.exportReport(jasperPrint, outStream);
	 
	}
}
