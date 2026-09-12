package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "kategori")
public class Kategori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kategori_id")
    private Integer kategoriId;

    @Column(name = "tur", length = 100, nullable = false)
    private String tur;

    @Column(name = "kategori_details", length = 250)
    private String kategoriDetails;
}
