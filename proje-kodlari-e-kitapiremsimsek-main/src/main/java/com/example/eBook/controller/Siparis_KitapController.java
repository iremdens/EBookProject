package com.example.eBook.controller;

import com.example.eBook.dto.Siparis_KitapDto;
import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Siparis_Kitap;
import com.example.eBook.service.Siparis_KitapService;
import com.example.eBook.service.SiparisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/siparis-kitaplar")
public class Siparis_KitapController {

    @Autowired
    private Siparis_KitapService siparis_KitapService;

    @Autowired
    private SiparisService siparisService;

    // ✅ 1. Siparişe ait kitapları listele
    @GetMapping("/siparis/{siparisId}")
    @PreAuthorize("isAuthenticated()")
    public List<Siparis_KitapDto> getBySiparis(@PathVariable Integer siparisId) {
        Siparis siparis = siparisService.getById(siparisId);
        List<Siparis_Kitap> liste = siparis_KitapService.getBySiparis(siparis);
        return liste.stream().map(this::toDto).collect(Collectors.toList());
    }

    // ✅ 2. Tekil kayıt getir (isteğe bağlı)
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Siparis_KitapDto getById(@PathVariable Integer id) {
        return toDto(siparis_KitapService.getById(id));
    }

    // --- DTO dönüşüm ---
    private Siparis_KitapDto toDto(Siparis_Kitap sk) {
        Siparis_KitapDto dto = new Siparis_KitapDto();
        dto.setId(sk.getSiparisKitapId());
        dto.setSiparisId(sk.getSiparis().getSiparisId());
        dto.setKitapId(sk.getKitap().getKitapId());
        dto.setKitapBaslik(sk.getKitap().getBaslik());
        dto.setAdet(sk.getAdet());
        dto.setBirimFiyat(sk.getBirimFiyat());
        dto.setToplamFiyat(sk.getBirimFiyat().multiply(BigDecimal.valueOf(sk.getAdet())));
        return dto;
    }
}
