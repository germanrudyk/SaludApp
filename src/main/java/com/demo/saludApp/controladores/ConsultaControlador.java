package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.servicios.ConsultaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/") //Mapea la ruta de la petición y el método del controlador
public class ConsultaControlador {  
    
    @Autowired
    private ConsultaServicio consultaS;     
    
    //------------- Vista General -------------
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_PROFESIONAL')")
    @GetMapping("/{id}") //asigna solicitudes HTTP GET
    public String vistaHistoria(@PathVariable String id,ModelMap modelo) {
        
        List<Consulta> consultas = consultaS.buscarPorEstado(Estado.UTILIZADA);  
        consultas.addAll(consultaS.buscarPorPaciente(id));
        modelo.addAttribute("consultas", consultas); 
       
            return "historiaClinica.html";
        }       
    }