//package com.safa.safaeventosbbdd.controladores;
//
//import com.safa.safaeventosbbdd.dto.AuthenticationResponseDTO;
//import com.safa.safaeventosbbdd.security.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody AuthenticationResponseDTO request) {
//
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia());
//
//        var auth = authenticationManager.authenticate(authToken);
//
//        String token = tokenService.generarToken(request.getEmail());
//
//        return Map.of("token", token);
//    }
//}
