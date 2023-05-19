/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.saludApp.enumeraciones;

/**
 *
 * @author ILMAN
 */
public enum Horario {
    
    PRIMERO("08:00 a 09:00"),
    SEGUNDO("09:00 a 10:00"),
    TERCERO("10:00 a 11:00"),
    CUARTO("11:00 a 12:00"),
    QUINTO("12:00 a 13:00"),
    SEXTO("16:00 a 17:00"),
    SEPTIMO("17:00 a 18:00"),
    OCTAVO("18:00 a 19:00");

    public final String texto;

    private Horario(String label) {
        this.texto = label;
    }
}
