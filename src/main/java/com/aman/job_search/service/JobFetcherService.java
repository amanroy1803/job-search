package com.aman.job_search.service;

import com.aman.job_search.model.GreenHouseResponse;
import com.aman.job_search.model.Job;
import com.aman.job_search.model.LeverPosting;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class JobFetcherService {

    private final RestTemplate restTemplate=new RestTemplate();

    public List<Job> fetchGreenhouseJobs(String company) {

        String url = "https://boards-api.greenhouse.io/v1/boards/" + company + "/jobs";

        GreenHouseResponse response = restTemplate.getForObject(url, GreenHouseResponse.class);

        return response.getJobs().stream()
                .map(job -> new Job(
                        String.valueOf(job.getId()),
                        job.getTitle(),
                        company,
                        job.getAbsolute_url()
                )).toList();
    }

    public List<Job> fetchLeverJobs(String company) {
        String url = "https://api.lever.co/v0/postings/" + company;

        LeverPosting[] response = restTemplate.getForObject(url, LeverPosting[].class);

        return Arrays.stream(response)
                .map(job -> new Job(
                        job.getId(),
                        job.getText(),
                        company,
                        job.getHostedUrl()
                )).toList();
    }
}
