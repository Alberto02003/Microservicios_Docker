package com.example.cliente_apis.respuestaApiUsuario;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static com.example.cliente_apis.controladores.ConfiguracionController.API_TIPOS_PRODUCTOS;

public class ObtenerNombresTiposProductos {


    public List<String> obtenerNombresDeTiposDeProductos() throws Exception {

        URL url = new URL(API_TIPOS_PRODUCTOS);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Error al conectar con la API: " + conn.getResponseCode());
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder jsonStr = new StringBuilder();
        while (scanner.hasNext()) {
            jsonStr.append(scanner.nextLine());
        }
        scanner.close();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> nombres = objectMapper.readValue(jsonStr.toString(), List.class);

        conn.disconnect();
        return nombres;
    }
}
