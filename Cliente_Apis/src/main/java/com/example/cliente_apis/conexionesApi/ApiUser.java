package com.example.cliente_apis.conexionesApi;

import org.springframework.web.reactive.function.client.WebClient;

public class ApiUser {
    private static final String API_URL = "http://localhost:8082/api/users";
    private static final WebClient webClient = WebClient.create(API_URL);

    public static void main(String[] args) {
        String response = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Response: " + response);
    }
}
