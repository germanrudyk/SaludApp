package com.demo.saludApp.entidades;

import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.ObraSocial;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profesional")
@Getter
@Setter
@NoArgsConstructor
public class Profesional extends Usuario {
    
    @Column
    private Integer matricula;
    @Column
    private String locacion;
    @Column
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @ElementCollection
    private List<String> obrasSociales;
    @Column
    private String detalleEspecialidad;
    @Column
    private Double calificacion;
    @Column
    private Integer calificaciones;
    @OneToMany
    @ElementCollection
    private List<Consulta> consultas;
}