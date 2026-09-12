package com.example.eBook.controller;

import com.example.eBook.dto.KitapCreateDto;
import com.example.eBook.dto.KitapDto;
import com.example.eBook.entity.Kategori;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Users;
import com.example.eBook.service.KategoriService;
import com.example.eBook.service.KitapService;
import com.example.eBook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kitaplar")
public class KitapController {

    @Autowired
    private KitapService kitapService;

    @Autowired
    private KategoriService kategoriService;

    @Autowired
    private UsersService usersService;

    // ✅ 1. Tüm onaylı kitapları getir
    @GetMapping("/onayli")
    @PreAuthorize("isAuthenticated()")
    public List<KitapDto> getOnayliKitaplar() {
        return kitapService.getOnayliKitaplar().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 2. Başlığa göre arama
    @GetMapping("/ara")
    @PreAuthorize("isAuthenticated()")
    public List<KitapDto> searchByBaslik(@RequestParam String baslik) {
        return kitapService.searchByBaslik(baslik).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 3. Kategoriye göre filtreleme
    @GetMapping("/kategori/{kategoriId}")
    @PreAuthorize("isAuthenticated()")
    public List<KitapDto> getByKategori(@PathVariable Integer kategoriId) {
        Kategori kategori = kategoriService.getKategoriById(kategoriId);
        return kitapService.getByKategori(kategori).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 4. Kitap oluştur
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public KitapDto createKitap(@RequestBody KitapCreateDto dto) {
        Kategori kategori = kategoriService.getKategoriById(dto.getKategoriId());
        Users ekleyen = usersService.getUserById(dto.getEkleyenId());

        Kitap kitap = new Kitap();
        kitap.setBaslik(dto.getBaslik());
        kitap.setYazar(dto.getYazar());
        kitap.setFiyat(dto.getFiyat());
        kitap.setKategori(kategori);
        kitap.setEkleyen(ekleyen);
        kitap.setAciklama(dto.getAciklama());
        kitap.setOnayli(false); // her zaman öneri olarak gelir
        kitap.setGorselUrl(dto.getGorselUrl());


        return toDto(kitapService.createKitap(kitap));
    }



    // ✅ 5. Kitap güncelle
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public KitapDto updateKitap(@PathVariable Integer id, @RequestBody KitapCreateDto dto) {
        Kategori kategori = kategoriService.getKategoriById(dto.getKategoriId());
        Users ekleyen = usersService.getUserById(dto.getEkleyenId());
// Önceki kitabı al
        Kitap mevcut = kitapService.getById(id);
        Kitap kitap = new Kitap();
        kitap.setBaslik(dto.getBaslik());
        kitap.setYazar(dto.getYazar());
        kitap.setFiyat(dto.getFiyat());
        kitap.setKategori(kategori);
        kitap.setEkleyen(ekleyen);
        kitap.setAciklama(dto.getAciklama());
        kitap.setGorselUrl(dto.getGorselUrl());

        kitap.setOnayli(mevcut.getOnayli()); // ⬅️ Onay durumu korunur

        return toDto(kitapService.updateKitap(id, kitap));
    }

    // ✅ 6. Kitap sil
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteKitap(@PathVariable Integer id) {
        kitapService.deleteKitap(id);
    }

    // ✅ 7. Onay bekleyen kitaplar (admin için)
    @GetMapping("/onaysiz")
    @PreAuthorize("hasRole('ADMIN')")
    public List<KitapDto> getOnayBekleyenler() {
        return kitapService.getOnayBekleyenKitaplar().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 8. Kitap onaylama (sadece admin)
    @PutMapping("/{id}/onayla")
    @PreAuthorize("hasRole('ADMIN')")
    public KitapDto approveKitap(@PathVariable Integer id) {
        Kitap kitap = kitapService.getById(id);
        kitap.setOnayli(true);
        return toDto(kitapService.updateKitap(id, kitap));
    }

    // ✅ 9. Admin Kitap Ekle (sadece admin)
    @PostMapping("/admin-ekle")
    @PreAuthorize("hasRole('ADMIN')")
    public KitapDto addBookAsAdmin(@RequestBody KitapCreateDto dto) {
        Kategori kategori = kategoriService.getKategoriById(dto.getKategoriId());
        Users ekleyen = usersService.getUserById(dto.getEkleyenId());

        Kitap kitap = new Kitap();
        kitap.setBaslik(dto.getBaslik());
        kitap.setYazar(dto.getYazar());
        kitap.setFiyat(dto.getFiyat());
        kitap.setAciklama(dto.getAciklama());
        kitap.setKategori(kategori);
        kitap.setEkleyen(ekleyen);
        kitap.setGorselUrl(dto.getGorselUrl());
        kitap.setOnayli(true); // ✅ otomatik onaylı

        return toDto(kitapService.createKitap(kitap));
    }


    // ✅ 10. Kullanıcının kendi kitapları
    @GetMapping("/ekleyen/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<KitapDto> getByEkleyen(@PathVariable Integer userId) {
        Users user = usersService.getUserById(userId);
        return kitapService.getByEkleyen(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 11. ID’ye göre kitap getir (BookDetail.js için)
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public KitapDto getById(@PathVariable Integer id) {
        return toDto ( kitapService.getById(id));
    }

    // ✅ 12. Başlığa göre kitap getir (tek kitap)
    @GetMapping("/baslik/{baslik}")
    @PreAuthorize("isAuthenticated()")
    public KitapDto getByBaslik(@PathVariable String baslik) {
        return toDto(kitapService.getByBaslik(baslik));
    }

    // ✅ 13. Başlığa göre kitap getir (Başlık ya da yazar)
    @GetMapping("/ikiliara")
    @PreAuthorize("isAuthenticated()")
    public List<KitapDto> searchByBaslikOrYazar(@RequestParam String query) {
        return kitapService.searchByBaslikOrYazar(query).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 14. onaylı kiapları getir(UserIdye göre)
    @GetMapping("/onayli/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<KitapDto> getOnayliKitaplarByUser(@PathVariable Integer userId) {
        Users user = usersService.getUserById(userId);
        return kitapService.getOnayliKitaplarByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 15. görsel yükle
    @PostMapping("/{id}/gorsel-yukle")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> uploadGorsel(@PathVariable Integer id,
                                               @RequestParam("file") MultipartFile file) {
        try {
            Kitap kitap = kitapService.getById(id);

            // Benzersiz dosya adı
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get("uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // URL'yi kaydet (örnek: /uploads/abc.jpg)
            String fileUrl = "/uploads/" + fileName;
            kitap.setGorselUrl(fileUrl);
            kitapService.updateKitap(id, kitap);

            return ResponseEntity.ok("Görsel yüklendi: " + fileUrl);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Hata: " + e.getMessage());
        }
    }


    // --- Yardımcı DTO dönüşüm metodu ---
    private KitapDto toDto(Kitap kitap) {
        KitapDto dto = new KitapDto();
        dto.setId(kitap.getKitapId());
        dto.setBaslik(kitap.getBaslik());
        dto.setYazar(kitap.getYazar());
        dto.setFiyat(kitap.getFiyat());
        dto.setKategoriAdi(kitap.getKategori().getTur());
        dto.setEkleyenAd(kitap.getEkleyen().getAdSoyad());
        dto.setOnayli(kitap.getOnayli());
        dto.setAciklama(kitap.getAciklama());
        dto.setGorselUrl(kitap.getGorselUrl());
        return dto;
    }
}
