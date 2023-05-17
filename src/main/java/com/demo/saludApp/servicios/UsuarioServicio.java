package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Rol;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ILMAN
 */
@Service
public class UsuarioServicio {
    
     @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
    
     @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2, Integer telefono) throws MiException {

        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
//        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.PACIENTE);
        usuario.setTelefono(telefono);
        usuario.setActivo(true);
        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
       
    }
    
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }
    
    @Transactional
    public void activarSuspender(String id) {
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (usuario.isActivo()) {

                usuario.setActivo(false);

            } else {
                usuario.setActivo(true);
            }
        }
    }
    
    @Transactional
    public void eliminarUsuario(String id) {
       Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuarioRepositorio.delete(respuesta.get());
        }
    }
    
    private void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }
}