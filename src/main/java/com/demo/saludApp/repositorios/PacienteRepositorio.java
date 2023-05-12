package com.demo.saludApp.repositorios;

import com.demo.saludApp.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author German
 */
@Repository
public interface PacienteRepositorio extends JpaRepository <Paciente, Integer>{
//realiza una búsqueda a través de los parámetros señalados
 @Query("SELECT p FROM Paciente p WHERE p.nombre = :nombre")
 public Paciente buscarNombre(@Param("nombre") String nombre);
}
