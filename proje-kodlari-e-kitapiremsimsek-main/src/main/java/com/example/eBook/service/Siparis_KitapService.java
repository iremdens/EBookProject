package com.example.eBook.service;

import com.example.eBook.entity.Siparis;
import com.example.eBook.entity.Siparis_Kitap;
import com.example.eBook.repository.Siparis_KitapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Siparis_KitapService {

    @Autowired
    private Siparis_KitapRepository siparis_KitapRepository;

    // ✅ 1. Siparişteki kitapları getir
    public List<Siparis_Kitap> getBySiparis(Siparis siparis) {
        return siparis_KitapRepository.findBySiparis(siparis);
    }

    // ✅ 2. Tek tek siparis_kitap kayıtlarına erişim (isteğe bağlı)
    public Siparis_Kitap getById(Integer id) {
        return siparis_KitapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş-Kitap ilişkisi bulunamadı."));
    }
}
