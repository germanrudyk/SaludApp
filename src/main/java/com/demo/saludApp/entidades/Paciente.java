package com.demo.saludApp.entidades;

import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
public class Paciente extends Usuario {

    @Column
    private String dni;
    @Column
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column
    @Enumerated(EnumType.STRING)
    private ObraSocial obraSocial;
    @OneToMany
    @ElementCollection
    private List<Consulta> idHistoria;
}
