package com.aman.job_search.service;

import com.aman.job_search.model.Job;
import com.aman.job_search.utils.JobFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class JobProcessorService {

    private final JobFetcherService fetcher;
    private final DatabaseService database;
    private final TelegramService telegram;

    public void runMonitor() {

        try {
            List<String> greenhouse = Files.readAllLines(
                    Path.of("companies/greenhouse_companies.txt"));

            List<String> lever = Files.readAllLines(
                    Path.of("companies/lever_companies.txt"));

            List<CompletableFuture<List<Job>>> futures = new ArrayList<>();

            for (String company : greenhouse) {
                futures.add(
                        CompletableFuture.supplyAsync(
                                () -> fetcher.fetchGreenhouseJobs(company)
                        )
                );
            }

            for (String company : lever) {
                futures.add(
                        CompletableFuture.supplyAsync(
                                () -> fetcher.fetchLeverJobs(company)
                        )
                );
            }

            List<Job> jobs = futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .toList();

            process(jobs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(List<Job> jobs) throws Exception {

        for (Job job : jobs) {
            if (!JobFilter.isRelevant(job.getTitle())) continue;

            if (database.exists(job.getId())) continue;

            database.save(job);

            telegram.send(job);
        }
    }
}
