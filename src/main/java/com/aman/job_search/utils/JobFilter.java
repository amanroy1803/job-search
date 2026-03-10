package com.aman.job_search.utils;

public class JobFilter {

    public static boolean isRelevant(String title) {

        title = title.toLowerCase();

        return title.contains("software")
                || title.contains("backend")
                || title.contains("engineer")
                || title.contains("developer");
    }
}
