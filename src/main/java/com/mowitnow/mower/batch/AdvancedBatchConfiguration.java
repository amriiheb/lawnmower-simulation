package com.mowitnow.mower.configurations;

import com.mowitnow.mower.JobCompletionNotificationListener;
import com.mowitnow.mower.batch.ConsoleItemWriter;
import com.mowitnow.mower.batch.MowerSequenceProcessor;
import com.mowitnow.mower.batch.SingleLineItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

@Configuration
@EnableBatchProcessing
@Profile("!test") // Ensure this configuration is not used during testing
public class AdvancedBatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(AdvancedBatchConfiguration.class);

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Environment environment;
	private final ResourceLoader resourceLoader;

	@Autowired
	public AdvancedBatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
									  Environment environment, ResourceLoader resourceLoader) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.environment = environment;
		this.resourceLoader = resourceLoader;
	}

	@Bean
	public SingleLineItemReader singleLineItemReader () {
		String inputFile = environment.getProperty("input.file");
		if (inputFile == null || inputFile.isEmpty()) {
			log.error("Input file path is not specified in the application properties.");
			throw new IllegalArgumentException("Input file path must be specified.");
		}
		return new SingleLineItemReader(resourceLoader.getResource(inputFile));
	}

	@Bean
	public MowerSequenceProcessor mowerSequenceProcessor() {
		return new MowerSequenceProcessor();
	}

	@Bean
	public ConsoleItemWriter consoleItemWriter() {
		return new ConsoleItemWriter();
	}

	@Bean
	public Step processMowersStep() {
		return stepBuilderFactory.get("processMowersStep")
				.<String, String>chunk(1) // Adjusted chunk size for improved performance
				.reader(singleLineItemReader())
				.processor(mowerSequenceProcessor())
				.writer(consoleItemWriter())
				.faultTolerant()
				.build();
	}

	@Bean
	public Job mowersJob(JobCompletionNotificationListener listener, Step processMowersStep) {
		return jobBuilderFactory.get("mowersJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(processMowersStep)
				.end()
				.build();
	}
}
