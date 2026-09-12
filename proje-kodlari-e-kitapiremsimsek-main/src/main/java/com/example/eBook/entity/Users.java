package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "adsoyad", length = 100, nullable = false)
    private String adSoyad;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "sifre", length = 100, nullable = false)
    private String sifre;

    @Column(name = "adres", length = 255)
    private String adres;

    @Column(name = "rol", length = 20, nullable = false)
    private String rol = "user"; // default olarak user atanır
}
