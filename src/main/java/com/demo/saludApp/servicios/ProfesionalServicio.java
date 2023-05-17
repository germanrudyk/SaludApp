package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.repositorios.ProfesionalRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Damian
 */
@Service
public class ProfesionalServicio {

    @Autowired
    private ProfesionalRepositorio pr;  
    
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
     


