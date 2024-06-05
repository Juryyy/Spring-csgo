package com.example.csgo.domain.damage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageRequest {
    private Long matchId;
    private int round;
    private String attackSide;
    private String victimSide;
    private int hpDmg;
    private int armDmg;
    private boolean isBombPlanted;
    private String hitbox;
    private String weapon;
    private String weaponType;
}