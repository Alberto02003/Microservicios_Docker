package com.example.api_user.repositorios;

import com.example.api_user.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByName(String name);
    Optional<Usuario> findByEmail(String email);

}
