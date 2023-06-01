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

    public Paciente getOne(String id) {
        return pacienteRepositorio.getOne(id);
    }

    //------------- Listar Pacientes -------------
    public List<Paciente> listar() {

        List<Paciente> pacientes = new ArrayList();
        pacientes = pacienteRepositorio.findAll();
        return pacientes;
    }

    //------------- Crear Paciente -------------
    @Transactional
    public void crear(String nombre, String apellido, Integer telefono, String email, String password, String password2, String dni, Genero genero, String fechaNacimiento, ObraSocial obraSocial, MultipartFile archivo) throws MiException, ParseException {

        validar(nombre, password, password2);
        Paciente paciente = new Paciente();

        paciente.setActivo(true);
        paciente.setRol(Rol.PACIENTE);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setTelefono(telefono);
        paciente.setEmail(email);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setDni(dni);
        paciente.setGenero(genero);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = format.parse(fechaNacimiento);
        paciente.setFechaNacimiento(fecha);
        paciente.setObraSocial(obraSocial);

        if (!(archivo.isEmpty())) {
            Imagen imagen = imagenServicio.guardar(archivo);
            paciente.setImagen(imagen); //Se agrega la imagen 
        }

        pacienteRepositorio.save(paciente);
    }

    //------------- Modificar Paciente -------------
    @org.springframework.transaction.annotation.Transactional
    public void modificar(MultipartFile archivo, String idUsuario, String nombre, String apellido, Integer telefono, String email, String dni, String fechaNacimiento, Genero genero, ObraSocial obrasocial, Boolean activo) throws MiException, ParseException {

        Optional<Paciente> respuesta = pacienteRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setTelefono(telefono);
            paciente.setEmail(email);
            paciente.setDni(dni);
            paciente.setGenero(genero);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = format.parse(fechaNacimiento);
            paciente.setFechaNacimiento(fecha);
            paciente.setObraSocial(obrasocial);
            paciente.setActivo(activo);
            String idImagen = null;

            if (paciente.getImagen() != null) {
                idImagen = paciente.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            paciente.setImagen(imagen);

            pacienteRepositorio.save(paciente);
        }
    }

    //------------- Eliminar paciente ------------
    public void eliminar(String id) {

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            pacienteRepositorio.delete(paciente);

        }

    }

    public void darDeBaja(String id) {

        Optional<Paciente> respuesta = pacienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setActivo(false);

            pacienteRepositorio.save(paciente);

        }

    }

    public Integer contarPacientes() {
        return pacienteRepositorio.contarPacientes();
    }

    //------------- Validar Paciente -------------
    private void validar(String nombre, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length() < 6) {
            throw new MiException("El email no puede ser nulo y debe tener mas de 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresasdas deben ser iguales");
        }
    }

}
