package com.example.cliente_apis.modelos;

public class TipoProducto {
    private Long id;
    private String nombre;


    public TipoProducto() {
    }

    public TipoProducto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoProducto(String nombre) {
        this.nombre = nombre;
    }

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

    @Override
    public String toString() {
        return "TipoProducto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}