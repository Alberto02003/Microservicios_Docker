package com.example.cliente_apis.controladores;

import com.example.cliente_apis.alertas.Alertas;
import com.example.cliente_apis.metodos.CrearUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class CrearCuentaController {

    public javafx.scene.control.TextField email;

    public javafx.scene.control.TextField name;
    public PasswordField contrasenya;
    public PasswordField contrasenya1;

    private CrearUsuario crearUsuario = new CrearUsuario();

    @FXML
    public  Label Crear;


    @FXML
    private ComboBox rol;

    Alertas alerta = new Alertas();

    public void CrearCuenta(ActionEvent actionEvent){

        if (email.getText().isEmpty() || name.getText().isEmpty() || contrasenya.getText().isEmpty() || contrasenya1.getText().isEmpty()) {
            alerta.mostrarAlertaError("Campos vacios","Introduzca los datos correspondientes");
        }

        if (contrasenya.equals(contrasenya1)) {
           alerta.mostrarAlertaError("Contraseña Inválida","Deben Coincidir");
        }

        if (contrasenya.getLength() < 6){
            alerta.mostrarAlertaError("Contraseña Inválida","La contraseña debe tener al menos 6 caracteres.");
        }

        CrearUsuario.CrearUsuarios(name.getText(), email.getText(),contrasenya.getText(),rol.getValue().toString() );

    }


    public void InicioSesion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cliente_apis/fxml/InicioSesion.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) Crear.getScene().getWindow();
        Scene siguiente = new Scene(root);
        stage.setScene(siguiente);
    }
}
