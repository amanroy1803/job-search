package com.aman.job_search.model;

import lombok.Data;

import java.util.List;

@Data
public class GreenHouseResponse {

    private List<GreenhouseJob> jobs;

    @Data
    public static class GreenhouseJob {
        private Long id;
        private String title;
        private String absolute_url;
    }
}
