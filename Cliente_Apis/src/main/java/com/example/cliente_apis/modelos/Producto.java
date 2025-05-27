package com.example.cliente_apis.modelos;

import java.math.BigDecimal;

public class Producto {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String fotoRuta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getFotoRuta() {
        return fotoRuta;
    }

    public void setFotoRuta(String fotoRuta) {
        this.fotoRuta = fotoRuta;
    }

    public String getInfo() {
        return "Ruta Imagen: " + fotoRuta + ", Nombre: " + nombre + ", Precio: " + precio;
    }
}
