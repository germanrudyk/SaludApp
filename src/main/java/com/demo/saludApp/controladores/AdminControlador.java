package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
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
    private UsuarioServicio usuarioS;

    @Autowired
    private ProfesionalServicio profesionalS;
    
    @Autowired
    private PacienteServicio pacienteS;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("")
    public String panelAdministrativo(ModelMap modelo) {
        
        List<Paciente> pacientes = pacienteS.listar();
        modelo.addAttribute("pacientes", pacientes);
        
        List<Profesional> profesionales = profesionalS.listar();
        modelo.addAttribute("profesionales", profesionales);
        
        return "admin.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar") //asigna solicitudes HTTP GET
    public String registrar(ModelMap modelo) {
        
        return "admin.html";
    }
    
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
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificacion")
    public String modificacion(MultipartFile archivo, @PathVariable String idUsuario,@PathVariable String nombre,@PathVariable String apellido,@PathVariable Integer telefono,@PathVariable String email,@PathVariable String password,@PathVariable Integer matricula,@PathVariable String locacion,@PathVariable Especialidad especialidad,@PathVariable String detalleEspecialidad,@PathVariable List<String> obrasocial,@PathVariable Double calificacion,@PathVariable List<Consulta> consultas,@PathVariable Boolean activo, ModelMap modelo) {

        try {
            profesionalS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, password, matricula, locacion, especialidad, detalleEspecialidad, obrasocial, calificacion, consultas, activo);
            modelo.put("exito", "Modificación exitosa");

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/admin"; 

        }
        return "redirect:/admin"; 
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {
    usuarioS.eliminar(id);
         return "redirect:/admin/";
    }   
}
