package com.demo.saludApp.entidades;

import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.enumeraciones.Horario;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.enumeraciones.ObraSocial;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Joaquin
 */
@Entity
public class Consulta {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private Especialidad especialidad;
    
    @ManyToOne
    private Profesional profesional;
    
    @Temporal(TemporalType.DATE)
    private Date fechaConsulta;
    
    private Horario horario;
    
    @Enumerated(EnumType.STRING)
    private ObraSocial obrasocial;
    
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @ManyToOne
    private Paciente paciente;
    
    private Estado estado;
        
    private Integer precioConsulta;
    
    private String detalleConsulta;
    
    @OneToOne
    private Imagen estudios;

    public Consulta() {
    }
    
    

    public Consulta(String id, Especialidad especialidad, Profesional profesional, Date fechaConsulta,Horario horario, ObraSocial obrasocial, Modalidad modalidad, Paciente paciente, Estado estado, Integer precioConsulta, String detalleConsulta, Imagen estudios) {
        this.id = id;
        this.especialidad = especialidad;
        this.profesional = profesional;
        this.fechaConsulta = fechaConsulta;
        this.horario = horario;
        this.obrasocial = obrasocial;
        this.modalidad = modalidad;
        this.paciente = paciente;
        this.estado = estado;
        this.precioConsulta = precioConsulta;
        this.detalleConsulta = detalleConsulta;
        this.estudios = estudios;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    
    public Profesional getprofesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public ObraSocial getObrasocial() {
        return obrasocial;
    }

    public void setObrasocial(ObraSocial obrasocial) {
        this.obrasocial = obrasocial;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Paciente getpaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Integer getPrecioConsulta() {
        return precioConsulta;
    }

    public void setPrecioConsulta(Integer precio) {
        this.precioConsulta = precio;
    }

    public String getDetalleConsulta() {
        return detalleConsulta;
    }

    public void setDetalleConsulta(String detalleConsulta) {
        this.detalleConsulta = detalleConsulta;
    }

    public Imagen getEstudios() {
        return estudios;
    }

    public void setEstudios(Imagen estudios) {
        this.estudios = estudios;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    
    
    
}
