package com.demo.saludApp.entidades;

import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.enumeraciones.Horario;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.enumeraciones.ObraSocial;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Joaquin
 */
@Entity
@Table(name = "consulta")
@Getter
@Setter
@NoArgsConstructor
public class Consulta {
    
    @Column
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;   
    
    @OneToOne
    private Profesional profesional;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date fechaConsulta;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Horario horario;
    
    @Column
    @Enumerated(EnumType.STRING)
    private ObraSocial obrasocial;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @OneToOne
    private Paciente paciente;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Estado estado;
        
    @Column
    private Double precioConsulta;
    
    @Lob
    @Column(length = 10000)
    private String detalleConsulta;
    
    @OneToMany
    @ElementCollection
    private List<Imagen> estudios;
    
}
