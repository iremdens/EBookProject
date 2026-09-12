package com.example.eBook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public class KitapDto {
    private Integer id;
    private String baslik;
    private String yazar;
    private BigDecimal fiyat;
    private String gorselUrl;



    public String getGorselUrl() {
        return gorselUrl;
    }

    public void setGorselUrl(String gorselUrl) {
        this.gorselUrl = gorselUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        this.yazar = yazar;
    }

    public BigDecimal getFiyat() {
        return fiyat;
    }

    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public String getEkleyenAd() {
        return ekleyenAd;
    }

    public void setEkleyenAd(String ekleyenAd) {
        this.ekleyenAd = ekleyenAd;
    }

    public Boolean getOnayli() {
        return onayli;
    }

    public void setOnayli(Boolean onayli) {
        this.onayli = onayli;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    private String kategoriAdi;
    private String ekleyenAd;
    private Boolean onayli;
    private String aciklama;
}