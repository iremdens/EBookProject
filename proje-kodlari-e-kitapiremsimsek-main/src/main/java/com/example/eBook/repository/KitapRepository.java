package com.example.eBook.repository;

import com.example.eBook.entity.Kategori;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KitapRepository extends JpaRepository<Kitap, Integer> {
    List<Kitap> findByOnayliTrue(); // kullanıcılar sadece onaylı kitapları görür
    List<Kitap> findByKategori(Kategori kategori);
    List<Kitap> findByBaslikContainingIgnoreCase(String baslik); // arama filtresi
    List<Kitap> findByEkleyen(Users user); // kullanıcıya ait kitaplar
    List<Kitap> findByOnayliFalse(); // admin onay listesi

    Optional<Kitap> findByBaslik(String baslik);

    List<Kitap> findByBaslikContainingIgnoreCaseOrYazarContainingIgnoreCase(String baslik, String yazar);

    List<Kitap> findByEkleyenAndOnayliTrue(Users user);



}

