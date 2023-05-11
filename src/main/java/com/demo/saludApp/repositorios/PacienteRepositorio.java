package com.demo.saludApp.repositorios;

/**
 *
 * @author German
 */
@Repository
public interface PacienteRepositorio extends JpaRepository <Paciente, Int>{
//realiza una búsqueda a través de los parámetros señalados
 @Query("SELECT p FROM Paciente p WHERE p.nombre = :nombre")
 public Paciente buscarNombre(@Param("nombre") String nombre);
}
