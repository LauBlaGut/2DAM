//package com.safa.safaeventosbbdd.security;
//
//import com.safa.safaeventosbbdd.modelos.Usuario;
//import com.safa.safaeventosbbdd.servicios.UsuarioService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@AllArgsConstructor
//public class JWTFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String username;
//
//        // Permitir rutas de autenticación sin JWT
//        if (request.getServletPath().contains("/auth/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Revisar que exista el header y comience con "Bearer "
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Extraer token y username
//        jwt = authHeader.substring(7);
//        username = jwtService.extractUsername(jwt);
//
//        // Verificar que no haya autenticación previa
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Cargar usuario de la BBDD
//            Usuario usuario = usuarioService.loadUserByUsername(username);
//
//            // Validar token
//            if (jwtService.isTokenValid(jwt, usuario)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                usuario.getNombreUsuario(),
//                                usuario.getPassword(),
//                                List.of(new SimpleGrantedAuthority(usuario.getRol().name()))
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}