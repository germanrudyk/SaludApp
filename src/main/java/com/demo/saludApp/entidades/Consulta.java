package com.demo.saludApp.entidades;

import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.enumeraciones.ObraSocial;
import java.time.LocalDate;
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
    
    private String nombre;
    
    @ManyToOne
    private Profesional matriculaProfesional;
    
    @Temporal(TemporalType.DATE)
    private Date fechaConsulta;
    
    /* HACER ENUMS*/
    private String horario;
    
    @Enumerated(EnumType.STRING)
    private ObraSocial obrasocial;
    
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @ManyToOne
    private Paciente dniPaciente;
    
    private Estado estado;
        
    private String precioConsulta;
    
    private String detalleConsulta;
    
    @OneToOne
    private Imagen estudios;

    public Consulta() {
    }
    
    

    public Consulta(String id, String nombre, Profesional matriculaProfesional, Date fechaConsulta, String horario, ObraSocial obrasocial, Modalidad modalidad, Paciente dniPaciente, Estado estado, String precioConsulta, String detalleConsulta, Imagen estudios) {
        this.id = id;
        this.nombre = nombre;
        this.matriculaProfesional = matriculaProfesional;
        this.fechaConsulta = fechaConsulta;
        this.horario = horario;
        this.obrasocial = obrasocial;
        this.modalidad = modalidad;
        this.dniPaciente = dniPaciente;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Profesional getMatriculaProfesional() {
        return matriculaProfesional;
    }

    public void setMatriculaProfesional(Profesional matriculaProfesional) {
        this.matriculaProfesional = matriculaProfesional;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
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

    public Paciente getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(Paciente dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getPrecioConsulta() {
        return precioConsulta;
    }

    public void setPrecioConsulta(String precioConsulta) {
        this.precioConsulta = precioConsulta;
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
