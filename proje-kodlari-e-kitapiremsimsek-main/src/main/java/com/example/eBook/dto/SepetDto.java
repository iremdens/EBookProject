package com.example.eBook.dto;

import lombok.Data;

@Data
public class SepetDto {
    private Integer sepetId;
    private Integer kitapId;
    private String kitapBaslik;
    private Integer adet;

    public Integer getSepetId() {
        return sepetId;
    }

    public void setSepetId(Integer sepetId) {
        this.sepetId = sepetId;
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

    public Integer getAdet() {
        return adet;
    }

    public void setAdet(Integer adet) {
        this.adet = adet;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;
}
