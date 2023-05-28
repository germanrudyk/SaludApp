package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import com.demo.saludApp.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    private UsuarioServicio usuarioS;

    @Autowired
    private ProfesionalServicio profesionalS;
    
    @Autowired
    private PacienteServicio pacienteS;
    
    //------------- Vista General -------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("")
    public String panelAdministrativo(ModelMap modelo) {
        
        List<Paciente> pacientes = pacienteS.listar();
        modelo.addAttribute("pacientes", pacientes);
        
        List<Profesional> profesionales = profesionalS.listar();
        modelo.addAttribute("profesionales", profesionales);
        
        return "admin.html";
    }
    
    //------------- Registro de Profesional -------------
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(@RequestParam String nombre, @RequestParam String apellido,@RequestParam Integer telefono, @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam Integer matricula, @RequestParam String locacion, @RequestParam Especialidad especialidad, ModelMap modelo, MultipartFile archivo) {
        //@RequestParam vincula los parámetros de una petición HTTP a los argumentos de un método
        try {
            profesionalS.crear(nombre, apellido, telefono, email, password, password2, matricula, locacion, especialidad, archivo);
            modelo.put("exito", "Profesional registrado con exito");
        } catch (Exception ex) {            
            modelo.put("error", ex.getMessage());
            return "redirect:/admin";      
           
        }
        return "redirect:/admin";        
    }
    
    //------------- Modificar Profesional -------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificarProfesional")
    public String modificarProfesional(MultipartFile archivo, @RequestParam String idUsuario,@RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer telefono,@RequestParam String email,@RequestParam Integer matricula,@RequestParam String locacion,@RequestParam Especialidad especialidad, @RequestParam Boolean activo, ModelMap modelo) {

        try {
            profesionalS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, matricula, locacion, especialidad, activo);
            modelo.put("exito", "Modificación exitosa");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/admin"; 
        }
        return "redirect:/admin"; 
    }
    
    //------------- Modificar Paciente -------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificarPaciente")
    public String modificarPaciente(MultipartFile archivo,@RequestParam String idUsuario,@RequestParam  String nombre,@RequestParam String apellido,@RequestParam Integer telefono,@RequestParam String email,@RequestParam String dni,@RequestParam String fechaNacimiento,@RequestParam Genero genero,@RequestParam ObraSocial obrasocial,@RequestParam Boolean activo, ModelMap modelo) {

        try {
            pacienteS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, dni, fechaNacimiento, genero, obrasocial, activo);
            modelo.put("exito", "Modificación exitosa");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/admin"; 
        }
        return "redirect:/admin"; 
    }
    
    //------------- Eliminar Usuario -------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/eliminar")
    public String eliminarUsuario(@RequestParam String id, ModelMap modelo) {
    usuarioS.eliminar(id);
         return "redirect:/admin";
    }   
}
