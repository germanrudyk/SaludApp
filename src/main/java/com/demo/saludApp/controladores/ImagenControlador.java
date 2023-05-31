package com.demo.saludApp.controladores;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.repositorios.ConsultaRepositorio;
import com.demo.saludApp.servicios.ConsultaServicio;
import com.demo.saludApp.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ILMAN
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    ConsultaRepositorio consultaRepositorio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

        Usuario usuario = usuarioServicio.getOne(id);

        byte[] imagen = usuario.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/estudio/{consultaid}/{estudioid}")
    public ResponseEntity<byte[]> imagenEstudio(@PathVariable String consultaid, @PathVariable String estudioid) {

        Consulta consulta = consultaRepositorio.getOne(consultaid);
        
        List<Imagen> imagenes = consulta.getEstudios();
        
        byte[] imagen = null;
        
        for (Imagen aux : imagenes) {
            
            imagen = aux.getContenido();
        }
        
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    } 
}