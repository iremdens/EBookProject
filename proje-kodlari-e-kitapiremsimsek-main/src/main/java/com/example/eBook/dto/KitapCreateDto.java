package com.example.eBook.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KitapCreateDto {
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

    public Integer getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Integer kategoriId) {
        this.kategoriId = kategoriId;
    }

    public Integer getEkleyenId() {
        return ekleyenId;
    }

    public void setEkleyenId(Integer ekleyenId) {
        this.ekleyenId = ekleyenId;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    private Integer kategoriId;
    private Integer ekleyenId;
    private String aciklama;
}