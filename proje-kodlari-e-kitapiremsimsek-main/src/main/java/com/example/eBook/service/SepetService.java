package com.example.eBook.service;

import com.example.eBook.entity.Kitap;
import com.example.eBook.entity.Sepet;
import com.example.eBook.entity.Users;
import com.example.eBook.repository.SepetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SepetService {

    @Autowired
    private SepetRepository sepetRepository;

    // Kullanıcının sepetini getir
    public List<Sepet> getSepetByUser(Users user) {
        return sepetRepository.findByUser(user);
    }

    // Sepete kitap ekle (varsa adet artırılır)
    public Sepet addToSepet(Users user, Kitap kitap, int adet) {
        Sepet mevcut = sepetRepository.findByUserAndKitap(user, kitap).orElse(null);

        if (mevcut != null) {
            mevcut.setAdet(mevcut.getAdet() + adet);
            return sepetRepository.save(mevcut);
        }

        Sepet yeni = new Sepet();
        yeni.setUser(user);
        yeni.setKitap(kitap);
        yeni.setAdet(adet);
        return sepetRepository.save(yeni);
    }

    // Sepetten kitap çıkar
    public void removeFromSepet(Integer sepetId) {
        sepetRepository.deleteById(sepetId);
    }

    // Sepetteki ürünün adedini değiştir
    public Sepet updateAdet(Integer sepetId, int yeniAdet) {
        Sepet sepet = sepetRepository.findById(sepetId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

        if (yeniAdet <= 0) {
            sepetRepository.deleteById(sepetId);
            return null;
        }

        sepet.setAdet(yeniAdet);
        return sepetRepository.save(sepet);
    }

    // Sipariş verildikten sonra sepeti temizle
    @Transactional
    public void clearSepetByUser(Users user) {
        sepetRepository.deleteByUser(user);
    }
}
