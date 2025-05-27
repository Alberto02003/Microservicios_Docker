package com.example.api_productos.servicios;

import com.example.api_productos.dto.ProductoDTO;
import com.example.api_productos.entidades.Producto;
import com.example.api_productos.repositorios.ProductosRepositorio;
import com.example.api_productos.entidades.TipoProducto;
import com.example.api_productos.repositorios.TipoProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductosRepositorio productoRepository;

    @Autowired
    private TipoProductoRepositorio tipoProductoRepository;

    public List<ProductoDTO> obtenerProductosPorCategoria(String categoriaNombre) {
        TipoProducto tipoProducto = tipoProductoRepository.findByNombre(categoriaNombre);

        if (tipoProducto != null) {
            List<Producto> productos = productoRepository.findByTipoProductoNombre(categoriaNombre);
            return productos.stream()
                    .map(producto -> new ProductoDTO(producto.getNombre(), producto.getFotoRuta(), producto.getPrecio()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
