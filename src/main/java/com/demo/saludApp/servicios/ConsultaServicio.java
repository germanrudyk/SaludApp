package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Especialidad;
import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.enumeraciones.Horario;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.ConsultaRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaServicio {

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    public void crearConsulta(String fecha, Horario horario, Profesional profesional, Modalidad modalidad, Especialidad especialidad, Integer precio) throws MiException, ParseException {

        validar(fecha, horario, profesional, modalidad, especialidad, precio);

        Consulta consulta = new Consulta();

        consulta.setEstado(Estado.DISPONIBLE);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaConsulta = format.parse(fecha);
        consulta.setFechaConsulta(fechaConsulta);

        consulta.setHorario(horario);
        consulta.setMatriculaProfesional(profesional);
        consulta.setPrecioConsulta(precio);

        consultaRepositorio.save(consulta);

    }

    public void reservarConsulta(String idConsulta, Paciente paciente) {

        Optional<Consulta> respuesta = consultaRepositorio.findById(idConsulta);

        if (respuesta.isPresent()) {
            Consulta consulta = respuesta.get();

            consulta.setDniPaciente(paciente);
            consulta.setObrasocial(paciente.getObraSocial());

            consulta.setEstado(Estado.RESERVADA);

            consultaRepositorio.save(consulta);
        }

    }

    private void validar(String fecha, Horario horario, Profesional profesional, Modalidad modalidad, Especialidad especialidad, Integer precio) throws MiException {

        if (fecha.isEmpty() || fecha == null) {
            throw new MiException("La fecha no puede ser nulo o estar vacio"); //
        }
        if (horario.toString().isEmpty() || horario == null) {
            throw new MiException("el horario no puede ser nulo o estar vacio"); //
        }
        if (profesional == null) {
            throw new MiException("el profesional no puede estar vacio"); //
        }
        if (modalidad.toString().isEmpty() || modalidad == null) {
            throw new MiException("la Modalidad no puede ser nulo o estar vacio"); //
        }
        if (especialidad.toString().isEmpty() || especialidad == null) {
            throw new MiException("la especialidad no puede ser nulo o estar vacio"); //
        }
        if (precio == null) {
            throw new MiException("el precio no puede estar vacio"); //
        }

    }
}
