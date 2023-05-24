package com.demo.saludApp.repositorios;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Estado;
import java.sql.Date;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author PC
 */
@Repository
public interface ConsultaRepositorio extends JpaRepository<Consulta, String> {
//realiza una búsqueda a través de los parámetros señalados

//    @Query("SELECT c FROM Consulta c WHERE c.fecha = :fecha")
//    public List<Consulta> buscarPorFecha(@Param("fecha") Date fecha);

//    @Query("SELECT c FROM Consulta c WHERE c.profesional = :profesional")
//    public List<Consulta> buscarPorProfesional(@Param("profesional") Profesional profesional);
//    
//    @Query("SELECT c FROM Consulta c WHERE c.estado = :estado")
//    public List<Consulta> buscarPorEstado(@Param("estado") Estado estado);
    
//    @Query("SELECT c FROM Consulta c WHERE c.precio = :precio")
//    public List<Consulta> buscarPorPrecio(@Param("precio") Integer precio);
}
