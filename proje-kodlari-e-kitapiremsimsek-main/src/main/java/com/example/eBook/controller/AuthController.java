package com.example.eBook.controller;

import com.example.eBook.dto.LoginRequest;
import com.example.eBook.entity.Users;
import com.example.eBook.security.JwtTokenUtil;
import com.example.eBook.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {

            String email = request.getEmail().toLowerCase(); // lower yapıyorum
            // Kimlik doğrulama
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, request.getSifre())
            );

            // Kullanıcıyı getir
            UserDetails user = userDetailsService.loadUserByUsername(email);

            // Token üret
            Users kullanici = userDetailsService.getUserEntityByEmail(email);
            String token = jwtTokenUtil.generateToken(email, kullanici.getRol(), kullanici.getUserId());



            return ResponseEntity.ok(Map.of("token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Geçersiz email veya şifre");
        }
    }

}
