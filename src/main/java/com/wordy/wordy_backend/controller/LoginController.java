package com.wordy.wordy_backend.controller;


import com.wordy.wordy_backend.model.Usuario;
import com.wordy.wordy_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite que tu HTML de NetBeans se conecte
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String nombreUsuario = request.get("usuario");
        String contrasena = request.get("contrasena");

        Optional<Usuario> userOpt = usuarioRepository.findByNombreUsuarioIgnoreCase(nombreUsuario);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("ok", false, "error", "Usuario no encontrado"));
        }

        Usuario user = userOpt.get();

        if (user.getActivo() != 1) {
            return ResponseEntity.status(403).body(Map.of("ok", false, "error", "Cuenta inactiva"));
        }

        // Comparación simple de texto plano para tu demo
        if (!contrasena.equals(user.getContrasenaHash())) { 
            return ResponseEntity.status(401).body(Map.of("ok", false, "error", "Contraseña incorrecta"));
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("ok", true);
        respuesta.put("usuario", user.getNombre());
        respuesta.put("id_usuario", user.getIdUsuario());
        respuesta.put("id_rol", user.getIdRol());
        
        return ResponseEntity.ok(respuesta);
    }
        @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Map<String, String> request) {
        String nombre = request.get("nombre");
        String usuario = request.get("usuario");
        String contrasena = request.get("contrasena");
        String tipoCuenta = request.get("tipo_cuenta");

        // Validar que el usuario no exista ya
        Optional<Usuario> existe = usuarioRepository.findByNombreUsuarioIgnoreCase(usuario);
        if (existe.isPresent()) {
            return ResponseEntity.status(400).body(Map.of("ok", false, "error", "El nombre de usuario ya está en uso"));
        }

        // Asignar ID de rol (3 = NINO, 1 = TUTOR)
        int idRol = tipoCuenta.equals("tutor") ? 1 : 3;

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setNombreUsuario(usuario);
        nuevoUsuario.setContrasenaHash(contrasena); // En un proyecto real, aquí se encripta
        nuevoUsuario.setIdRol(idRol);
        nuevoUsuario.setActivo(1);

        try {
            // Guardamos en la tabla USUARIO
            usuarioRepository.save(nuevoUsuario);

            // NOTA: Para guardar el perfil del niño o el tutor, se necesitaría
            // otro repositorio (PerfilRepository). Para no hacerlo tan largo, 
            // solo guardamos el usuario por ahora. ¡Ya puedes loguearte con él!

            return ResponseEntity.ok(Map.of("ok", true, "mensaje", "Usuario registrado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("ok", false, "error", "Error al guardar en la base de datos"));
        }
    }
}