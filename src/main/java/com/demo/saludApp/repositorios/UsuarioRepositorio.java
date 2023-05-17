package com.demo.saludApp.repositorios;


import com.demo.saludApp.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ILMAN
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
     @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email")String email);
    
}
