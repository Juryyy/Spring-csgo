package com.example.csgo.domain.damage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageResponse {
    private Long id;
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

    public DamageResponse(Damage damage) {
        this.id = damage.getId();
        this.matchId = damage.getMatch().getId();
        this.round = damage.getRound();
        this.attackSide = damage.getAttackSide();
        this.victimSide = damage.getVictimSide();
        this.hpDmg = damage.getHpDmg();
        this.armDmg = damage.getArmDmg();
        this.isBombPlanted = damage.isBombPlanted();
        this.hitbox = damage.getHitbox();
        this.weapon = damage.getWeapon();
        this.weaponType = damage.getWeaponType();
    }
}