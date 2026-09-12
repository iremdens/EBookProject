package com.example.eBook.controller;

import com.example.eBook.dto.SiparisCreateDto;
import com.example.eBook.dto.SiparisDto;
import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Users;
import com.example.eBook.service.SiparisService;
import com.example.eBook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/siparisler")
public class SiparisController {

    @Autowired
    private SiparisService siparisService;

    @Autowired
    private UsersService usersService;

    // ✅ 1. Kullanıcının siparişlerini getir
    @GetMapping("/kullanici/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<SiparisDto> getByUser(@PathVariable Integer userId) {
        Users user = usersService.getUserById(userId);
        return siparisService.getSiparisByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Admin tüm siparişleri görsün
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SiparisDto> getAllSiparisler() {
        return siparisService.getAllSiparisler().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // ✅ 2. Sipariş oluştur
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public SiparisDto createSiparis(@RequestBody SiparisCreateDto dto) {
        // Token'daki kullanıcı bilgisini al
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // JWT'deki "sub" alanı → email

        // Email ile kullanıcıyı bul
        Users user = usersService.getByEmail(email);

        // Siparişi oluştur
        Siparis siparis = siparisService.createSiparis(user, dto.getOdemeYontemi());
        return toDto(siparis);
    }

    // ✅ 3. Siparişi iptal et (admin)
    @PutMapping("/{id}/iptal")
    @PreAuthorize("hasRole('ADMIN')")
    public SiparisDto cancel(@PathVariable Integer id) {
        return toDto(siparisService.cancelSiparis(id));
    }

    // ✅ 4. Siparişi ID ile getir
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public SiparisDto getById(@PathVariable Integer id) {
        return toDto(siparisService.getById(id));
    }

    // --- Yardımcı DTO dönüşüm metodu ---
    private SiparisDto toDto(Siparis s) {
        SiparisDto dto = new SiparisDto();
        dto.setSiparisId(s.getSiparisId());
        dto.setUserId(s.getUser().getUserId());
        dto.setUserAdSoyad(s.getUser().getAdSoyad());
        dto.setOdemeYontemi(s.getOdemeYontemi());
        dto.setDurum(s.getDurum());
        dto.setTarih(s.getTarih());
        return dto;
    }
}
