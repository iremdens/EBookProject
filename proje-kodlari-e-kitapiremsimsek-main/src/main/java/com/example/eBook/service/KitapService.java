package com.example.eBook.service;

import com.example.eBook.entity.Kategori;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Users;
import com.example.eBook.repository.KitapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitapService {

    @Autowired
    private KitapRepository kitapRepository;

    // Tüm onaylı kitapları getir (kullanıcılara gösterilir)
    public List<Kitap> getOnayliKitaplar() {
        return kitapRepository.findByOnayliTrue();
    }

    // Onay bekleyen kitapları getir (admin için)
    public List<Kitap> getOnayBekleyenKitaplar() {
        return kitapRepository.findByOnayliFalse();
    }

    // Kitabı başlığa göre ara (kullanıcı araması)
    public List<Kitap> searchByBaslik(String baslik) {
        return kitapRepository.findByBaslikContainingIgnoreCase(baslik);
    }

    // Kategoriye göre kitapları getir
    public List<Kitap> getByKategori(Kategori kategori) {
        return kitapRepository.findByKategori(kategori);
    }

    // Kullanıcının eklediği kitapları getir
    public List<Kitap> getByEkleyen(Users user) {
        return kitapRepository.findByEkleyen(user);
    }

    // Yeni kitap ekle (onay bekleyen)
    public Kitap createKitap(Kitap kitap) {
        if (kitap.getOnayli() == null) {
            kitap.setOnayli(false); // eğer null ise varsayılan olarak false
        }
        // her zaman onaysız başlar
        return kitapRepository.save(kitap);
    }

    // Kitap güncelle
    public Kitap updateKitap(Integer id, Kitap kitapDetails) {
        Kitap kitap = kitapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));

        kitap.setBaslik(kitapDetails.getBaslik());
        kitap.setYazar(kitapDetails.getYazar());
        kitap.setFiyat(kitapDetails.getFiyat());
        kitap.setKategori(kitapDetails.getKategori());
        kitap.setAciklama(kitapDetails.getAciklama());
        kitap.setOnayli(kitapDetails.getOnayli()); // admin güncelleme senaryosunda kullanılabilir

        return kitapRepository.save(kitap);
    }

    // Kitap sil
    public void deleteKitap(Integer id) {
        kitapRepository.deleteById(id);
    }

    // ID'ye göre kitap getir
    public Kitap getById(Integer id) {
        return kitapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
    }

    public Kitap getByBaslik(String baslik) {
        return kitapRepository.findByBaslik(baslik)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
    }
    public List<Kitap> searchByBaslikOrYazar(String query) {
        return kitapRepository.findByBaslikContainingIgnoreCaseOrYazarContainingIgnoreCase(query, query);
    }
    public List<Kitap> getOnayliKitaplarByUser(Users user) {
        return kitapRepository.findByEkleyenAndOnayliTrue(user);
    }


}
