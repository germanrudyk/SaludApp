package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Horario;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.servicios.ConsultaServicio;
import com.demo.saludApp.servicios.ImagenServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Damian
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/profesional") //Mapea la ruta de la petición y el método del controlador
public class ProfesionalControlador {
    
    @Autowired
    private ProfesionalServicio profesionalS;

    @Autowired
    private ConsultaServicio consultaS;
    
    @Autowired
    private ImagenServicio imagenS;


    //------------- Vista General -------------
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("") //asigna solicitudes HTTP GET
    public String vistaProfesional(HttpSession session, ModelMap modelo) {

        Usuario logueado = (Profesional) session.getAttribute("usuariosession");

        try {
            List<Consulta> consultas = consultaS.buscarPorProfesional(logueado.getId());
            modelo.put("consultas", consultas);
            return "profesional.html";
        } catch (Exception e) {
            return "profesional.html";
        }

    }

    //------------- Modificar Profesional -------------
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @PostMapping("/modificar")
    public String modificarProfesional(MultipartFile archivo, @RequestParam String idUsuario,@RequestParam String nombre,@RequestParam String apellido,@RequestParam Integer telefono,@RequestParam String email,@RequestParam Integer matricula,@RequestParam String locacion,@RequestParam Especialidad especialidad, @RequestParam Boolean activo, ModelMap modelo) {

        try {
            profesionalS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, matricula, locacion, especialidad, activo);
            modelo.put("exito", "Modificación exitosa");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/profesional"; 
        }
        return "redirect:/profesional"; 
    }
    
    //------------- Descripción -------------
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @PostMapping("/descripcion")
    public String descripcion(@RequestParam String idUsuario,@RequestParam String detalleEspecialidad, ModelMap modelo) {

        try {
            profesionalS.descripcion(idUsuario, detalleEspecialidad);
            modelo.put("exito", "Modificación exitosa");
        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            return "redirect:/profesional"; 
        }
        return "redirect:/profesional"; 
    }
    
    //------------- Filtrar Especialidad -------------
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/filtrar/{especialidad}")
    public String filtrar(@PathVariable String especialidad, ModelMap modelo) {

        List<Profesional> profesionales = profesionalS.filtrar(especialidad);
        modelo.addAttribute("profesionales", profesionales);

        return "panelAdmin.html";
    }

    //------------- Login -------------
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @PostMapping("/registro")
    public String registro(HttpSession session, String fecha, Horario horario, Modalidad modalidad, Double precio, ModelMap modelo) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        Profesional profesional = (Profesional) logueado;

        try {
            consultaS.crearConsulta(fecha, horario, profesional, modalidad, precio);
            return "redirect:../profesional";
        } catch (MiException ex) {
            System.out.println(ex.getMessage());
            return "profesional";
        } catch (ParseException ex) {
            Logger.getLogger(ProfesionalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "profesional";
        }

    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/modificarConsulta/eliminar/{id}")
    public String eliminarConsulta(@PathVariable String id){
        
        consultaS.eliminarConsulta(id);
        
        return "redirect:/profesional";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/modificarConsulta/iniciar/{id}")
    public String utilizarConsultar(@PathVariable String id){
        
        consultaS.utilizarConsulta(id);
        
        return "redirect:/profesional";
        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/modificarConsulta/cancelar/{id}")
    public String darBajaConsulta(@PathVariable String id){
              
        consultaS.darBajaConsulta(id);
        
        return "redirect:/profesional";
        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @PostMapping("/modificarConsulta/cargar/{id}")
    public String cargarConsulta(@PathVariable String id, @RequestParam String detalleConsulta, MultipartFile estudios[]){
        
        List<Imagen> archivosEstudios = new ArrayList();

        for (MultipartFile estudio : estudios) {

             Imagen imagen = imagenS.guardar(estudio);

             archivosEstudios.add(imagen);

        }
        
        consultaS.cargarDatosConsulta(id, archivosEstudios, detalleConsulta);
        
        return "redirect:/profesional";
        
    }

}
