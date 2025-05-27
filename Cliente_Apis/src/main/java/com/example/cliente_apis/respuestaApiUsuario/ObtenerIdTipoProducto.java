package com.example.cliente_apis.respuestaApiUsuario;

import com.example.cliente_apis.modelos.TipoProducto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.example.cliente_apis.controladores.ConfiguracionController.API_TIPOS;

public class ObtenerIdTipoProducto {
    public Long obtenerIdTipoProducto(String nombreCategoria) {
        try {
            List<TipoProducto> tiposProductos = obtenerListaTiposProductos();
            if (tiposProductos != null) {
                for (TipoProducto tipo : tiposProductos) {
                    if (tipo.getNombre().equals(nombreCategoria)) {
                        return tipo.getId();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TipoProducto> obtenerListaTiposProductos() {
        try {

            URL url = new URL(API_TIPOS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Leer la respuesta
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(connection.getInputStream(), new TypeReference<List<TipoProducto>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
