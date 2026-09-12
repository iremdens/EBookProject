package com.example.eBook.service;

import com.example.eBook.entity.Degerlendirme;
import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Users;
import com.example.eBook.repository.DegerlendirmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegerlendirmeService {

    @Autowired
    private DegerlendirmeRepository degerlendirmeRepository;

    // Tüm değerlendirmeleri getir (isteğe bağlı)
    public List<Degerlendirme> getAllDegerlendirmeler() {
        return degerlendirmeRepository.findAll();
    }

    // Kitaba göre tüm yorumları getir
    public List<Degerlendirme> getDegerlendirmelerByKitap(Kitap kitap) {
        return degerlendirmeRepository.findByKitap(kitap);
    }

    public List<Degerlendirme> getDegerlendirmelerByKitapId(Integer kitapId) {
        System.out.println("[Service] kitapId ile gelen sorgu: " + kitapId);

        List<Degerlendirme> result = degerlendirmeRepository.findByKitap_KitapId(kitapId);

        System.out.println("[Service] Repository'den dönen yorum sayısı: " + result.size());
        return result;
    }



    // Kullanıcıya göre tüm değerlendirmeleri getir
    public List<Degerlendirme> getDegerlendirmelerByUser(Users user) {
        return degerlendirmeRepository.findByUser(user);
    }

    // Kullanıcı aynı kitaba tekrar yorum yapmasın diye kontrol
    public Degerlendirme getDegerlendirmeByUserAndKitap(Users user, Kitap kitap) {
        return degerlendirmeRepository.findByUserAndKitap(user, kitap).orElse(null);
    }

    // Yeni değerlendirme oluştur
    public Degerlendirme createDegerlendirme(Degerlendirme degerlendirme) {
        // Bu kontrol controller tarafında da yapılabilir
        if (degerlendirmeRepository.findByUserAndKitap(degerlendirme.getUser(), degerlendirme.getKitap()).isPresent()) {
            throw new RuntimeException("Bu kullanıcı bu kitaba zaten yorum yaptı.");
        }
        return degerlendirmeRepository.save(degerlendirme);
    }

    // Güncelle
    public Degerlendirme updateDegerlendirme(Integer id, Degerlendirme details) {
        Degerlendirme d = degerlendirmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Değerlendirme bulunamadı"));

        d.setPuan(details.getPuan());
        d.setYorum(details.getYorum());
        return degerlendirmeRepository.save(d);
    }

    // Sil
    public void deleteDegerlendirme(Integer id) {
        degerlendirmeRepository.deleteById(id);
    }

    // ID ile getir (isteğe bağlı)
    public Degerlendirme getById(Integer id) {
        return degerlendirmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Değerlendirme bulunamadı"));
    }
}
