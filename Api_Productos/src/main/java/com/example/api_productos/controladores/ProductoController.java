package com.example.api_productos.controladores;

import com.example.api_productos.dto.ProductoDTO;
import com.example.api_productos.entidades.Producto;
import com.example.api_productos.repositorios.ProductosRepositorio;
import com.example.api_productos.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductosRepositorio productosRepositorio;

    @GetMapping("/categoria/{categoriaNombre}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable String categoriaNombre) {
        List<ProductoDTO> productos = productoService.obtenerProductosPorCategoria(categoriaNombre);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/info")
    public List<Map<String, Object>> obtenerProductosInfo() {
        List<Producto> productos = productosRepositorio.findAll();
        List<Map<String, Object>> productosInfo = new ArrayList<>();

        for (Producto producto : productos) {
            Map<String, Object> info = new HashMap<>();
            info.put("fotoRuta", producto.getFotoRuta());
            info.put("nombre", producto.getNombre());
            info.put("precio", producto.getPrecio());
            productosInfo.add(info);
        }
        return productosInfo;
    }

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productosRepositorio.findAll();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productosRepositorio.save(producto);
    }

    @GetMapping("/{id}")
    public Producto obtenerPorId(@PathVariable Long id) {
        return productosRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        return productosRepositorio.save(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productosRepositorio.deleteById(id);
    }
}

