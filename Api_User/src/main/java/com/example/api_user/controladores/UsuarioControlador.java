package com.example.api_user.controladores;

import com.example.api_user.entidades.Usuario;
import com.example.api_user.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsuarioControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioRepositorio.findAll();
    }


    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody LoginRequest loginRequest) {
        return usuarioRepositorio.findByName(loginRequest.getName())
                .map(usuario -> {
                    if (loginRequest.getPassword().equals(usuario.getPassword())) {

                        Map<String, String> response = new HashMap<>();
                        response.put("message", "Usuario autenticado correctamente");
                        response.put("role", usuario.getRole().toString());

                        return ResponseEntity.ok(response);
                    } else {

                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("message", "Contraseña incorrecta");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
                    }
                })
                .orElseGet(() -> {

                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("message", "Usuario no encontrado");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                });
    }



    static class LoginRequest {
        private String name;
        private String password;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        return usuarioRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Usuario> getUserByName(@PathVariable String name) {
        return usuarioRepositorio.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario user) {
        if (usuarioRepositorio.findByName(user.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepositorio.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario updatedUser) {
        return usuarioRepositorio.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setRole(updatedUser.getRole());
                    return ResponseEntity.ok(usuarioRepositorio.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (usuarioRepositorio.existsById(id)) {
            usuarioRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}