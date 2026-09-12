package com.example.eBook.controller;

import com.example.eBook.dto.KategoriDto;
import com.example.eBook.entity.Kategori;
import com.example.eBook.service.KategoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kategoriler")
public class KategoriController {

    @Autowired
    private KategoriService kategoriService;

    // ✅ 1. Tüm kategorileri getir
    @GetMapping
    @PreAuthorize("permitAll()")
    public List<KategoriDto> getAll() {
        return kategoriService.getAllKategoriler()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ 2. ID ile kategori getir
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public KategoriDto getById(@PathVariable Integer id) {
        return toDto(kategoriService.getKategoriById(id));
    }

    // ✅ 3. Yeni kategori oluştur
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public KategoriDto create(@RequestBody KategoriDto dto) {
        Kategori kategori = new Kategori();
        kategori.setTur(dto.getTur());
        return toDto(kategoriService.createKategori(kategori));
    }

    // ✅ 4. Kategori güncelle
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public KategoriDto update(@PathVariable Integer id, @RequestBody KategoriDto dto) {
        Kategori kategori = new Kategori();
        kategori.setTur(dto.getTur());
        return toDto(kategoriService.updateKategori(id, kategori));
    }

    // ✅ 5. Kategori sil
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        kategoriService.deleteKategori(id);
    }

    // --- DTO dönüşüm metodu ---
    private KategoriDto toDto(Kategori k) {
        KategoriDto dto = new KategoriDto();
        dto.setId(k.getKategoriId());
        dto.setTur(k.getTur());
        return dto;
    }
}
