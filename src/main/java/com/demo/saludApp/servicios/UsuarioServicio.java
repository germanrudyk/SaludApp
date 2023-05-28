package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Usuario;
import com.demo.saludApp.excepciones.MiException;
import com.demo.saludApp.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author ILMAN
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    
     @Autowired
    private UsuarioRepositorio ur;
    
     //------------- Buscar Usuario -------------
    public Usuario getOne(String id) {
        return ur.getOne(id);
    }   
    
    //------------- Eliminar Usuario -------------
    @Transactional
    public void eliminar(String id) {
       Optional<Usuario> respuesta = ur.findById(id);
        if (respuesta.isPresent()) {
            ur.delete(respuesta.get());
        }
    }
    
    //------------- Cambiar Contraseña -------------
   @org.springframework.transaction.annotation.Transactional
    public void modificar(String id, String passwordAnterior, String passwordNuevo, String password2) throws MiException {
        
        
        Optional<Usuario> respuesta = ur.findById(id);
        
        Usuario usuario = respuesta.get();
        
        validar(passwordAnterior, passwordNuevo, password2, usuario.getPassword());

        if (respuesta.isPresent()) {

            usuario.setPassword(new BCryptPasswordEncoder().encode(password2));

            ur.save(usuario);
        }
    }
    
     //------------- Validar Contraseña -------------
    private void validar(String passwordAnterior, String passwordNuevo, String password2, String passwordActual) throws MiException{
        
        if (!passwordAnterior.equals(passwordActual)) {
            throw new MiException("La contraseña ingresada no coincide con la contraseña actual");
        }
       
        if (passwordNuevo.isEmpty() || passwordNuevo == null || passwordNuevo.length()<6) {
            throw new MiException("El email no puede ser nulo y debe tener mas de 5 digitos");
        }
        
        if (!passwordNuevo.equals(password2)) {
            throw new MiException("Las contraseñas ingresasdas deben ser iguales");
        }
    }
    
    //------------- Login Usuario -------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = ur.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = attr.getRequest().getSession(true);
            sesion.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombre(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
