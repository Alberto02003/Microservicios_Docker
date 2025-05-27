package com.example.cliente_apis.controladores;

import com.example.cliente_apis.alertas.Alertas;
import com.example.cliente_apis.modelos.Producto;
import com.example.cliente_apis.modelos.ProductoDTO;
import com.example.cliente_apis.respuestaApiUsuario.ProductoService;
import com.example.cliente_apis.sesiones.SesionUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javafx.geometry.Insets;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AplicacionController implements Initializable {

    public ImageView imagenes;
    public TextField Buscar;
    private SesionUsuario sesionUsuario = new SesionUsuario();
    private Alertas alertas = new Alertas();
    public FlowPane contenedorProductos;
    public Button config;
    private ProductoService productoService = new ProductoService();

    private int index = 0;

    private final List<Producto> productos = consultarProductosDesdeAPI();

    private final String[] imagen = {
            "/com/example/cliente_apis/images/fondo1.jpg",
            "/com/example/cliente_apis/images/fondo2.jpg",
            "/com/example/cliente_apis/images/fondo3.jpg",
            "/com/example/cliente_apis/images/fondo4.jpg",
            "/com/example/cliente_apis/images/fondo5.jpg",
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(sesionUsuario.rolUsuario);


        if (imagenes == null) {
            System.out.println("Error: El ImageView no está enlazado con el FXML.");
            return;
        }

        cambiarImagen();


        for (Producto producto : productos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/Producto_Card.fxml"));
                AnchorPane card = loader.load();

                ProductoCardController controller = loader.getController();
                controller.setProductoData(producto.getFotoRuta(), producto.getPrecio().toString(), producto.getNombre());

                contenedorProductos.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> cambiarImagen()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        if (sesionUsuario.rolUsuario.equals("CLIENTE")){
            config.setDisable(true);
            alertas.mostrarAlertaAdvertencia("Advertencia" , "Al ser Cliente no puede acceder a Configuracion");
        }

    }

    @FXML
    public void buscarProductos(ActionEvent event) {
        String categoriaNombre = Buscar.getText();

        if (categoriaNombre.isEmpty()) {
            alertas.mostrarAlertaError("Campo vacío", "Por favor, ingrese una categoría.");
            return;
        }

        List<ProductoDTO> productos = productoService.obtenerProductosPorCategoria(categoriaNombre);

        if (productos.isEmpty()) {
            alertas.mostrarAlertaAdvertencia("Sin resultados", "No se encontraron productos para esta categoría.");
            return;
        }

        contenedorProductos.getChildren().clear();

        for (ProductoDTO producto : productos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/Producto_Card.fxml"));
                AnchorPane card = loader.load();

                ProductoCardController controller = loader.getController();
                controller.setProductoData(producto.getFotoRuta(), producto.getPrecio().toString(), producto.getNombre());

                contenedorProductos.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cambiarImagen() {
        URL imageUrl = getClass().getResource(imagen[index]);
        if (imageUrl != null) {
            imagenes.setImage(new Image(imageUrl.toExternalForm()));
            System.out.println("Imagen cambiada: " + imagen[index]);
        } else {
            System.out.println("Error: No se encontró la imagen " + imagen[index]);
        }

        index = (index + 1) % imagen.length;
    }


    public void Configuracion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/Configuracion.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) imagenes.getScene().getWindow();
        Scene siguiente = new Scene(root);
        stage.setScene(siguiente);
    }

    private List<Producto> consultarProductosDesdeAPI() {
        List<Producto> productos = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/api/productos/info"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            productos = List.of(mapper.readValue(response.body(), Producto[].class));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return productos;
    }
}
