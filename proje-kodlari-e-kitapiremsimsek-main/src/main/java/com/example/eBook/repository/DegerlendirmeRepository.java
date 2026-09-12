package com.example.eBook.repository;

import com.example.eBook.entity.Degerlendirme;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DegerlendirmeRepository extends JpaRepository<Degerlendirme, Integer> {
    List<Degerlendirme> findByKitap(Kitap kitap);
    List<Degerlendirme> findByUser(Users user);
    Optional<Degerlendirme> findByUserAndKitap(Users user, Kitap kitap); // tekrar yorum kontrolü
    List<Degerlendirme> findByKitap_KitapId(Integer kitapId);


}

