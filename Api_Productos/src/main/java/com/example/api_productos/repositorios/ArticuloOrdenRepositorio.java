package com.example.api_productos.repositorios;

import com.example.api_productos.entidades.ArticuloOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloOrdenRepositorio extends JpaRepository<ArticuloOrden, Long> {
}
