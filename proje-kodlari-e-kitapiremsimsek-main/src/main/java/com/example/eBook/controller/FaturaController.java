package com.example.eBook.controller;

import com.example.eBook.dto.FaturaDto;
import com.example.eBook.entity.Fatura;
import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Users;
import com.example.eBook.service.FaturaService;
import com.example.eBook.service.SiparisService;
import com.example.eBook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faturalar")
public class FaturaController {

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private SiparisService siparisService;

    @Autowired
    private UsersService usersService;

    // Kullanıcının faturasını getir (ödeme yöntemi GİZLİ)
    @GetMapping("/siparis/{siparisId}")
    @PreAuthorize("isAuthenticated()")
    public FaturaDto getBySiparis(@PathVariable Integer siparisId) {
        Siparis siparis = siparisService.getById(siparisId);
        return toDtoForUser(faturaService.getBySiparis(siparis));
    }

    // Kullanıcının tüm faturalarını getir
    @GetMapping("/kullanici/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<FaturaDto> getByUser(@PathVariable Integer userId) {
        Users user = usersService.getUserById(userId);
        return faturaService.getFaturalarByUser(user).stream()
                .map(this::toDtoForUser)
                .collect(Collectors.toList());
    }

    // Admin tüm faturaları görsün (ödeme yöntemi dahil)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FaturaDto> getAll() {
        return faturaService.getAll().stream()
                .map(this::toDtoForAdmin)
                .collect(Collectors.toList());
    }

    // --- DTO dönüşümleri ---

    private FaturaDto toDtoForUser(Fatura fatura) {
        FaturaDto dto = new FaturaDto();
        dto.setFaturaId(fatura.getFaturaId());
        dto.setSiparisId(fatura.getSiparis().getSiparisId());
        dto.setTutar(fatura.getTutar());
        dto.setTarih(fatura.getTarih());
        dto.setUserId(fatura.getSiparis().getUser().getUserId());
        dto.setUserAdSoyad(fatura.getSiparis().getUser().getAdSoyad());
        return dto;
    }

    private FaturaDto toDtoForAdmin(Fatura fatura) {
        FaturaDto dto = toDtoForUser(fatura);
        dto.setOdemeYontemi(fatura.getSiparis().getOdemeYontemi()); // sadece admin görür
        return dto;
    }
}
