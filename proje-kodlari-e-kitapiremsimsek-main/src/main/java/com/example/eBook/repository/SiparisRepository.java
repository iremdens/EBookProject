package com.example.eBook.repository;

import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiparisRepository extends JpaRepository<Siparis, Integer> {
    List<Siparis> findByUser(Users user);
}
