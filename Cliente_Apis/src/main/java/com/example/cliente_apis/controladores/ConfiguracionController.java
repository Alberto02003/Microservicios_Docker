package com.example.cliente_apis.controladores;



import com.example.cliente_apis.alertas.Alertas;
import com.example.cliente_apis.modelos.TipoProducto;
import com.example.cliente_apis.respuestaApiUsuario.ObtenerIdTipoProducto;
import com.example.cliente_apis.respuestaApiUsuario.ObtenerNombresTiposProductos;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;



public class ConfiguracionController {

    public Label ruta;
    public AnchorPane contenedorProductos;
    public AnchorPane contenedorCategorias;
    public ObtenerIdTipoProducto obtenerIdTipoProducto = new ObtenerIdTipoProducto();
    public static final String API_TIPOS_PRODUCTOS = "http://localhost:8083/api/tipos-productos/nombres";
    public static final String API_PRODUCTOS = "http://localhost:8083/api/productos";
    public static final String API_TIPOS = "http://localhost:8083/api/tipos-productos";
    public TextField nombreCategoria;
    public ComboBox CategoriaProducto;
    public TextField nombreProducto;
    public TextField precioProducto;
    private Alertas alerta = new Alertas();
    ObtenerNombresTiposProductos obtenerNombres = new ObtenerNombresTiposProductos();

    @FXML
    public void initialize() {
        cargarTiposDeProductos();
    }

    private void cargarTiposDeProductos() {
        ScheduledService<List<String>> service = new ScheduledService<>() {
            @Override
            protected Task<List<String>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<String> call() throws Exception {
                        return obtenerNombres.obtenerNombresDeTiposDeProductos();
                    }
                };
            }
        };

        service.setPeriod(Duration.seconds(5));
        service.setOnSucceeded(e -> {
            List<String> nombres = service.getValue();
            CategoriaProducto.getItems().clear();
            CategoriaProducto.getItems().addAll(nombres);
        });

        service.start();
    }



    @FXML
    public void agregarNuevaCategoria(ActionEvent event) {
        try {
            String nombre = nombreCategoria.getText();
            if (nombre.isEmpty()) {
                alerta.mostrarAlertaError("Error" ,"El nombre no puede estar vacio");
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(new TipoProducto(nombre));
            URL url = new URL(API_TIPOS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonRequest.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                alerta.mostrarAlertaConfirmacion("Categoria","Categoria agregada correctamente");
            } else {
                alerta.mostrarAlertaError("Error", "No se pudo agregar la categoría. Código: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            alerta.mostrarAlertaError("Error", "Ocurrió un error al agregar la categoría.");
        }
    }

    @FXML
    public void seleccionarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) ruta.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            ruta.setText(archivoSeleccionado.getAbsolutePath());
        } else {
            ruta.setText("Ningún archivo seleccionado");
        }
    }

    public void Volver(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/Aplicacion.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ruta.getScene().getWindow();
        Scene siguiente = new Scene(root);
        stage.setScene(siguiente);
    }

    public void AgregarProductos(ActionEvent actionEvent) {
        contenedorProductos.setVisible(true);
        contenedorCategorias.setVisible(false);
    }

    public void AgregarTiposProductos(ActionEvent actionEvent) {
        contenedorProductos.setVisible(false);
        contenedorCategorias.setVisible(true);

    }

    public void CrearProducto(ActionEvent event) {
        try {
            String nombre = nombreProducto.getText();
            String precioTexto = precioProducto.getText();
            String categoriaSeleccionada = (String) CategoriaProducto.getValue();
            String fotoRuta = ruta.getText();

            if (nombre.isEmpty() || precioTexto.isEmpty() || categoriaSeleccionada == null || fotoRuta.isEmpty()) {
                alerta.mostrarAlertaError("Error", "Todos los campos son obligatorios.");
                return;
            }

            BigDecimal precio;
            try {
                precio = new BigDecimal(precioTexto);
                if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                    alerta.mostrarAlertaError("Error", "El precio debe ser mayor que cero.");
                    return;
                }
            } catch (NumberFormatException e) {
                alerta.mostrarAlertaError("Error", "El precio debe ser un número válido.");
                return;
            }

            Long tipoProductoId = obtenerIdTipoProducto.obtenerIdTipoProducto(categoriaSeleccionada);
            if (tipoProductoId == null) {
                alerta.mostrarAlertaError("Error", "No se pudo obtener el ID del tipo de producto.");
                return;
            }

            fotoRuta = fotoRuta.replace("\\", "\\\\");

            String jsonInputString = "{"
                    + "\"nombre\": \"" + nombre + "\","
                    + "\"precio\": " + precio + ","
                    + "\"tipoProducto\": { \"id\": " + tipoProductoId + " },"
                    + "\"fotoRuta\": \"" + fotoRuta + "\""
                    + "}";

            System.out.println("JSON enviado: " + jsonInputString);

            URL url = new URL(API_PRODUCTOS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                alerta.mostrarAlertaConfirmacion("Éxito", "Producto creado correctamente.");
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String responseLine;
                    StringBuilder response = new StringBuilder();
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Respuesta de error: " + response.toString());
                }
                alerta.mostrarAlertaError("Error", "No se pudo crear el producto. Código: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            alerta.mostrarAlertaError("Error", "Ocurrió un error al crear el producto: " + e.getMessage());
        }
    }
}

