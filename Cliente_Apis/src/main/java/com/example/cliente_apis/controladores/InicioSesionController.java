package com.example.cliente_apis.controladores;

import com.example.cliente_apis.alertas.Alertas;
import com.example.cliente_apis.respuestaApiUsuario.LoginRespuesta;
import com.example.cliente_apis.sesiones.SesionUsuario;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InicioSesionController {
    private static final String API_URL = "http://localhost:8082/api/users/verify";

    Alertas alerta = new Alertas();
    public  javafx.scene.control.TextField name;
    public  PasswordField psswd;


    public void VerificarSesion(ActionEvent actionEvent) {
        try {
            if (name.getText().isEmpty() || psswd.getText().isEmpty()) {
                alerta.mostrarAlertaError("Campos vacíos", "Introduzca los datos correspondientes");
                return;
            }

            if (psswd.getText().length() < 6) {
                alerta.mostrarAlertaError("Contraseña corta", "La contraseña debe tener al menos 6 caracteres.");
                return;
            }

            LoginRespuesta loginRequest = new LoginRespuesta(name.getText(), psswd.getText());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequestBody = objectMapper.writeValueAsString(loginRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Respuesta: " + response.body());


                JsonNode jsonResponse = objectMapper.readTree(response.body());
                String mensaje = jsonResponse.get("message").asText();
                String rol = jsonResponse.get("role").asText();


                SesionUsuario.nombreUsuario = name.getText();
                SesionUsuario.rolUsuario = rol;

                alerta.mostrarAlertaInformacion("Usuario Confirmado", mensaje + "\nRol: " + rol);


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/Aplicacion.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) psswd.getScene().getWindow();
                Scene siguiente = new Scene(root);
                stage.setScene(siguiente);
            } else {
                System.out.println("Error: " + response.body());
                alerta.mostrarAlertaError("Usuario no encontrado", "Vuelva a intentarlo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CrearCuenta(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/CrearUsuario.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) psswd.getScene().getWindow();
        Scene siguiente = new Scene(root);
        stage.setScene(siguiente);

    }
}