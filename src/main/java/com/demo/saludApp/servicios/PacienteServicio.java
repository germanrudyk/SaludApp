package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.enumeraciones.Genero;
import com.demo.saludApp.enumeraciones.ObraSocial;
import com.demo.saludApp.enumeraciones.Rol;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.PacienteRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void crearPaciente(String nombre, String email, String password, Integer telefono, Imagen imagen, String dni, Genero genero, ObraSocial obraSocial, String fechaNacimiento) throws MiException, ParseException {

        validar(nombre, email, password, telefono, dni, fechaNacimiento);

        Paciente paciente = new Paciente();

        paciente.setNombre(nombre);
        paciente.setEmail(email);
        paciente.setPassword(password);
        paciente.setTelefono(telefono);
        paciente.setActivo(true);               //Cuando se crea el Paciente estado true es activo
        paciente.setRol(Rol.PACIENTE);            //Cuando se crea el Paciente el Rol es seteado a Paciente automaticamente
        
        if (imagen != null){
        paciente.setImagen(imagen);
        }
        paciente.setDni(dni);
        paciente.setGenero(genero);
        paciente.setObraSocial(obraSocial);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = format.parse(fechaNacimiento);

        paciente.setFechaNacimiento(fecha);

        pacienteRepositorio.save(paciente);
    }

    public List<Paciente> listarPacientes() {

        List<Paciente> pacientes = new ArrayList();
        pacientes = pacienteRepositorio.findAll();
        return pacientes;
    }

    public Paciente buscarPorEmail(String email) {

        return pacienteRepositorio.buscarPorEmail(email);

    }

    public Paciente getOne(String id) {

        return pacienteRepositorio.getOne(id);

    }

    @Transactional
    public void modificarPaciente(String id, String nombre, String email, String password, Integer telefono, Imagen imagen, String dni, Genero genero, ObraSocial obraSocial, String fechaNacimiento) throws MiException, ParseException {

        validar(nombre, email, password, telefono, dni, fechaNacimiento);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setEmail(email);
            paciente.setPassword(password);
            paciente.setTelefono(telefono);
            paciente.setImagen(imagen);

            paciente.setDni(dni);
            paciente.setGenero(genero);
            paciente.setObraSocial(obraSocial);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = format.parse(fechaNacimiento);

            paciente.setFechaNacimiento(fecha);

            pacienteRepositorio.save(paciente);

        }
    }

    public void eliminarPaciente(String id) throws MiException {

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();
            paciente.setActivo(false);
            pacienteRepositorio.save(paciente);
        }
    }

    private void validar(String nombre, String email, String password, Integer telefono, String dni, String fechaNacimiento) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio"); //
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio"); //
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("el password no puede ser nulo o estar vacio"); //
        }
        if (telefono.toString().isEmpty() || telefono == null) {
            throw new MiException("el password no puede ser nulo o estar vacio"); //
        }
        
        if (dni.isEmpty() || dni == null) {
            throw new MiException("el dni no puede ser nulo o estar vacio"); //
        }
        if (fechaNacimiento.isEmpty() || fechaNacimiento == null) {
            throw new MiException("la fecha de nacimiento no puede estar vacia o ser nula");
        }

    }

}
