package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "degerlendirme")
public class Degerlendirme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degerlendirme_id")
    private Integer degerlendirmeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;

    @Column(name = "puan", nullable = false)
    private Integer puan;

    @Column(name = "yorum", columnDefinition = "TEXT")
    private String yorum;

    @Column(name = "tarih")
    private LocalDateTime tarih = LocalDateTime.now(); // değerlendirme tarihi
}
