package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Imagen;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Profesional;
import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.enumeraciones.Horario;
import com.demo.saludApp.enumeraciones.Modalidad;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.ConsultaRepositorio;
import com.demo.saludApp.repositorios.PacienteRepositorio;
import com.demo.saludApp.repositorios.ProfesionalRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConsultaServicio {

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private PacienteServicio pacienteS;

    @Autowired
    private ImagenServicio imagenS;

    public void crearConsulta(String fecha, Horario horario, Profesional profesional, Modalidad modalidad, Double precio) throws MiException, ParseException {

        validar(fecha, horario, profesional, modalidad, precio);

        Consulta consulta = new Consulta();

        consulta.setEstado(Estado.DISPONIBLE);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaConsulta = format.parse(fecha);
        consulta.setFechaConsulta(fechaConsulta);

        consulta.setHorario(horario);
        consulta.setProfesional(profesional);
        consulta.setModalidad(modalidad);
        consulta.setPrecioConsulta(precio);

        consultaRepositorio.save(consulta);

    }

    public void reservarConsulta(String idConsulta, Paciente paciente) {

        Optional<Consulta> respuesta = consultaRepositorio.findById(idConsulta);

        if (respuesta.isPresent() || !(paciente.toString().isEmpty()) || !(paciente == null)) {
            Consulta consulta = respuesta.get();

            consulta.setPaciente(paciente);
            consulta.setObrasocial(paciente.getObraSocial()); // se guarda la obra social porque puede cambiar en el tiempo
            consulta.setEstado(Estado.RESERVADA);

            consultaRepositorio.save(consulta);
        }

    }

    public void realizarConsulta(String idConsulta, String detalleConsulta, Imagen estudio) {

        Optional<Consulta> respuesta = consultaRepositorio.findById(idConsulta);

        if (respuesta.isPresent() || !(detalleConsulta.isEmpty()) || !(detalleConsulta == null) || !(estudio == null)) {

            Consulta consulta = respuesta.get();

            consulta.setDetalleConsulta(detalleConsulta);

            List<Imagen> lista = consulta.getEstudios();
            lista.add(estudio);
            consulta.setEstudios(lista);

            consulta.setEstado(Estado.UTILIZADA);

            consultaRepositorio.save(consulta);
        }

    }

    public List<Consulta> buscarPorFecha(Date fecha) {
        return consultaRepositorio.buscarPorFecha(fecha);
    }

    public List<Consulta> buscarPorProfesional(String idProfesional) {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(idProfesional);

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            return consultaRepositorio.buscarPorProfesional(profesional);
        }
        return null;        // ver opciones
    }

    public List<Consulta> buscarPorPaciente(String idPaciente) {
        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);

        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();
            return consultaRepositorio.buscarTodoPorPaciente(paciente);
        }
        return null;        // ver opciones
    }

//    public List<Consulta> buscarPorPacientePorFecha(String idPaciente, Date fecha) {
//        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);
//
//        if (respuesta.isPresent() && fecha != null) {
//            Paciente paciente = respuesta.get();
//            return consultaRepositorio.buscarPorPacientePorFecha(paciente, fecha);
//        }
//        return null;        // ver opciones
//    }
    public List<Consulta> buscarPorEstado(Estado estado) {
        return consultaRepositorio.buscarPorEstado(estado);
    }

    public List<Consulta> buscarPorPrecio(Double precio) {
        if (precio != null) {
            return consultaRepositorio.buscarPorPrecio(precio);
        }
        return null;
    }

    public List<Consulta> listarTodas() {

        return consultaRepositorio.findAll();

    }

    public void darBajaConsulta(String idConsulta) {
        Optional<Consulta> respuesta = consultaRepositorio.findById(idConsulta);

        if (respuesta.isPresent()) {
            Consulta consulta = respuesta.get();

            consulta.setEstado(Estado.DISPONIBLE);

            consulta.setPaciente(null);

            consultaRepositorio.save(consulta);
        }

    }

    public void utilizarConsulta(String idConsulta) {

        Optional<Consulta> respuesta = consultaRepositorio.findById(idConsulta);

        if (respuesta.isPresent()) {
            Consulta consulta = respuesta.get();

            consulta.setEstado(Estado.UTILIZADA);

            consultaRepositorio.save(consulta);
        }

    }

    public void eliminarConsulta(String idConsulta) {
        Optional<Consulta> respuesta = consultaRepositorio.findById(idConsulta);

        if (respuesta.isPresent()) {
            Consulta consulta = respuesta.get();

            consultaRepositorio.delete(consulta);
        }

    }

    public void cargarDatosConsulta(String idConsulta, List<Imagen> estudios, String detalleConsulta) {

        // Cargar estudios y detalle de la consulta
        Optional<Consulta> respuestaConsulta = consultaRepositorio.findById(idConsulta);

        if (respuestaConsulta.isPresent()) {

            Consulta consulta = respuestaConsulta.get();

            consulta.setDetalleConsulta(detalleConsulta);

            consulta.setEstudios(estudios);

            consultaRepositorio.save(consulta);

            // Agregar consulta a la historia clinica del paciente
            Optional<Paciente> respuestaPaciente = pacienteRepositorio.findById(consulta.getPaciente().getId());

            if (respuestaPaciente.isPresent()) {

                Paciente paciente = respuestaPaciente.get();

                if (paciente.getIdHistoria() == null) {

                    List<Consulta> primeraConsulta = new ArrayList();

                    primeraConsulta.add(consulta);
                    
                    paciente.setIdHistoria(primeraConsulta);

                } else {

                    List<Consulta> consultas = paciente.getIdHistoria();

                    consultas.add(consulta);
                    
                    paciente.setIdHistoria(consultas);

                }

                pacienteRepositorio.save(paciente);

            }

        }

    }

    private void validar(String fecha, Horario horario, Profesional profesional, Modalidad modalidad, Double precio) throws MiException {

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
        if (precio == null) {
            throw new MiException("el precio no puede estar vacio"); //
        }

    }
}
