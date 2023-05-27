package com.demo.saludApp.repositorios;

import com.demo.saludApp.entidades.Profesional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 *
 * @author German
 */
@Repository
public interface ProfesionalRepositorio extends JpaRepository <Profesional, String>{
 //realiza una búsqueda a través de la especialidad del Profesional
 @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
 public List<Profesional> buscarEspecialidad(@Param("especialidad") String especialidad);
 
}
