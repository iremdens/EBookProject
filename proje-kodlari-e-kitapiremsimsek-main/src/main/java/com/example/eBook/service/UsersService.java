package com.example.eBook.service;

import com.example.eBook.entity.Users;
import com.example.eBook.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    // Tüm kullanıcıları getir (admin paneli için)
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Rol bazlı kullanıcıları getir
    public List<Users> getUsersByRol(String rol) {
        return usersRepository.findByRol(rol);
    }

    // Yeni kullanıcı oluştur
    public Users createUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu e-posta zaten kayıtlı.");
        }

        user.setEmail(user.getEmail().toLowerCase()); // ✅ email normalize
        user.setSifre(passwordEncoder.encode(user.getSifre())); // ✅ şifre hash


        return usersRepository.save(user);
    }

    // ID ile kullanıcı getir
    public Users getUserById(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    public Users getByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    // Kullanıcı güncelle
    public Users updateUser(Integer id, Users userDetails) {
        Users user = getUserById(id);
        user.setAdSoyad(userDetails.getAdSoyad());
        user.setEmail(userDetails.getEmail());
        user.setSifre(userDetails.getSifre());
        user.setAdres(userDetails.getAdres());
        user.setRol(userDetails.getRol());
        return usersRepository.save(user);
    }

    // Kullanıcı sil
    public void deleteUser(Integer id) {
        usersRepository.deleteById(id);
    }

   /* // Giriş işlemi
    public Users login(String email, String sifre) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        if (!user.getSifre().equals(sifre)) {
            throw new RuntimeException("Geçersiz şifre!");
        }
        return user;
    }
    */

}
