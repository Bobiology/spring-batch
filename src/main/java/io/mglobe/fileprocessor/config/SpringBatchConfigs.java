package io.mglobe.fileprocessor.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import io.mglobe.fileprocessor.model.Users;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfigs {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, 
			StepBuilderFactory stepBuilderFactory, 
			ItemReader<Users> itemReader,
			ItemProcessor<Users, Users> itemProcessor, 
			ItemWriter<Users> itemWriter) {
		
		Step step = stepBuilderFactory.get("ETL-FILE_LOAD")
				.<Users, Users>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		
		return jobBuilderFactory.get("ETL-LOAD")
		.incrementer(new RunIdIncrementer())
		.start(step)
		.build();
	}

    @Bean
    public FlatFileItemReader<Users> itemReader() {
    	FlatFileItemReader<Users> flatFileItemReader =  new FlatFileItemReader<>();
    	flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
    	flatFileItemReader.setName("CSV-Reader");
    	flatFileItemReader.setLinesToSkip(1);
    	flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;

    }

    @Bean
    public LineMapper<Users> lineMapper() {

        DefaultLineMapper<Users> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer =  new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "dept", "salary"});

        BeanWrapperFieldSetMapper<Users> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Users.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

}
