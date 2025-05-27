package com.example.api_productos.dto;

import java.math.BigDecimal;

public class ProductoDTO {

    private String nombre;
    private String fotoRuta;
    private BigDecimal precio;

    public ProductoDTO(String nombre, String fotoRuta, BigDecimal precio) {
        this.nombre = nombre;
        this.fotoRuta = fotoRuta;
        this.precio = precio;
    }

    // Getters y setters...
}