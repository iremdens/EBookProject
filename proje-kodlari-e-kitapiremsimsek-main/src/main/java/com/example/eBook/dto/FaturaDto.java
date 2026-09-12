package com.example.eBook.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FaturaDto {
    private Integer faturaId;
    private Integer siparisId;
    private BigDecimal tutar;
    private LocalDateTime tarih;
    private String odemeYontemi;

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }

    public Integer getFaturaId() {
        return faturaId;
    }

    public void setFaturaId(Integer faturaId) {
        this.faturaId = faturaId;
    }

    public Integer getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(Integer siparisId) {
        this.siparisId = siparisId;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
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

    private Integer userId;
    private String userAdSoyad;
}
