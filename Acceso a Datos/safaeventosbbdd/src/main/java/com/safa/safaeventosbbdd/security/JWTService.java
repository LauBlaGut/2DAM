//package com.safa.safaeventosbbdd.security;
//
//import com.safa.safaeventosbbdd.modelos.Usuario;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JWTService {
//
//    // Clave secreta para firmar tokens (debe ser segura y larga)
//    private static final String SECRET_KEY = "MI_SUPER_SECRETO_KEY_QUE_DEBERIA_SER_MUY_LARGA_Y_SEGURA_1234567890";
//
//    // Extraer username (nombreUsuario) del token
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // Extraer cualquier claim
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    // Generar token para un usuario
//    public String generateToken(Usuario usuario) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("rol", usuario.getRol().name());
//        return createToken(claims, usuario.getNombreUsuario());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        long expirationTime = 1000 * 60 * 60 * 10; // 10 horas
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    // Validar token
//    public boolean isTokenValid(String token, Usuario usuario) {
//        final String username = extractUsername(token);
//        return (username.equals(usuario.getNombreUsuario()) && !isTokenExpired(token));
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    // Obtener la clave secreta para firmar/verificar
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}