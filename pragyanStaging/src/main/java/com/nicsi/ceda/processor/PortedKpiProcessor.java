package com.nicsi.ceda.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATADeleted;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATADeletedRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.util.GlobalVariable;


public class PortedKpiProcessor implements ItemProcessor<Tbl_DataPort_KPI_PORTED_DATA, Tbl_DataPort_KPI_PORTED_DATADeleted> 
{
    private static final Logger log = LoggerFactory.getLogger(PortedKpiProcessor.class);

    @Autowired
    private Tbl_DataPort_KPI_PORTED_DATARepo kpiRepo;
    @Autowired
    @Lazy
    private Tbl_DataPort_KPI_PORTED_DATADeletedRepo deletedRepo;
    
    @Override
    public Tbl_DataPort_KPI_PORTED_DATADeleted process(Tbl_DataPort_KPI_PORTED_DATA data) throws Exception 
    {
    	
    	Tbl_DataPort_KPI_PORTED_DATADeleted temp = new Tbl_DataPort_KPI_PORTED_DATADeleted();
    	
    	if(data.getProjectCode() == GlobalVariable.projectCode)
    	{
    		if(data.getKpiId() == GlobalVariable.kpiId)
    		{
    			
    			temp.setAtmpt(data.getAtmpt());
                temp.setData_Group_Id(data.getData_Group_Id());
                temp.setDatadt(data.getDatadt());
                temp.setDept_code(data.getDept_code());
                temp.setEntrydt(data.getEntrydt());
                temp.setInstance_Id(data.getInstance_Id());
                temp.setIsComplete(data.getIsComplete());
                temp.setKpi_Data(data.getKpi_Data());
                temp.setKpi_id(data.getKpiId());
                temp.setLevel10Code(data.getLevel10Code());
                temp.setLevel1Code(data.getLevel1Code());
                temp.setLevel2Code(data.getLevel2Code());
                temp.setLevel3Code(data.getLevel3Code());
                temp.setLevel4Code(data.getLevel4Code());
                temp.setLevel5Code(data.getLevel5Code());
                temp.setLevel6Code(data.getLevel6Code());
                temp.setLevel7Code(data.getLevel7Code());
                temp.setLevel8Code(data.getLevel8Code());
                temp.setLevel9Code(data.getLevel9Code());
                temp.setMigrated(data.getMigrated());
                temp.setMinistry_Code(data.getMinistry_Code());
                temp.setMode_frequency_id(data.getMode_frequency_id());
                temp.setProjectCode(data.getProjectCode());
                temp.setSec_code(data.getSec_code());
                temp.setSimilarity(data.getSimilarity());
                temp.setUsername(data.getUsername());
                //deletedRepo.save(temp);
                //Delete KPI Data 
                //kpiRepo.deleteById(data.getId());
    		}
    		else if(GlobalVariable.kpiId == 0)
    		{
    			//for Project Code  based data clean
    			temp.setAtmpt(data.getAtmpt());
                temp.setData_Group_Id(data.getData_Group_Id());
                temp.setDatadt(data.getDatadt());
                temp.setDept_code(data.getDept_code());
                temp.setEntrydt(data.getEntrydt());
                temp.setInstance_Id(data.getInstance_Id());
                temp.setIsComplete(data.getIsComplete());
                temp.setKpi_Data(data.getKpi_Data());
                temp.setKpi_id(data.getKpiId());
                temp.setLevel10Code(data.getLevel10Code());
                temp.setLevel1Code(data.getLevel1Code());
                temp.setLevel2Code(data.getLevel2Code());
                temp.setLevel3Code(data.getLevel3Code());
                temp.setLevel4Code(data.getLevel4Code());
                temp.setLevel5Code(data.getLevel5Code());
                temp.setLevel6Code(data.getLevel6Code());
                temp.setLevel7Code(data.getLevel7Code());
                temp.setLevel8Code(data.getLevel8Code());
                temp.setLevel9Code(data.getLevel9Code());
                temp.setMigrated(data.getMigrated());
                temp.setMinistry_Code(data.getMinistry_Code());
                temp.setMode_frequency_id(data.getMode_frequency_id());
                temp.setProjectCode(data.getProjectCode());
                temp.setSec_code(data.getSec_code());
                temp.setSimilarity(data.getSimilarity());
                temp.setUsername(data.getUsername());
                
                //Delete KPI Data 
                //kpiRepo.deleteById(data.getId());
    		}
    		
           
    	}
    	else
    	{
    		temp.setProjectCode(Long.valueOf(0));
    	}
    	//System.out.println("ASHU="+temp);
    	 return temp;
        
    }

}
