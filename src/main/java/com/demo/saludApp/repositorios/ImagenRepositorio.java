package com.demo.saludApp.repositorios;

import com.demo.saludApp.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ILMAN
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
