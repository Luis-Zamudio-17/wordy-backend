package com.wordy.wordy_backend.repository;

import com.wordy.wordy_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreUsuarioIgnoreCase(String nombreUsuario);
}
