package com.sandbox.springboot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SpringbootApplication.class, args);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootApplication.class, args);
        applicationContext.close();
	}

	@Bean
	protected FlatFileItemReader<String> reader() {
		return new FlatFileItemReaderBuilder<String>() 
			.resource(new ClassPathResource("large_messages.csv"))
			.name("csv-reader")
			.lineMapper((line, lineNumber) -> line)
			.build();
	}

	@Bean
	protected FlatFileItemWriter<String> writer() {
		String fileLocation = "src/main/resources/out.csv";
		return new FlatFileItemWriterBuilder<String>()
			.name("csv-writer")
			.resource(new FileSystemResource(fileLocation)) // output file
			.lineAggregator(item -> item) //
			.build();
	}

	@Bean
	protected Step maskingStep( // all theses parameters are Dependency Injected. each of these parameter must have a bean defined.
		JobRepository jobRepo, 
		PlatformTransactionManager manager, 
		FlatFileItemReader<String> reader, 
		TextItemProcessor processor, 
		FlatFileItemWriter<String> writer
	) {
	return new StepBuilder("masking-step", jobRepo)
			.<String, String>chunk(2, manager) // 
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}
	
	@Bean
	protected Job maskingJob(JobRepository jobRepository, Step maskingStep) {
		return new JobBuilder("masking-job", jobRepository)
			.start(maskingStep)
			.build();
	}
}
