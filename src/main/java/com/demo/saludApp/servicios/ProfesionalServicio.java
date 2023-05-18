package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.ProfesionalRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Damian
 */
@Service
public class ProfesionalServicio {


    private ArrayList<ObraSocial> obraSocialAceptada;
    
    @Autowired
    private ProfesionalRepositorio pr;  
    
        @Transactional
    public void crearProfesional(String nombre, String email, String password, Integer matricula, Integer calificacion, String consultas, String locacion, String detalleEspecialidad, Turnos turnos, Especialidad especialidad, ObraSocial obraSocialAceptada) throws MiException, ParseException {

        validar(nombre, email, password, matricula, locacion, especialidad, obraSocialAceptada);

        Profesional profesional = new Profesional();

        profesional.setNombre(nombre);
        profesional.setEmail(email);
        profesional.setPassword(password);
        profesional.setMatricula(matricula);
        profesional.setLocacion(locacion);
        profesional.setEspecialidad(especialidad);
        profesional.setObraSocialAceptada(obraSocialAceptada);

        pr.save(profesional);
    }

    @Transactional
    public void modificarProfesional() throws MiException, ParseException {

        validar();

        Optional<Profesional> respuesta = pr.findById(id);

        if (respuesta.isPresent()) {

            Profesional profesional = profesional.get();

            profesional.setNombre(nombre);
            profesional.setEmail(email);
            profesional.setPassword(password);
            profesional.setMatricula(matricula);
            profesional.setLocacion(locacion);
            profesional.setEspecialidad(especialidad);
            profesional.setObraSocialAceptada(obraSocialAceptada);
            
            pr.save(profesional);

        }
    }

    public void eliminarPaciente(String id) throws MiException {

//        if (dni.isEmpty() || dni == null) {
//            throw new MiException("el dni no puede ser nulo o estar vacio"); //
//        }
        Optional<Profesional> respuesta = pr.findById(id);

        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();
            pr.delete(profesional);
        }
    }

    private void validar() throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio"); //
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio"); //
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("el password no puede ser nulo o estar vacio"); //
        }

        if (matricula.isEmpty() || matricula == null) {
            throw new MiException("el dni no puede ser nulo o estar vacio"); //
        }
        if (locacion.isEmpty() || locacion == null) {
            throw new MiException("la locacion no puede ser vacia o nula");
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
     


