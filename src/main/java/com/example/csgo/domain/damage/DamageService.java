package com.example.csgo.domain.damage;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageService {
    private final DamageRepository damageRepository;

    public DamageService(DamageRepository damageRepository) {
        this.damageRepository = damageRepository;
    }

    public Damage createDamage(Damage damage){
        return damageRepository.save(damage);
    }

    public Damage getDamageById(Long id){
        return damageRepository.findById(id).orElse(null);
    }

    public List<Damage> getDamagesByMatchId(Long matchId) {
        return damageRepository.getDamagesByMatchId(matchId);
    }
}