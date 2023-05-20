package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
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
 * @author German
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/paciente") //Mapea la ruta de la petición y el método del controlador
public class PacienteControlador {    
    
    @Autowired
    private PacienteServicio ps;
    
    @Autowired
    private ProfesionalServicio profesionalServicio; 
    
    @GetMapping("") //asigna solicitudes HTTP GET
    public String vistaPaciente(ModelMap modelo) {
        
//        modelo.put("registrar", "pagina de registro");
         List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        
        return "paciente.html";
    }
    
    
    @GetMapping("/modificar/{email}")
    public String modificar(@PathVariable String email, ModelMap modelo) {
        
        modelo.put("modificar", ps.buscarPorEmail(email));
        
        return "paciente_modificar.html";
    }
    
    @PostMapping("/modificacion")
    public String modificacion(@RequestParam String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String dni, @RequestParam Genero genero, @RequestParam ObraSocial obraSocial, @RequestParam String fechaNacimiento, ModelMap modelo, MultipartFile archivo) {
        
        try {           
            ps.modificarPaciente(archivo, dni, nombre, email, email, password, dni, fechaNacimiento, genero, obraSocial);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", ps.getOne(id));
            
        } catch (Exception ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("modificar", ps.getOne(id));
            return "paciente_modificar";
            
        }
        return "paciente_modificar";
    }    
}
