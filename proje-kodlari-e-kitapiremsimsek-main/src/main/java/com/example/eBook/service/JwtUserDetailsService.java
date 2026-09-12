package com.example.eBook.service;

import com.example.eBook.entity.Users;
import com.example.eBook.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));

        System.out.println("Kullanıcı: " + user.getEmail());
        System.out.println("Rol: " + user.getRol());
        System.out.println("Yetki: ROLE_" + user.getRol().toUpperCase(Locale.ROOT));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getSifre(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol().toUpperCase(Locale.ROOT)))
        );
    }

    // 🔥 Eklenen yeni metot
    public Users getUserEntityByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
    }
}
