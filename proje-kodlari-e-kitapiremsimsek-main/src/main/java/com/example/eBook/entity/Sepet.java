package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sepet")
public class Sepet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sepet_id")
    private Integer sepetId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;

    @Column(name = "adet", nullable = false)
    private Integer adet = 1;
}
