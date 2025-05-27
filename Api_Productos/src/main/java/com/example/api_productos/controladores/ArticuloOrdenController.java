package com.example.api_productos.controladores;

import com.example.api_productos.entidades.ArticuloOrden;
import com.example.api_productos.repositorios.ArticuloOrdenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-orden")
public class ArticuloOrdenController {

    @Autowired
    private ArticuloOrdenRepositorio articuloOrdenRepositorio;

    @GetMapping
    public List<ArticuloOrden> obtenerTodos() {
        return articuloOrdenRepositorio.findAll();
    }

    @PostMapping
    public ArticuloOrden crear(@RequestBody ArticuloOrden articuloOrden) {
        return articuloOrdenRepositorio.save(articuloOrden);
    }

    @GetMapping("/{id}")
    public ArticuloOrden obtenerPorId(@PathVariable Long id) {
        return articuloOrdenRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Artículo de orden no encontrado"));
    }

    @PutMapping("/{id}")
    public ArticuloOrden actualizar(@PathVariable Long id, @RequestBody ArticuloOrden articuloOrden) {
        articuloOrden.setId(id);
        return articuloOrdenRepositorio.save(articuloOrden);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        articuloOrdenRepositorio.deleteById(id);
    }
}
