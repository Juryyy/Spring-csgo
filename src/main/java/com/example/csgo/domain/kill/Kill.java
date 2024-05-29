package com.example.csgo.domain.kill;

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
@Table(name = "kills")
public class Kill {

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
    private String weapon;

    @NotNull
    @NotEmpty
    private String weaponType;

    @NotNull
    @NotEmpty
    private int ctAlive;

    @NotNull
    @NotEmpty
    private int tAlive;

    @NotNull
    @NotEmpty
    private boolean isBombPlanted;

    public Kill(Match match, int round, String attackSide, String victimSide, String weapon, String weaponType, int ctAlive, int tAlive, boolean isBombPlanted) {
        this.match= match;
        this.round = round;
        this.attackSide = attackSide;
        this.victimSide = victimSide;
        this.weapon = weapon;
        this.weaponType = weaponType;
        this.ctAlive = ctAlive;
        this.tAlive = tAlive;
        this.isBombPlanted = isBombPlanted;
    }


}
