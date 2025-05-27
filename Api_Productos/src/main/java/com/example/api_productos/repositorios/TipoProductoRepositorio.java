package com.example.api_productos.repositorios;

import com.example.api_productos.entidades.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoProductoRepositorio extends JpaRepository<TipoProducto, Integer> {
    TipoProducto findByNombre(String nombre);

}

