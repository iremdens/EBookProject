package com.example.eBook.repository;

import com.example.eBook.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KategoriRepository extends JpaRepository<Kategori, Integer> {
    Optional<Kategori> findByTur(String tur);
}

