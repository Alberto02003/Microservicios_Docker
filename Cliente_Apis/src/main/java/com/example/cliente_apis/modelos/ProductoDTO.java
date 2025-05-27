package com.example.cliente_apis.modelos;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoRuta() {
        return fotoRuta;
    }

    public void setFotoRuta(String fotoRuta) {
        this.fotoRuta = fotoRuta;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
