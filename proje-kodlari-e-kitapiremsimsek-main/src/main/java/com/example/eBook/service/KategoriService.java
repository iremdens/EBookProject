package com.example.eBook.service;

import com.example.eBook.entity.Kategori;
import com.example.eBook.repository.KategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategoriService {

    @Autowired
    private KategoriRepository kategoriRepository;

    // ✅ Tüm kategorileri getir
    public List<Kategori> getAllKategoriler() {
        return kategoriRepository.findAll();
    }

    // ✅ ID ile kategori getir
    public Kategori getKategoriById(Integer id) {
        return kategoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
    }

    // ✅ İsimle (tur) kategori ara
    public Kategori getKategoriByTur(String tur) {
        return kategoriRepository.findByTur(tur)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
    }

    // ✅ Yeni kategori oluştur
    public Kategori createKategori(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    // ✅ Kategori güncelle
    public Kategori updateKategori(Integer id, Kategori yeni) {
        Kategori mevcut = getKategoriById(id);
        mevcut.setTur(yeni.getTur());
        return kategoriRepository.save(mevcut);
    }

    // ✅ Kategori sil
    public void deleteKategori(Integer id) {
        kategoriRepository.deleteById(id);
    }
}
