package com.example.csgo.domain.damage;

import com.example.csgo.domain.match.Match;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "damage")
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @NotNull
    @NotEmpty
    private int round;

    @NotNull
    @NotEmpty
    private String attackSide;

    @NotNull
    @NotEmpty
    private String victimSide;

    @NotNull
    @NotEmpty
    private int hpDmg;

    @NotNull
    @NotEmpty
    private int armDmg;

    @NotNull
    @NotEmpty
    private boolean isBombPlanted;

    @NotNull
    @NotEmpty
    private String hitbox;

    @NotNull
    @NotEmpty
    private String weapon;

    @NotNull
    @NotEmpty
    private String weaponType;

    public Damage(Match match, int round, String attackSide, String victimSide, int hpDmg, int armDmg, boolean isBombPlanted, String hitbox, String weapon, String weaponType) {
        this.match = match;
        this.round = round;
        this.attackSide = attackSide;
        this.victimSide = victimSide;
        this.hpDmg = hpDmg;
        this.armDmg = armDmg;
        this.isBombPlanted = isBombPlanted;
        this.hitbox = hitbox;
        this.weapon = weapon;
        this.weaponType = weaponType;
    }
}