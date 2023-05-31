package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.ConsultaServicio;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
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
 * @author German
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/paciente") //Mapea la ruta de la petición y el método del controlador
public class PacienteControlador {  
    
    @Autowired
    private PacienteServicio pacienteS;
    @Autowired
    private ProfesionalServicio profesionalS;
    @Autowired
    private ConsultaServicio consultaS;     
    
    //------------- Vista General -------------
    @GetMapping("") //asigna solicitudes HTTP GET
    public String vistaPaciente(HttpSession session, ModelMap modelo) {
        
        List<Consulta> consultas = consultaS.buscarPorEstado(Estado.DISPONIBLE);   
        modelo.addAttribute("consultas", consultas); 
       
        Usuario logueado = (Paciente) session.getAttribute("usuariosession");
          
        try {
            List<Consulta> misconsultas = consultaS.buscarPorPaciente(logueado.getId());
            modelo.addAttribute("misconsultas", misconsultas);
            return "paciente.html";
        } catch (Exception e) {
            return "paciente.html";
        }
                
    }

        
    //------------- Modificar Paciente -------------
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @PostMapping("/modificar")
    public String modificarPaciente(MultipartFile archivo,@RequestParam String idUsuario,@RequestParam  String nombre,@RequestParam String apellido,@RequestParam Integer telefono,@RequestParam String email,@RequestParam String dni,@RequestParam String fechaNacimiento,@RequestParam Genero genero,@RequestParam ObraSocial obrasocial,@RequestParam Boolean activo, ModelMap modelo) {

        try {
            pacienteS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, dni, fechaNacimiento, genero, obrasocial, activo);
            modelo.put("exito", "Modificación exitosa");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/paciente"; 
        }
        return "redirect:/paciente"; 
    }   
    
    //------------- Calificar -------------
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @PostMapping("/calificar")
    public String calificar(@RequestParam String idUsuario,@RequestParam Double calificacion, ModelMap modelo) {

        try {
            profesionalS.calificar(idUsuario, calificacion);
            modelo.put("exito", "Modificación exitosa");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/paciente"; 
        }
        return "redirect:/paciente"; 
    }
    
    //------------- Login -------------
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/reservar/{id}")
    public String reservar(HttpSession session, @PathVariable String id){
        
        Usuario logueado = (Paciente) session.getAttribute("usuariosession");
        Paciente paciente = (Paciente) logueado;
        consultaS.reservarConsulta(id, paciente);
        return "redirect:/paciente";  
    }
}
