package com.demo.saludApp.entidades;


import com.demo.saludApp.enumeraciones.Especialidad;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

@Entity
public class Profesional extends Usuario {
    
    private Integer matricula;
    private Double calificacion;
    private String locacion;
    private String detalleEspecialidad;
        
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    
    
    @ElementCollection(targetClass=String.class)
    private List<String> obraSocialAceptada;

    public Profesional() {
        super();
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    public String getDetalleEspecialidad() {
        return detalleEspecialidad;
    }

    public void setDetalleEspecialidad(String detalleEspecialidad) {
        this.detalleEspecialidad = detalleEspecialidad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<String> getObraSocialAceptada() {
        return obraSocialAceptada;
    }

    public void setObraSocialAceptada(List<String> obraSocialAceptada) {
        this.obraSocialAceptada = obraSocialAceptada;
    }

    @Override
    public String toString() {
        return "Profesional{" + "matricula=" + matricula + ", calificacion=" + calificacion + ", locacion=" + locacion + ", detalleEspecialidad=" + detalleEspecialidad + ", especialidad=" + especialidad + ", obraSocialAceptada=" + obraSocialAceptada + '}';
    }



    
}