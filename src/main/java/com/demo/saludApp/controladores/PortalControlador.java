package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    PacienteServicio pacienteS;
    
    @Autowired
    private ProfesionalServicio profesionalS;

    @GetMapping("")
    public String index(ModelMap modelo) {
        
        List<Profesional> profesionales = profesionalS.listar();
        modelo.addAttribute("profesionales", profesionales);  
        return "index.html";
    }
    
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam Integer telefono, @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam String dni, @RequestParam Genero genero, @RequestParam String fechaNacimiento, @RequestParam ObraSocial obraSocial, MultipartFile archivo, ModelMap modelo) {
        try {
            pacienteS.crear(nombre, apellido, telefono, email, password, password2, dni, genero, fechaNacimiento, obraSocial, archivo);
            modelo.put("exito", "Paciente registrado con exito");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "index.html";
        }
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos!");
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_ADMIN','ROLE_PROFESIONAL')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.isActivo() == false) {
            modelo.put("suspendido", "Cuenta suspendida!");
            return "redirect:/logout";
        }
        modelo.put("exito", "Bienvenido");
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin";
        }
        if (logueado.getRol().toString().equals("PROFESIONAL")) {
            return "redirect:/profesional";
        }
        return "redirect:/paciente";
    }
}
