package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    PacienteServicio pacienteServicio;
    
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("")
    public String index(ModelMap modelo) {
        
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        
        return "index.html";
    }
    
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam String email, @RequestParam String apellido, @RequestParam String password, @RequestParam String dni, @RequestParam Genero genero, @RequestParam ObraSocial obraSocial, @RequestParam String fechaNacimiento, ModelMap modelo) {
        //@RequestParam vincula los parámetros de una petición HTTP a los argumentos de un método
//        try {
//            Path directorioImagenes = Paths.get("src//main//resources//static/img");
//            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
//            byte[] bytesImg = archivo.getBytes();
//            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + archivo.getOriginalFilename());
//
//            Files.write(rutaCompleta, bytesImg);
//        } catch (IOException ex) {
//            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
//        }

        try {
            pacienteServicio.crearPaciente(nombre, apellido, email, Integer.SIZE, password, dni, genero, obraSocial, fechaNacimiento, archivo);
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
