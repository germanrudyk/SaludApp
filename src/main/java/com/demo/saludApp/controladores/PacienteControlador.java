package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.repositorios.UsuarioRepositorio;
import com.demo.saludApp.servicios.ConsultaServicio;
import com.demo.saludApp.servicios.PacienteServicio;
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
    private UsuarioRepositorio usuarioS;
    @Autowired
    private ConsultaServicio consultaS;     
    
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("") //asigna solicitudes HTTP GET
    public String vistaPaciente(ModelMap modelo) {
        
        List<Consulta> consultas = consultaS.listarTodas();   
        modelo.put("consultas", consultas); 
        return "paciente.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/reservar/{id}")
    public String reservar(HttpSession session, @PathVariable String id){
        
        Usuario logueado = (Paciente) session.getAttribute("usuariosession");
        Paciente paciente = (Paciente) logueado;
        consultaS.reservarConsulta(id, paciente);
        return "redirect:/paciente";  
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/modificar/{email}")
    public String modificar(@PathVariable String email, ModelMap modelo) {
        
        modelo.put("modificar", usuarioS.buscarPorEmail(email));
        return "paciente_modificar.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @PostMapping("/modificacion")
    public String modificacion(@RequestParam String idUsuario, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Integer telefono, @RequestParam String email, @RequestParam String password, @RequestParam String dni, @RequestParam Genero genero, @RequestParam String fechaNacimiento, @RequestParam ObraSocial obraSocial,@RequestParam List<Consulta> idHistoria,@RequestParam Boolean activo, ModelMap modelo, MultipartFile archivo) {
        
        try {           
            pacienteS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, password, dni, fechaNacimiento, genero, obraSocial, idHistoria, activo);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", pacienteS.getOne(idUsuario)); 
        } catch (Exception ex) { 
            modelo.put("error", ex.getMessage());
            modelo.put("modificar", pacienteS.getOne(idUsuario));
            return "paciente_modificar";
        }
        return "paciente_modificar";
    }    
}
