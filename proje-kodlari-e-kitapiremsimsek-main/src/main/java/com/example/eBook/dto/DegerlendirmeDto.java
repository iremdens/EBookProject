package com.example.eBook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DegerlendirmeDto {
    private Integer id;
    private Integer kitapId;
    private String kitapBaslik;
    private Integer userId;
    private String userAdSoyad;
    private Integer puan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKitapId() {
        return kitapId;
    }

    public void setKitapId(Integer kitapId) {
        this.kitapId = kitapId;
    }

    public String getKitapBaslik() {
        return kitapBaslik;
    }

    public void setKitapBaslik(String kitapBaslik) {
        this.kitapBaslik = kitapBaslik;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAdSoyad() {
        return userAdSoyad;
    }

    public void setUserAdSoyad(String userAdSoyad) {
        this.userAdSoyad = userAdSoyad;
    }

    public Integer getPuan() {
        return puan;
    }

    public void setPuan(Integer puan) {
        this.puan = puan;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
    }

    private String yorum;
    private LocalDateTime tarih;
}
