package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.repositorios.UsuarioRepositorio;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    @Autowired
    private PacienteServicio pacienteServicio;
    
    @Autowired
    private UsuarioRepositorio us;
    
    @GetMapping("") //asigna solicitudes HTTP GET
    public String vistaProfesional(HttpSession session, ModelMap modelo) {
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);
        
        return "profesional.html";
    }
        
    
    @GetMapping("/modificar/{nombre}")
    public String modificar(@PathVariable String nombre, ModelMap modelo) {
        
        modelo.put("modificar", us.getOne(nombre));
        
        return "profesional_modificar.html";
    }
    
    @PostMapping("/modificacion")
    public String modificacion(@PathVariable MultipartFile archivo,@PathVariable String idUsuario,@PathVariable String nombre,@PathVariable String apellido,@PathVariable String email,@PathVariable String password,@PathVariable Integer matricula,@PathVariable Integer telefono,@PathVariable ArrayList obrasocial,@PathVariable String locacion,@PathVariable String detalleEspecialidad,@PathVariable Especialidad especialidad, ModelMap modelo) {
        
        try {   
            ps.modificarProfesional(archivo, idUsuario, nombre, apellido, email, password, matricula, telefono, obrasocial, locacion, detalleEspecialidad, especialidad);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", us.getOne(nombre));
            
        } catch (Exception ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("modificar", us.getOne(nombre));
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
