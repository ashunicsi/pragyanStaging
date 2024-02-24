package com.nicsi.ceda.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort.Direction;

import com.nicsi.ceda.listener.JobCompletionNotificationListener;
import com.nicsi.ceda.model.TableDataTemp;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATA;
import com.nicsi.ceda.model.Tbl_DataPort_KPI_PORTED_DATADeleted;
import com.nicsi.ceda.processor.PortedKpiProcessor;
import com.nicsi.ceda.processor.ReadUserDataProcessor;
import com.nicsi.ceda.repository.TableDataTempRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATADeletedRepo;
import com.nicsi.ceda.repository.Tbl_DataPort_KPI_PORTED_DATARepo;
import com.nicsi.ceda.util.GlobalVariable;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration 
{
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    @Lazy
    private TableDataTempRepo dataRepo;
    @Autowired
    @Lazy
    private Tbl_DataPort_KPI_PORTED_DATARepo kpiRepo;
    

    @Bean
    @Primary
   // public RepositoryItemReader<Tbl_DataPort_KPI_PORTED_DATA> kpiDataReader(@Value("#{jobParameters['projectCode']}") Long projectCode) 
    public RepositoryItemReader<TableDataTemp> decryptDataReader() 
    {
    
        RepositoryItemReader<TableDataTemp> reader = new RepositoryItemReader<>();
        reader.setRepository(dataRepo);
        reader.setMethodName("findAll");
      
        reader.setPageSize(1000);
        Map<String, Direction> sorts = new HashMap<>();
        sorts.put("id", Direction.ASC);
        reader.setSort(sorts);
        return reader;
        
        
    }
   
    @Bean
    public RepositoryItemWriter<Tbl_DataPort_KPI_PORTED_DATA> writer() 
    {
        RepositoryItemWriter<Tbl_DataPort_KPI_PORTED_DATA> writer = new RepositoryItemWriter<>();
        writer.setRepository(kpiRepo);
       writer.setMethodName("save");
        return writer;
    }


    @Bean
    public ReadUserDataProcessor processor() 
    {
    	return new ReadUserDataProcessor();
    }


    @Bean
    public Step step1(ItemReader<TableDataTemp> itemReader, ItemWriter<Tbl_DataPort_KPI_PORTED_DATA> itemWriter)
            throws Exception 
    {
        return this.stepBuilderFactory.get("step1").<TableDataTemp, Tbl_DataPort_KPI_PORTED_DATA>chunk(1000).reader(itemReader)
                .processor(processor()).writer(itemWriter).taskExecutor(taskExecutor()).build();
    }
   
    @Bean
    public Job decryptData(JobCompletionNotificationListener listener, Step step1) throws Exception 
    {
        return this.jobBuilderFactory.get("decryptData").incrementer(new RunIdIncrementer()).listener(listener).start(step1).build();
    }
    @Bean
    public TaskExecutor taskExecutor() 
    {
    	SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    	asyncTaskExecutor.setConcurrencyLimit(50);
    	asyncTaskExecutor.setThreadNamePrefix("Thread N:-> ");
     
    	return asyncTaskExecutor;
    }
    
  
}