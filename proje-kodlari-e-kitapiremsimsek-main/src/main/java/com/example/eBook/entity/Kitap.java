package com.example.eBook.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "kitap")
public class Kitap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kitap_id")
    private Integer kitapId;

    @Column(name = "baslik", length = 250, nullable = false)
    private String baslik;

    @Column(name = "yazar", length = 250, nullable = false)
    private String yazar;

    @Column(name = "fiyat", precision = 10, scale = 2, nullable = false)
    private BigDecimal fiyat;

    @ManyToOne
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    @ManyToOne
    @JoinColumn(name = "ekleyen_id", nullable = false)
    private Users ekleyen; // kitabı ekleyen kullanıcı (user veya admin)

    @Column(name = "onayli")
    private Boolean onayli = false; // varsayılan false

    @Column(name = "aciklama", columnDefinition = "TEXT")
    private String aciklama;

    @Column(name = "gorsel_url", length = 500)
    private String gorselUrl;

}
