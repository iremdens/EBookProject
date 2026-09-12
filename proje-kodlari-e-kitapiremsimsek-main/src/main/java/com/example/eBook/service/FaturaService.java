package com.example.eBook.service;

import com.example.eBook.entity.Fatura;
import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Users;
import com.example.eBook.repository.FaturaRepository;
import com.example.eBook.repository.SiparisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private SiparisRepository siparisRepository;

    // ✅ 1. Siparişe ait faturayı getir
    public Fatura getBySiparis(Siparis siparis) {
        return faturaRepository.findBySiparis(siparis)
                .orElseThrow(() -> new RuntimeException("Fatura bulunamadı."));
    }

    // ✅ 2. Kullanıcının tüm faturalarını getir
    public List<Fatura> getFaturalarByUser(Users user) {
        List<Siparis> siparisler = siparisRepository.findByUser(user);
        return siparisler.stream()
                .map(faturaRepository::findBySiparis)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    // ✅ 3. Tüm faturaları getir (admin paneli)
    public List<Fatura> getAll() {
        return faturaRepository.findAll();
    }

    public boolean hasUserPurchasedKitap(Integer userId, Integer kitapId) {
        System.out.println("🔎 [SATINALMA KONTROL] userId: " + userId + ", kitapId: " + kitapId);
        boolean sonuc = faturaRepository.hasUserPurchasedKitap(userId, kitapId);
        System.out.println("🔍 [SATINALMA KONTROL] sonuç: " + sonuc);
        return sonuc;
    }

}
