package com.example.eBook.service;

import com.example.eBook.entity.*;
import com.example.eBook.repository.FaturaRepository;
import com.example.eBook.repository.Siparis_KitapRepository;
import com.example.eBook.repository.SiparisRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SiparisService {

    @Autowired
    private SiparisRepository siparisRepository;

    @Autowired
    private Siparis_KitapRepository siparisKitapRepository;

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private SepetService sepetService;

    // ✅ Kullanıcının tüm siparişlerini getir
    public List<Siparis> getSiparisByUser(Users user) {
        return siparisRepository.findByUser(user);
    }

    // ✅ Tüm siparişleri getir (admin paneli)
    public List<Siparis> getAllSiparisler() {
        return siparisRepository.findAll();
    }


    // ✅ ID ile siparişi getir
    public Siparis getById(Integer id) {
        return siparisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));
    }

    // ✅ Siparişi iptal et (durum = "iptal edildi")
    public Siparis cancelSiparis(Integer id) {
        Siparis siparis = getById(id);
        siparis.setDurum("iptal edildi");
        return siparisRepository.save(siparis);
    }

    // ✅ Yeni sipariş oluştur
    @Transactional
    public Siparis createSiparis(Users user, String odemeYontemi) {
        List<Sepet> sepetListesi = sepetService.getSepetByUser(user);

        if (sepetListesi.isEmpty()) {
            throw new RuntimeException("Sepet boş, sipariş verilemez.");
        }

        // 1. Sipariş kaydı
        Siparis siparis = new Siparis();
        siparis.setUser(user);
        siparis.setDurum("hazırlanıyor");
        siparis.setTarih(LocalDateTime.now());
        siparis.setOdemeYontemi(odemeYontemi);
        Siparis kaydedilenSiparis = siparisRepository.save(siparis);

        // 2. Sipariş-Kitap ilişkileri
        BigDecimal toplamTutar = BigDecimal.ZERO;

        for (Sepet s : sepetListesi) {
            Siparis_Kitap sk = new Siparis_Kitap();
            sk.setSiparis(kaydedilenSiparis);
            sk.setKitap(s.getKitap());
            sk.setAdet(s.getAdet());
            sk.setBirimFiyat(s.getKitap().getFiyat());
            siparisKitapRepository.save(sk);

            toplamTutar = toplamTutar.add(s.getKitap().getFiyat().multiply(BigDecimal.valueOf(s.getAdet())));
        }

        // 3. Fatura oluştur
        Fatura fatura = new Fatura();
        fatura.setSiparis(kaydedilenSiparis);
        fatura.setTutar(toplamTutar);
        fatura.setTarih(LocalDateTime.now());
        faturaRepository.save(fatura);

        // 4. Sepeti temizle
        sepetService.clearSepetByUser(user);

        return kaydedilenSiparis;
    }
}
