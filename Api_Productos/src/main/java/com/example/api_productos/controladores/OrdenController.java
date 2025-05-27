package com.example.api_productos.controladores;

import com.example.api_productos.entidades.Orden;
import com.example.api_productos.repositorios.OrdenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @GetMapping
    public List<Orden> obtenerTodos() {
        return ordenRepositorio.findAll();
    }

    @PostMapping
    public Orden crear(@RequestBody Orden orden) {
        return ordenRepositorio.save(orden);
    }

    @GetMapping("/{id}")
    public Orden obtenerPorId(@PathVariable Long id) {
        return ordenRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

    @PutMapping("/{id}")
    public Orden actualizar(@PathVariable Long id, @RequestBody Orden orden) {
        orden.setId(id);
        return ordenRepositorio.save(orden);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ordenRepositorio.deleteById(id);
    }
}

