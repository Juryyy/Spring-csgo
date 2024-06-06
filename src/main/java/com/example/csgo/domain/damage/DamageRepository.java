package com.example.csgo.domain.damage;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DamageRepository extends CrudRepository<Damage, Long> {
    List<Damage> getDamagesByMatchId(Long matchId);

    void deleteAllByMatch_Id(Long matchId);
}