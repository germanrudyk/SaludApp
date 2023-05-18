/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ILMAN
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
     @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id, ModelMap modelo) {

        return "redirect:/admin/usuarios";
    }
    
     
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {
    usuarioServicio.eliminarUsuario(id);
         return "redirect:/admin/usuarios";
    }
    
       
    
     
}
