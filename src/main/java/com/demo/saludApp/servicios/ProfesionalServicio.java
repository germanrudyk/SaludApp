package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Rol;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.ProfesionalRepositorio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Damian
 */
@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio pr;

    @Autowired
    private ImagenServicio imagenServicio;

    public Profesional getOne(String id) {
        return pr.getOne(id);
    }

    //------------- Listar Profesional -------------
    public List<Profesional> listar() {
        List<Profesional> profesionales = new ArrayList();
        profesionales = pr.findAll();
        return profesionales;
    }

    //------------- Filtrar Profesional -------------
    public List<Profesional> filtrar(String especialidad) {
        List<Profesional> profesionales = new ArrayList();
        profesionales = pr.buscarEspecialidad(especialidad);
        return profesionales;
    }

    //------------- Crear Profesional -------------
    @Transactional
    public void crear(String nombre, String apellido, Integer telefono, String email, String password, String password2, Integer matricula, String locacion, Especialidad especialidad, MultipartFile archivo) throws MiException, ParseException {

        validar(nombre, password, password2);

        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setTelefono(telefono);
        profesional.setEmail(email);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setMatricula(matricula);
        profesional.setLocacion(locacion);
        profesional.setEspecialidad(especialidad);
        profesional.setRol(Rol.PROFESIONAL);
        profesional.setActivo(true);

        if (!(archivo.isEmpty())) {
            Imagen imagen = imagenServicio.guardar(archivo);
            profesional.setImagen(imagen); //Se agrega la imagen 
        }
        pr.save(profesional);
    }

    //------------- Modificar Profesional -------------
    @org.springframework.transaction.annotation.Transactional
    public void modificar(MultipartFile archivo, String idUsuario, String nombre, String apellido, Integer telefono, String email, Integer matricula, String locacion, Especialidad especialidad, Boolean activo) throws MiException, ParseException {

        Optional<Profesional> respuesta = pr.findById(idUsuario);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            profesional.setActivo(activo);
            profesional.setRol(Rol.PROFESIONAL);
            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);
            profesional.setMatricula(matricula);
            profesional.setLocacion(locacion);
            profesional.setEspecialidad(especialidad);

            String idImagen = null;

            if (profesional.getImagen() != null) {
                idImagen = profesional.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            profesional.setImagen(imagen);

            pr.save(profesional);
        }
    }

    //------------- Eliminar Profesional ------------
    public void eliminar(String id) {

        Optional<Profesional> respuesta = pr.findById(id);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            pr.delete(profesional);

        }

    }
    
    public void darDeBaja(String id){
        
        Optional<Profesional> respuesta = pr.findById(id);
        
        if(respuesta.isPresent()){
            
            Profesional profesional = respuesta.get();
            
            profesional.setActivo(false);        
            
            pr.save(profesional);
            
        }
        
    }

    //------------- Descripcion -------------
    @org.springframework.transaction.annotation.Transactional
    public void descripcion(String idUsuario, String detalleEspecialidad) throws MiException, ParseException {

        Optional<Profesional> respuesta = pr.findById(idUsuario);

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setDetalleEspecialidad(detalleEspecialidad);
            pr.save(profesional);
        }
    }

    //------------- Calificar -------------
    @org.springframework.transaction.annotation.Transactional
    public void calificar(String idUsuario, Double calificacion) {

        Optional<Profesional> respuesta = pr.findById(idUsuario);

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            calificacion = (profesional.getCalificacion() + calificacion) / (profesional.getCalificaciones() + 1);
            profesional.setCalificaciones(profesional.getCalificaciones() + 1);
            profesional.setCalificacion(calificacion);
            pr.save(profesional);
        }
    }

    //------------- Validar Profesional -------------
    private void validar(String nombre, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() < 6) {
            throw new MiException("El email no puede ser nulo y debe tener mas de 5 digitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresasdas deben ser iguales");
        }
    }
}
