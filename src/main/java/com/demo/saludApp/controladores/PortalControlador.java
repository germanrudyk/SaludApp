/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.saludApp.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ILMAN
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    

    @GetMapping("/")
    public String index() {

        return "index1.html";
    }
}
