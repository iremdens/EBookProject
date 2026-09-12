package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "siparis_kitap")
public class Siparis_Kitap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siparis_kitap_id")
    private Integer siparisKitapId;

    @ManyToOne
    @JoinColumn(name = "siparis_id", nullable = false)
    private Siparis siparis;

    @ManyToOne
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;

    @Column(name = "adet", nullable = false)
    private Integer adet = 1;

    @Column(name = "birim_fiyat", precision = 10, scale = 2, nullable = false)
    private BigDecimal birimFiyat;
}
