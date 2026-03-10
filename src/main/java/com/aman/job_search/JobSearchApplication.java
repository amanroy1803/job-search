package com.aman.job_search;

import com.aman.job_search.service.JobProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobSearchApplication implements CommandLineRunner {

    @Autowired
    private JobProcessorService processor;

    public static void main(String[] args) {
        SpringApplication.run(JobSearchApplication.class, args);
    }

    @Override
    public void run(String... args) {
        processor.runMonitor();
        System.exit(0);
    }
}
