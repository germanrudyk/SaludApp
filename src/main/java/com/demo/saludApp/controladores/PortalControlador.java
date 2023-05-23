package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.PacienteServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ILMAN
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    PacienteServicio pacienteServicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }
    
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String dni, @RequestParam Genero genero, @RequestParam ObraSocial obraSocial, @RequestParam String fechaNacimiento, ModelMap modelo) {
        //@RequestParam vincula los parámetros de una petición HTTP a los argumentos de un método
        try {
            pacienteServicio.crearPaciente(nombre, email, email, Integer.SIZE, password, dni, genero, obraSocial, fechaNacimiento);
            modelo.put("exito", "Paciente registrado con exito");
        } catch (Exception ex) {            
            modelo.put("error", ex.getMessage());
            return "index.html";
            
        }
        return "index.html";        
    }  
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        System.out.println("llego a login");
        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos!");
            
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_ADMIN','ROLE_PROFESIONAL')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
         System.out.println("llego a inicio");
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
      
        if(logueado.isActivo()==false){
           modelo.put("suspendido", "Cuenta suspendida!");
          
        return "redirect:/logout";
        }
        modelo.put("exito","Bienvenido");
        
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin";
        }
        
        if (logueado.getRol().toString().equals("PROFESIONAL")) {
            return "redirect:/profesional";
        }
                       
        return "redirect:/paciente";
    }
}
