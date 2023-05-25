package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import com.demo.saludApp.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @author ILMAN
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
     @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;
    
    @Autowired
    private PacienteServicio pacienteServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("")
    public String panelAdministrativo(ModelMap modelo) {
        
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);
        
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        
        return "admin.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar") //asigna solicitudes HTTP GET
    public String registrar(ModelMap modelo) {
        
        return "admin.html";
    }
    
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String password, @RequestParam Integer matricula, @RequestParam String locacion, @RequestParam Especialidad especialidad, ModelMap modelo, MultipartFile archivo) {
        //@RequestParam vincula los parámetros de una petición HTTP a los argumentos de un método
        try {
            
            profesionalServicio.crearProfesional(nombre, apellido, email, password, matricula, locacion, especialidad, archivo);
            modelo.put("exito", "Profesional registrado con exito");
        } catch (Exception ex) {            
            modelo.put("error", ex.getMessage());
            return "redirect:/admin";      
            
        }
        return "redirect:/admin";        
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable String id, ModelMap modelo) {
          Usuario usuario =  usuarioServicio.getOne(id);
          modelo.addAttribute("usuario", usuario);
          //ver vista
        return "redirect:/admin/usuarios";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {
    usuarioServicio.eliminarUsuario(id);
         return "redirect:/admin/";
    }
    
       
    
     
}
