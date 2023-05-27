package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Horario;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.UsuarioRepositorio;
import com.demo.saludApp.servicios.ConsultaServicio;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.text.ParseException;
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
    private PacienteServicio pacienteS;

    @Autowired
    private ConsultaServicio consultaS;

    @Autowired
    private UsuarioRepositorio us;

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

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/modificar/{nombre}")
    public String modificar(@PathVariable String nombre, ModelMap modelo) {

        modelo.put("modificar", us.getOne(nombre));
        return "profesional_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @PostMapping("/modificacion")
    public String modificacion(MultipartFile archivo, @PathVariable String idUsuario,@PathVariable String nombre,@PathVariable String apellido,@PathVariable Integer telefono,@PathVariable String email,@PathVariable String password,@PathVariable Integer matricula,@PathVariable String locacion,@PathVariable Especialidad especialidad,@PathVariable String detalleEspecialidad,@PathVariable List<String> obrasocial,@PathVariable Double calificacion,@PathVariable List<Consulta> consultas,@PathVariable Boolean activo, ModelMap modelo) {

        try {
            profesionalS.modificar(archivo, idUsuario, nombre, apellido, telefono, email, password, matricula, locacion, especialidad, detalleEspecialidad, obrasocial, calificacion, consultas, activo);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", us.getOne(nombre));

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("modificar", us.getOne(nombre));
            return "redirect:/profesional";

        }
        return "redirect:/profesional";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/listar")
    public String listar(ModelMap modelo) {

        List<Profesional> profesionales = profesionalS.listar();
        modelo.addAttribute("profesionales", profesionales);

        return "panelAdmin.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/filtrar/{especialidad}")
    public String filtrar(@PathVariable String especialidad, ModelMap modelo) {

        List<Profesional> profesionales = profesionalS.filtrar(especialidad);
        modelo.addAttribute("profesionales", profesionales);

        return "panelAdmin.html";
    }

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

}
