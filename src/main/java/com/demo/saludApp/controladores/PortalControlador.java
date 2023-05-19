/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ILMAN
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    

    @GetMapping("/")
    public String index() {

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
                       
        return "paciente_modificar.html";
    }
}
