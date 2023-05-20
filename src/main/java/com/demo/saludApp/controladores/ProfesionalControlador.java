package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Damian
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/profesional") //Mapea la ruta de la petición y el método del controlador
public class ProfesionalControlador {
    
    @Autowired
    private ProfesionalServicio ps;
    
    @GetMapping("") //asigna solicitudes HTTP GET
    public String vistaProfesional(ModelMap modelo) {
        
        return "profesional.html";
    }
        
    
    @GetMapping("/modificar/{nombre}")
    public String modificar(@PathVariable String nombre, ModelMap modelo) {
        
        modelo.put("modificar", ps.getOne(nombre));
        
        return "profesional_modificar.html";
    }
    
    @PostMapping("/modificacion")
    public String modificacion(@RequestParam String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam Integer matricula, @RequestParam Integer calificacion, @RequestParam String consultas, @RequestParam String locacion, @RequestParam String detalleEspecialidad, @RequestParam ArrayList<String> turnos, @RequestParam Especialidad especialidad, @RequestParam ArrayList<ObraSocial> obraSocialAceptada,ModelMap modelo, MultipartFile archivo) {
        
        try {           
            ps.modificarProfesional(archivo, locacion, nombre, email, email, password, id, calificacion, locacion, Genero.FEMENINO, obraSocialAceptada, locacion, detalleEspecialidad, especialidad, matricula);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", ps.getOne(nombre));
            
        } catch (Exception ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("modificar", ps.getOne(id));
            return "paciente_modificar";
            
        }
        return "paciente_modificar";
    }
    
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
    
}
