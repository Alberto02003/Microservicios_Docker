package com.example.api_productos.controladores;
import com.example.api_productos.entidades.TipoProducto;
import com.example.api_productos.repositorios.TipoProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-productos")
public class TipoProductoController {

    @Autowired
    private TipoProductoRepositorio tipoProductoRepositorio;

    @GetMapping
    public ResponseEntity<List<TipoProducto>> obtenerTodos() {
        List<TipoProducto> tipos = tipoProductoRepositorio.findAll();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<String>> obtenerNombres() {

        List<String> nombres = tipoProductoRepositorio.findAll()
                .stream()
                .map(TipoProducto::getNombre)
                .collect(Collectors.toList());

        return ResponseEntity.ok(nombres);
    }

    @PostMapping
    public TipoProducto crear(@RequestBody TipoProducto tipoProducto) {
        return tipoProductoRepositorio.save(tipoProducto);
    }

    @GetMapping("/{id}")
    public TipoProducto obtenerPorId(@PathVariable Integer id) {
        return tipoProductoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
    }

    @PutMapping("/{id}")
    public TipoProducto actualizar(@PathVariable Integer id, @RequestBody TipoProducto tipoProducto) {  // Mantener Integer
        tipoProducto.setId(id);
        return tipoProductoRepositorio.save(tipoProducto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        tipoProductoRepositorio.deleteById(id);
    }
}
