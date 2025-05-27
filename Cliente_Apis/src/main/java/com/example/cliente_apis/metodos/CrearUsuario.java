package com.example.cliente_apis.metodos;


import com.example.cliente_apis.alertas.Alertas;
import com.example.cliente_apis.modelos.Usuario;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class CrearUsuario {
    private static final String API_URL = "http://localhost:8082/api/users";

   private static  Alertas alertas = new Alertas();
   private static CrearUsuario crearUsuario = new CrearUsuario();

    public static void CrearUsuarios(String name, String email, String password, String role) {
        try {
            System.out.println("Enviando solicitud...");

            Usuario usuario = new Usuario(name, email, password, role);
            Gson gson = new Gson();
            String jsonBody = gson.toJson(usuario);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Código de respuesta: " + response.statusCode());
            System.out.println("Respuesta: " + response.body());

            if (response.statusCode() == 201) {
                System.out.println("✅ Usuario creado correctamente");
                alertas.mostrarAlertaInformacion("✅ Usuario creado correctamente", "Accediendo al Inicio de Sesion");

                try {
                    FXMLLoader loader = new FXMLLoader(CrearUsuario.class.getResource("/com/example/cliente_apis/fxml/InicioSesion.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) Stage.getWindows().stream()
                            .filter(Window::isShowing)
                            .findFirst()
                            .orElse(null);

                    if (stage != null) {
                        Scene siguiente = new Scene(root);
                        stage.setScene(siguiente);
                        stage.show();
                    } else {
                        System.out.println("No se encontró una ventana activa.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("❌ Error al crear usuario: " + response.statusCode());
                alertas.mostrarAlertaError("❌ Error al crear usuario:","La contraseña debe tener al menos 6 caracteres." + + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}