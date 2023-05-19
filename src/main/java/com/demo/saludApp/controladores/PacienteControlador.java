/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.saludApp.controladores;

import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author German
 */
@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/paciente") //Mapea la ruta de la petición y el método del controlador
public class PacienteControlador {    
    
    @Autowired
    private PacienteServicio ps;
    
    @GetMapping("/registrar") //asigna solicitudes HTTP GET
    public String registrar(ModelMap modelo) {
        
//        modelo.put("registrar", "pagina de registro");
        
        

        return "admin.html";
    }
    
    @PostMapping("/registro") //asigna solicitudes HTTP POST
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String dni, @RequestParam Genero genero, @RequestParam ObraSocial obraSocial, @RequestParam String fechaNacimiento, ModelMap modelo) {
        //@RequestParam vincula los parámetros de una petición HTTP a los argumentos de un método
        try {
            ps.crearPaciente(nombre, email, password, dni, genero, obraSocial, fechaNacimiento);
            modelo.put("exito", "Paciente registrado con exito");
        } catch (Exception ex) {            
            modelo.put("error", ex.getMessage());
            return "paciente_registrar.html";
            
        }
        return "paciente_registrar.html";        
    }    
    
    @GetMapping("/modificar/{email}")
    public String modificar(@PathVariable String email, ModelMap modelo) {
        
        modelo.put("modificar", ps.buscarPorEmail(email));
        
        return "paciente_modificar.html";
    }
    
    @PostMapping("/modificacion")
    public String modificacion(@RequestParam String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String dni, @RequestParam Genero genero, @RequestParam ObraSocial obraSocial, @RequestParam String fechaNacimiento, ModelMap modelo) {
        
        try {           
            ps.modificarPaciente(id, nombre, email, genero, obraSocial, password, dni, fechaNacimiento);
            modelo.put("exito", "Modificación exitosa");
            modelo.put("modificar", ps.buscarPorEmail(email));
            
        } catch (Exception ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("modificar", ps.getOne(id));
            return "paciente_modificar";
            
        }
        return "paciente_modificar";
    }    
}
