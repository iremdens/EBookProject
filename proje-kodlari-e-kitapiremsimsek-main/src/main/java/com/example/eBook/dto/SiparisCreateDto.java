package com.example.eBook.dto;

import lombok.Data;

@Data
public class SiparisCreateDto {
    private Integer userId;
    private String odemeYontemi;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }

}
