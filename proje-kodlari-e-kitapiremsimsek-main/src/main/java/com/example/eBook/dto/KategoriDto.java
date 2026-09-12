package com.example.eBook.dto;

import lombok.Data;

@Data
public class KategoriDto {
    private Integer id;
    private String tur;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }
}
