package com.example.spring_batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.spring_batch.model.Customer;
import com.example.spring_batch.processor.CustomerProcessor;
import com.example.spring_batch.service.ExternalApiService;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final ExternalApiService externalApiService;

    public BatchConfig(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory,
            ExternalApiService externalApiService) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.externalApiService = externalApiService;
    }

    @Bean
    public JpaPagingItemReader<Customer> reader() {
        return new JpaPagingItemReaderBuilder<Customer>()
                .name("customerReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM Customer c WHERE c.status = 'PENDING'")
                .pageSize(10)
                .build();
    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor(externalApiService);
    }

    @Bean
    public Step processCustomersStep() {
        return new StepBuilder("processCustomersStep", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(chunk -> {
                    for (Customer customer : chunk) {
                        // Update status after successful processing
                        customer.setStatus("PROCESSED");
                    }
                })
                .build();
    }

    @Bean
    public Job processCustomersJob() {
        return new JobBuilder("processCustomersJob", jobRepository)
                .start(processCustomersStep())
                .build();
    }
} 