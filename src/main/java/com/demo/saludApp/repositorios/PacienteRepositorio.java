package com.demo.saludApp.repositorios;

import com.demo.saludApp.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author German
 */
@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {
    @Query(value = "SELECT COUNT(id) FROM Paciente;", nativeQuery = true)
    public Integer contarPacientes();
}
