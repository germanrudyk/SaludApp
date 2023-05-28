package com.demo.saludApp.servicios;

import com.demo.saludApp.entidades.Usuario;
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
    private UsuarioRepositorio usuarioRepositorio;
    
     //------------- Buscar Usuario -------------
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }   
    
    //------------- Eliminar Usuario -------------
    @Transactional
    public void eliminar(String id) {
       Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            usuarioRepositorio.delete(respuesta.get());
        }
    }
    
    //------------- Login Usuario -------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

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
