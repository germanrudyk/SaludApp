package com.demo.saludApp.repositorios;

/**
 *
 * @author German
 */
@Repository
public interface ProfesionalRepositorio extends JpaRepository <Profesional, Int>{
//realiza una búsqueda a través del nombre del Profesional
 @Query("SELECT p FROM Profesional p WHERE p.nombre = :nombre")
 public Profesional buscarNombre(@Param("nombre") String nombre);
 //realiza una búsqueda a través de la especialidad del Profesional
 @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
 public Profesional buscarEspecialidad(@Param("especialidad") String especialidad);
 //trae una lista ordenada de los Profesionales por precio
 @Query("SELECT * FROM Profesional p ORDER BY p.precio DESC")
 public List<Profesional> ordenarPrecio();
  //trae una lista ordenada de los Profesionales por calificación
 @Query("SELECT * FROM Profesional p ORDER BY p.calificacion ")
 public List<Profesional> ordenarCalificacion();
}
