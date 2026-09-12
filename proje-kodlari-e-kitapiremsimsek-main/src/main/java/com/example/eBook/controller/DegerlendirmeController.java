package com.example.eBook.controller;

import com.example.eBook.dto.DegerlendirmeCreateDto;
import com.example.eBook.dto.DegerlendirmeDto;
import com.example.eBook.entity.Degerlendirme;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Users;
import com.example.eBook.service.DegerlendirmeService;
import com.example.eBook.service.FaturaService;
import com.example.eBook.service.KitapService;
import com.example.eBook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/degerlendirmeler")
public class DegerlendirmeController {

    @Autowired
    private DegerlendirmeService degerlendirmeService;

    @Autowired
    private KitapService kitapService;

    @Autowired
    private UsersService usersService;

    // ✅ 1. Yorum oluştur
    @Autowired
    private FaturaService faturaService;

    @PostMapping("/yorum")
    @PreAuthorize("isAuthenticated()")
    public DegerlendirmeDto createDegerlendirme(@RequestBody DegerlendirmeCreateDto dto) {
        System.out.println("🎯 [YORUM] Giriş yapıldı. Kullanıcı ID: " + dto.getUserId() + ", Kitap ID: " + dto.getKitapId());

        Kitap kitap = kitapService.getById(dto.getKitapId());
        Users user = usersService.getUserById(dto.getUserId());

        // Satın alma kontrolü
        boolean satinAlinmis = faturaService.hasUserPurchasedKitap(user.getUserId(), kitap.getKitapId());
        System.out.println("✅ [YORUM] Kullanıcı kitabı satın almış mı? " + satinAlinmis);

        if (!satinAlinmis) {
            System.out.println("❌ [YORUM] Kullanıcı bu kitabı satın almamış.");
            throw new RuntimeException("Bu kitabı satın almadığınız için yorum yapamazsınız.");
        }

        // Daha önce yorum yapmış mı
        if (degerlendirmeService.getDegerlendirmeByUserAndKitap(user, kitap) != null) {
            System.out.println("❌ [YORUM] Daha önce yorum yapılmış.");
            throw new RuntimeException("Aynı kullanıcı aynı kitaba tekrar yorum yapamaz.");
        }

        System.out.println("✅ [YORUM] Yeni yorum oluşturuluyor...");

        Degerlendirme d = new Degerlendirme();
        d.setKitap(kitap);
        d.setUser(user);
        d.setPuan(dto.getPuan());
        d.setYorum(dto.getYorum());

        return toDto(degerlendirmeService.createDegerlendirme(d));
    }



    @GetMapping("/kitap/{kitapId}")
    @PreAuthorize("permitAll()")
    public List<DegerlendirmeDto> getByKitap(@PathVariable Integer kitapId) {
        System.out.println("[Controller] kitapId: " + kitapId);

        List<Degerlendirme> yorumlar = degerlendirmeService.getDegerlendirmelerByKitapId(kitapId);
        System.out.println("[Controller] Gelen yorum adedi: " + yorumlar.size());

        return yorumlar.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // ✅ 3. Kullanıcının tüm yorumları
    @GetMapping("/kullanici/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DegerlendirmeDto> getByUser(@PathVariable Integer userId) {
        Users user = usersService.getUserById(userId);
        return degerlendirmeService.getDegerlendirmelerByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 4. Tekil yorum görüntüleme
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DegerlendirmeDto getById(@PathVariable Integer id) {
        return toDto(degerlendirmeService.getById(id));
    }

    // ✅ 5. Güncelleme
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public DegerlendirmeDto update(@PathVariable Integer id, @RequestBody DegerlendirmeCreateDto dto) {
        Degerlendirme existing = degerlendirmeService.getById(id);
        existing.setPuan(dto.getPuan());
        existing.setYorum(dto.getYorum());
        return toDto(degerlendirmeService.updateDegerlendirme(id, existing));
    }

    // ✅ 6. Silme
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void delete(@PathVariable Integer id) {
        degerlendirmeService.deleteDegerlendirme(id);
    }

    // --- Yardımcı dönüşüm metodu ---
    private DegerlendirmeDto toDto(Degerlendirme d) {
        DegerlendirmeDto dto = new DegerlendirmeDto();
        dto.setId(d.getDegerlendirmeId());
        dto.setKitapId(d.getKitap().getKitapId());
        dto.setKitapBaslik(d.getKitap().getBaslik());
        dto.setUserId(d.getUser().getUserId());
        dto.setUserAdSoyad(d.getUser().getAdSoyad());
        dto.setPuan(d.getPuan());
        dto.setYorum(d.getYorum());
        dto.setTarih(d.getTarih());
        return dto;
    }
}
