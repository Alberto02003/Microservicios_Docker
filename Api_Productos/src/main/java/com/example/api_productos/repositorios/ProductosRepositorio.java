package com.example.api_productos.repositorios;

import com.example.api_productos.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepositorio extends JpaRepository<Producto, Long> {
    List<Producto> findByTipoProductoNombre(String tipoProductoNombre);

}

