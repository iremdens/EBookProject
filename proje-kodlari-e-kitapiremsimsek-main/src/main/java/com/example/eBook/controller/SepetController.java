package com.example.eBook.controller;

import com.example.eBook.dto.SepetCreateDto;
import com.example.eBook.dto.SepetDto;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Sepet;
import com.example.eBook.entity.Users;
import com.example.eBook.service.KitapService;
import com.example.eBook.service.SepetService;
import com.example.eBook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sepet")
public class SepetController {

    @Autowired
    private SepetService sepetService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private KitapService kitapService;

    // ✅ 1. Sepeti getir
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<SepetDto> getSepetByUser(@PathVariable Integer userId) {
        Users user = usersService.getUserById(userId);
        return sepetService.getSepetByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 2. Sepete ürün ekle
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public SepetDto addToSepet(@RequestBody SepetCreateDto dto) {
        Users user = usersService.getUserById(dto.getUserId());
        Kitap kitap = kitapService.getById(dto.getKitapId());

        Sepet sepet = sepetService.addToSepet(user, kitap, dto.getAdet());
        return toDto(sepet);
    }

    // ✅ 3. Sepetteki ürünü sil
    @DeleteMapping("/{sepetId}")
    @PreAuthorize("isAuthenticated()")
    public void deleteFromSepet(@PathVariable Integer sepetId) {
        sepetService.removeFromSepet(sepetId);
    }

    // ✅ 4. Sepetteki ürünün adedini güncelle
    @PutMapping("/adet/{sepetId}")
    @PreAuthorize("isAuthenticated()")
    public SepetDto updateAdet(@PathVariable Integer sepetId, @RequestBody Map<String, Integer> body) {
        Integer yeniAdet = body.get("adet");
        Sepet updated = sepetService.updateAdet(sepetId, yeniAdet);
        return updated != null ? toDto(updated) : null;
    }


    // --- Yardımcı DTO dönüşüm metodu ---
    private SepetDto toDto(Sepet s) {
        SepetDto dto = new SepetDto();
        dto.setSepetId(s.getSepetId());
        dto.setKitapId(s.getKitap().getKitapId());
        dto.setKitapBaslik(s.getKitap().getBaslik());
        dto.setAdet(s.getAdet());
        dto.setUserId(s.getUser().getUserId());
        return dto;
    }
}
