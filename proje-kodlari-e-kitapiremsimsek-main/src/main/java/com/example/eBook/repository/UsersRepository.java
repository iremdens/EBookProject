package com.example.eBook.repository;

import com.example.eBook.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    List<Users> findByRol(String rol); // "user" veya "admin" listesi için
    boolean existsByEmail(String email);
}

