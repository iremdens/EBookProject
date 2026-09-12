package com.example.eBook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SiparisDto {
    private Integer siparisId;
    private Integer userId;
    private String userAdSoyad;
    private String odemeYontemi;

    public Integer getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(Integer siparisId) {
        this.siparisId = siparisId;
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

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
    }

    private String durum;
    private LocalDateTime tarih;
}
