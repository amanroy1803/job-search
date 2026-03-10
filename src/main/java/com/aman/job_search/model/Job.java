package com.aman.job_search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private String id;
    private String title;
    private String company;
    private String url;
}
