package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
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
     
    
     @Transactional
    public void crearProfesional(String nombre,String apellido, String email, String password, Integer matricula, Integer calificacion, String consultas, String locacion, String detalleEspecialidad,ArrayList<String> turnos, Especialidad especialidad, ArrayList<ObraSocial> obraSocialAceptada) throws MiException, ParseException {

//        validar(nombre, apellido, email, password, matricula, locacion, especialidad, obraSocialAceptada);

        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setEmail(email);
        profesional.setPassword(password);
        profesional.setMatricula(matricula);
        profesional.setLocacion(locacion);
        profesional.setEspecialidad(especialidad);
        profesional.setObraSocialAceptada(obraSocialAceptada);

        pr.save(profesional);
    }

   @org.springframework.transaction.annotation.Transactional
    public void modificarProfesional(MultipartFile archivo, String idUsuario, String nombre, String apellido, String email, String password, String dni, Integer telefono, String fechaNacimiento, Genero genero, ArrayList obrasocial, String locacion, String detalleEspecialidad, Especialidad especialidad, Integer matricula) throws MiException, ParseException {
        
        Optional<Profesional> respuesta = pr.findById(idUsuario);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setEmail(email);
            profesional.setPassword(new BCryptPasswordEncoder().encode(password));
            profesional.setEspecialidad(especialidad);
            profesional.setMatricula(matricula);
            profesional.setTelefono(telefono);
            profesional.setLocacion(locacion);
            
            String idImagen = null;

            if (profesional.getImagen() != null) {
                idImagen = profesional.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            profesional.setImagen(imagen);

            pr.save(profesional);
        }
    }


    private void validar(String nombre, String email, String password, Integer matricula, String locacion, Especialidad especialidad, ArrayList<ObraSocial> obraSocialAceptada) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio"); //
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio"); //
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("el password no puede ser nulo o estar vacio"); //
        }
        if ( matricula == null) {
            throw new MiException("la matricula no puede ser nulo o estar vacio"); //
        }
        if (locacion.isEmpty() || locacion == null) {
            throw new MiException("la locacion no puede ser vacia o nula");
        }
        if (especialidad.toString().isEmpty() || especialidad == null) {
            throw new MiException("la especialidad no puede ser vacia o nula");
        }
        if (obraSocialAceptada.isEmpty() || obraSocialAceptada == null) {
            throw new MiException("la obraSocialAceptada no puede ser vacia o nula");
        }

    }
    public List<Profesional> listarProfesionales() {
        List<Profesional> profesionales = new ArrayList();
        profesionales = pr.findAll();
    return profesionales;
    }
    
    public Profesional getOne(String nombre){
    return pr.buscarNombre(nombre);
    }
        
    public List<Profesional> filtrarEspecialidad(String especialidad) {
        List<Profesional> profesionales = new ArrayList();
        profesionales = pr.buscarEspecialidad(especialidad);
    return profesionales;
    }
    
  }
     


