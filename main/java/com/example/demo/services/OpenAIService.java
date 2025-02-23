package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class OpenAIService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    @Value("${openai.api.key}") // Inject API key from application.properties
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    // Constructor injection for RestTemplate
    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateResponse(String userMessage) {
        logger.info("Generating response for message: {}", userMessage);

        // Build the request payload for chat/completions
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", List.of(
            Map.of("role", "user", "content", userMessage)
        ));

        // Setup headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            // Make POST request to OpenAI
            Map<String, Object> response = restTemplate.postForObject(OPENAI_API_URL, entity, Map.class);
            logger.info("OpenAI API Response: {}", response);

            // Parse the AI text from the "choices" array
            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> messageMap = (Map<String, Object>) firstChoice.get("message");
                    if (messageMap != null) {
                        return (String) messageMap.get("content");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error calling OpenAI API: {}", e.getMessage());
            return "Error: Unable to generate a response. Please try again later.";
        }

        // If no "choices" or empty content
        return "No response from AI.";
    }
}
