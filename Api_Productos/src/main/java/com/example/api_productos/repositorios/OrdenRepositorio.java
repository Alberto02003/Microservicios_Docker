package com.example.api_productos.repositorios;

import com.example.api_productos.entidades.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepositorio extends JpaRepository<Orden, Long> {
}
