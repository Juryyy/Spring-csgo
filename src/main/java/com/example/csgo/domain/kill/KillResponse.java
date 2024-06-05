package com.example.csgo.domain.kill;

import com.example.csgo.domain.round.Round;

public class KillResponse {

    private Long id;
    private Long matchId;
    private String attackSide;
    private String victimSide;
    private String weapon;
    private String weaponType;
    private int ctAlive;
    private int tAlive;
    private boolean isBombPlanted;

    public KillResponse(Kill kill) {
        this.id = kill.getId();
        this.matchId = kill.getMatch().getId();
        this.attackSide = kill.getAttackSide();
        this.victimSide = kill.getVictimSide();
        this.weapon = kill.getWeapon();
        this.weaponType = kill.getWeaponType();
        this.ctAlive = kill.getCtAlive();
        this.tAlive = kill.getTAlive();
        this.isBombPlanted = kill.isBombPlanted();


    }
}
