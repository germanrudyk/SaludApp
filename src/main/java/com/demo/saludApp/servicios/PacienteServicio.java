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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;
     
    @Transactional
    public void crearPaciente(String nombre, String apellido, String email, Integer telefono, String password, String dni, Genero genero, ObraSocial obraSocial, String fechaNacimiento) throws MiException, ParseException {

        validar(nombre, email, password, dni, fechaNacimiento);

        Paciente paciente = new Paciente();
        
        paciente.setActivo(true);
        paciente.setRol(Rol.PACIENTE);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
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

    public Paciente getOne(String id) {
        return pacienteRepositorio.getOne(id);
    }

     @org.springframework.transaction.annotation.Transactional
    public void modificarPaciente(MultipartFile archivo, String idUsuario,  String nombre, String apellido, String email, String password, String dni, String fechaNacimiento, Genero genero, ObraSocial obrasocial) throws MiException, ParseException {
        
        Optional<Paciente> respuesta = pacienteRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setEmail(email);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));
            paciente.setDni(dni);
            paciente.setGenero(genero);
            paciente.setObraSocial(obrasocial);
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = format.parse(fechaNacimiento);
            paciente.setFechaNacimiento(fecha);
            
            String idImagen = null;

            if (paciente.getImagen() != null) {
                idImagen = paciente.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            paciente.setImagen(imagen);

            pacienteRepositorio.save(paciente);
        }
    }

    public void eliminarPaciente(String id) throws MiException {

         Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

                paciente.setActivo(false);
                
        }
    }

    private void validar(String nombre, String email, String password, String dni, String fechaNacimiento) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio"); //
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio"); //
        }
        if (password.isEmpty() || password == null) {
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
