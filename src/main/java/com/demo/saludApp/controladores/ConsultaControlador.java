
import com.demo.saludApp.entidades.Consulta;
import com.demo.saludApp.entidades.Paciente;
import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.enumeraciones.Estado;
import com.demo.saludApp.servicios.ConsultaServicio;
import com.demo.saludApp.servicios.PacienteServicio;
import com.demo.saludApp.servicios.ProfesionalServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //Declara un controlador para la gestion de la comunicación usuario/aplicación
@RequestMapping("/paciente") //Mapea la ruta de la petición y el método del controlador
public class ConsultaControlador {  
    
    @Autowired
    private PacienteServicio pacienteS;
    @Autowired
    private ProfesionalServicio profesionalS;
    @Autowired
    private ConsultaServicio consultaS;     
    
    //------------- Vista General -------------
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_PROFESIONAL')")
    @GetMapping("/historia/{id}") //asigna solicitudes HTTP GET
    public String vistaHistoria(HttpSession session,@PathVariable String id, ModelMap modelo) {
        
      Usuario logueado = (Paciente) session.getAttribute("usuariosession");  
      List<Consulta> consultas = consultaS.buscarPorPacientePorEstado(logueado.getId(), Estado.UTILIZADA);
        modelo.addAttribute("consultas", consultas);   
        return "historiaClinica.html";
    }
}