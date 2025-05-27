module com.example.cliente_apis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires reactor.core;
    requires com.fasterxml.jackson.databind;
    exports com.example.cliente_apis.respuestaApiUsuario;
    requires java.net.http;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires spring.webflux;
    opens com.example.cliente_apis.modelos to com.google.gson;
    requires java.desktop;
    requires com.google.gson;
    requires spring.security.crypto;
    requires spring.context;
    exports com.example.cliente_apis.modelos to com.fasterxml.jackson.databind;
    opens com.example.cliente_apis to javafx.fxml;
    exports com.example.cliente_apis;
    exports com.example.cliente_apis.controladores;
    opens com.example.cliente_apis.controladores to javafx.fxml;
}