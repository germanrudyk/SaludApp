package com.demo.saludApp.entidades;


import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.ObraSocial;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Profesional {
    
    private Integer matricula;
    private Integer calificacion;
    private String consultas;
    private String locacion;
    private String detalleEspecialidad;
    private ArrayList<String> turnos;
    
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    private ArrayList<ObraSocial> obraSocialAceptada;

    public Profesional() {
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getConsultas() {
        return consultas;
    }

    public void setConsultas(String consultas) {
        this.consultas = consultas;
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

    public ArrayList<String> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<String> turnos) {
        this.turnos = turnos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public ArrayList<ObraSocial> getObraSocialAceptada() {
        return obraSocialAceptada;
    }

    public void setObraSocialAceptada(ArrayList<ObraSocial> obraSocialAceptada) {
        this.obraSocialAceptada = obraSocialAceptada;
    }

    @Override
    public String toString() {
        return "Profesional{" + "matricula=" + matricula + ", calificacion=" + calificacion + ", consultas=" + consultas + ", locacion=" + locacion + ", detalleEspecialidad=" + detalleEspecialidad + ", turnos=" + turnos + ", especialidad=" + especialidad + ", obraSocialAceptada=" + obraSocialAceptada + '}';
    }
    
    
    
}
