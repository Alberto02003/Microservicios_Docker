package com.example.cliente_apis.controladores;

import com.example.cliente_apis.sesiones.SesionUsuario;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


public class ProductoCardController {


    SesionUsuario sesion = new SesionUsuario();
    public AnchorPane contenedor;
    public ImageView imagenProducto;
    public Label precioProducto;
    public Label nombreProducto;

    private boolean seleccionado = false;

    public void setProductoData(String imagenUrl, String precio, String nombre) {
        imagenProducto.setImage(new Image(imagenUrl));
        precioProducto.setText(precio + " $");
        nombreProducto.setText(nombre);
    }

    @FXML
    private void seleccionarTarjeta() {
        seleccionado = !seleccionado;
        if (seleccionado) {
            contenedor.setStyle("-fx-background-color:#37475A; -fx-border-color: #232F3E; -fx-border-width: 2;");
            precioProducto.setTextFill(Color.WHITE);
            nombreProducto.setTextFill(Color.WHITE);
            escribirDatosEnArchivo(sesion.nombreUsuario, nombreProducto.getText(), precioProducto.getText());
        } else {
            contenedor.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");
            precioProducto.setTextFill(Color.BLACK);
            nombreProducto.setTextFill(Color.BLACK);
        }
    }

    private void escribirDatosEnArchivo(String nombreCliente, String nombreProducto, String precioProducto) {
        String rutaArchivo = "C:\\Users\\alber\\Documents\\FTP\\Transacciones\\compras.txt";
        File archivo = new File(rutaArchivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write("Cliente: " + nombreCliente + " | Producto: " + nombreProducto + " | Precio: " + precioProducto);
            writer.newLine(); // Añadimos una nueva línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}