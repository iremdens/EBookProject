package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "siparis")
public class Siparis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siparis_id")
    private Integer siparisId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "tarih")
    private LocalDateTime tarih = LocalDateTime.now();

    @Column(name = "durum", length = 20)
    private String durum = "hazırlanıyor"; // varsayılan değer

    @Column(name = "odemeyontemi", length = 50)
    private String odemeYontemi;
}
