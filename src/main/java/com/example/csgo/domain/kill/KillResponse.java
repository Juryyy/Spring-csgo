package com.example.csgo.domain.kill;

import com.example.csgo.domain.round.Round;

public class KillResponse {

    //        <column name="round" type="int" />
    //        <column name="attack_side" type="varchar(255)" />
    //        <column name="victim_side" type="varchar(255)" />
    //        <column name="weapon" type="varchar(255)" />
    //        <column name="weapon_type" type="varchar(255)" />
    //        <column name="ct_alive" type="int" />
    //        <column name="t_alive" type="int" />
    //        <column name="is_bomb_planted" type="boolean" />

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
