package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
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

/**
 *
 * @author Damian
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/profesional") //Mapea la ruta de la petición y el método del controlador
public class ProfesionalControlador {
    
    @Autowired
    private ProfesionalServicio ps;
        
    @GetMapping("/registrar") //asigna solicitudes HTTP GET
    public String registrar(ModelMap modelo) {
        
        return "profesional_registrar.html";
    }
    
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam Integer matricula, @RequestParam String locacion, @RequestParam Especialidad especialidad,@RequestParam String detalleEspecialidad, @RequestParam List<String> obraSocialAceptada,ModelMap modelo) throws Exception {
        //@RequestParam vincula los parámetros de una petición HTTP a los argumentos de un método
        try {
            ps.crearProfesional(nombre, email, password, matricula, locacion, especialidad, detalleEspecialidad, obraSocialAceptada);
            modelo.put("exito", "Paciente registrado con exito");
        } catch (Exception ex) {            
            modelo.put("error", ex.getMessage());
            return "paciente_registrar.html";
            
        }
        return "profesional_registrar.html";        
    }
    
    @GetMapping("/modificar/{nombre}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        
        modelo.put("modificar", ps.getOne(id));
        
        return "profesional_modificar.html";
    }
    
    @PostMapping("/modificacion")
    public String modificacion(@RequestParam String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam Integer matricula, @RequestParam String locacion,@RequestParam Especialidad especialidad, @RequestParam List<String> obraSocialAceptada,ModelMap modelo) {
        
        try {           
            ps.modificarProfesional(id, nombre, email, password, matricula, locacion, especialidad, locacion, obraSocialAceptada);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", ps.getOne(id));
            
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
