package com.example.eBook.repository;

import com.example.eBook.entity.Fatura;
import com.example.eBook.entity.Siparis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FaturaRepository extends JpaRepository<Fatura, Integer> {

    Optional<Fatura> findBySiparis(Siparis siparis);


    @Query(value = """
    SELECT EXISTS (
      SELECT 1
      FROM siparis s
      JOIN siparis_kitap sk ON s.siparis_id = sk.siparis_id
      JOIN fatura f ON f.siparis_id = s.siparis_id
      WHERE s.user_id = :userId
        AND sk.kitap_id = :kitapId
    )
""", nativeQuery = true)
    boolean hasUserPurchasedKitap(
            @Param("userId") Integer userId,
            @Param("kitapId") Integer kitapId
    );


}

