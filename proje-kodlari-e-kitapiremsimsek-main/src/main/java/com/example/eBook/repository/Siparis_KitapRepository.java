package com.example.eBook.repository;

import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Siparis_Kitap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Siparis_KitapRepository extends JpaRepository<Siparis_Kitap, Integer> {
    List<Siparis_Kitap> findBySiparis(Siparis siparis);
}
