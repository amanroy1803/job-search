package com.aman.job_search.service;

import com.aman.job_search.model.Job;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TelegramService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String token = System.getenv("TELEGRAM_SECRET");
    private final String chatId = System.getenv("TELEGRAM_CHAT_ID");

    public void send(Job job) {
        String url = "https://api.telegram.org/bot" + token + "/sendMessage";
        String msg = "🚨 New Job Alert\n\n"
                + "Company: " + job.getCompany()
                + "\nRole: " + job.getTitle()
                + "\n\n" + job.getUrl();

        restTemplate.postForObject(
                url,
                Map.of(
                        "chat_id", chatId,
                        "text", msg
                ),
                String.class
        );
    }
}
