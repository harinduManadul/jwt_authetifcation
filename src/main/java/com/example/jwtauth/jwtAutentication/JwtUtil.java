// package com.example.jwtauth.jwtAutentication;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtUtil {

//     private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // Use a strong secret key
//     private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

//     private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

//     // Generate JWT Token
//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     // Validate JWT Token
//     public boolean validateToken(String token, String username) {
//         return username.equals(extractUsername(token)) && !isTokenExpired(token);
//     }

//     // Extract Username from Token
//     public String extractUsername(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     // Check if Token is Expired
//     public boolean isTokenExpired(String token) {
//         return extractExpiration(token).before(new Date());
//     }

//     // Extract Expiration Date from Token
//     public Date extractExpiration(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getExpiration();
//     }
// }
