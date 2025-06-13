package com.examen.hazavao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import java.util.List;

@Service
public class HazavaoService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "apiHere";

    public String getDefinition(String teny) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Préparer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.set("Content-Type", "application/json");

            // Préparer le prompt
            String prompt = "Manomeza famaritana fohy sy mazava amin'ny teny malagasy ho an'ny teny \"" + teny + "\". Ny valiny dia tsy maintsy malagasy tanteraka.";

            // Préparer le body de la requête
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    ),
                    "max_tokens", 150,
                    "temperature", 0.7
            );

            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(requestBody);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            // Faire l'appel à l'API
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Extraire la réponse
            JsonNode responseNode = mapper.readTree(response.getBody());
            String definition = responseNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return definition.trim();

        } catch (Exception e) {
            return "Tsy afaka nahazo ny famaritana an'io teny io. Misy olana: " + e.getMessage();
        }
    }
}