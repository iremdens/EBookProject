package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "fatura")
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fatura_id")
    private Integer faturaId;

    @OneToOne
    @JoinColumn(name = "siparis_id", nullable = false, unique = true)
    private Siparis siparis;

    @Column(name = "tutar", precision = 10, scale = 2, nullable = false)
    private BigDecimal tutar;

    @Column(name = "tarih")
    private LocalDateTime tarih = LocalDateTime.now();
}
