package com.demo.saludApp.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ILMAN
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Imagen {
    @Column
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column
    private String mime;
    @Column
    private String nombre;
    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY) //Se va a cargar solo cuando se pide
    private byte[] contenido;
   
}
