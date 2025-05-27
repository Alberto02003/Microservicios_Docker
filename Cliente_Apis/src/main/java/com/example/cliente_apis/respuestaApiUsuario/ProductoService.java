package com.example.cliente_apis.respuestaApiUsuario;

import com.example.cliente_apis.modelos.ProductoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ProductoService {
    private static final String API_BASE_URL = "http://localhost:8083/api";

    private HttpClient client;
    private ObjectMapper objectMapper;

    public ProductoService() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<ProductoDTO> obtenerProductosPorCategoria(String categoriaNombre) {
        try {
            URI uri = new URI(API_BASE_URL + "/productos/categoria/" + categoriaNombre);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, ProductoDTO.class));
            } else {
                System.out.println("Error: " + response.statusCode());
                return List.of();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
