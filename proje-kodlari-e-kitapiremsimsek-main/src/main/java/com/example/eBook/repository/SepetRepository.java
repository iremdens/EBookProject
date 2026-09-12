package com.example.eBook.repository;

import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Sepet;
import com.example.eBook.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SepetRepository extends JpaRepository<Sepet, Integer> {
    List<Sepet> findByUser(Users user);
    Optional<Sepet> findByUserAndKitap(Users user, Kitap kitap); // sepet güncelleme için
    void deleteByUser(Users user); // siparişten sonra sepeti temizleme
}
