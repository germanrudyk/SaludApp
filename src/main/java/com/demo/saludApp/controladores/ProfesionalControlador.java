package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Damian
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/profesional") //Mapea la ruta de la petición y el método del controlador
public class ProfesionalControlador {
    
    @Autowired
    private ProfesionalServicio ps;
        
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        
        List<Profesional> profesionales = ps.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
         
    return "panelAdmin.html";
    }
    
    @GetMapping("/filtrarEspecialidad/{especialidad}")
    public String filtrarEspecialidad(@PathVariable String especialidad,ModelMap modelo){
        
        List<Profesional> profesionales = ps.filtrarEspecialidad(especialidad);
        modelo.addAttribute("profesionales", profesionales);
         
    return "panelAdmin.html";
    }
    
    @GetMapping("/ordenarPrecio")
    public String ordenarPrecio(ModelMap modelo){
        
        List<Profesional> profesionales = ps.ordenarPrecio();
        modelo.addAttribute("profesionales", profesionales);
         
    return "panelAdmin.html";
    }
    
    @GetMapping("/ordenarCalificacion")
    public String ordenarCalificacion(ModelMap modelo){
        
        List<Profesional> profesionales = ps.ordenarCalificacion();
        modelo.addAttribute("profesionales", profesionales);
         
    return "panelAdmin.html";
    }

}
