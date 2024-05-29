package com.example.csgo.domain.kill;

import com.example.csgo.domain.match.Match;
import com.example.csgo.domain.match.MatchService;
import com.example.csgo.utils.interfaces.weapon.Weapon;
import com.example.csgo.utils.interfaces.weapon.WeaponType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class KillRequest {

    @NotEmpty
    @Schema(description = "Match ID.", example = "1")
    private Long matchId;

    @NotEmpty
    @Schema(description = "Round number.", example = "1")
    private int round;

    @NotEmpty
    @Schema(description = "Attack side.", example = "CounterTerrorist",
            allowableValues = {"CounterTerrorist", "Terrorist"})
    private String attackSide;

    @NotEmpty
    @Schema(description = "Victim side.", example = "CounterTerrorist",
            allowableValues = {"CounterTerrorist", "Terrorist"})
    private String victimSide;

    @NotEmpty
    @Schema(description = "Weapon", example = "USP")
    private String weapon;

    //private String weaponType;

    @NotEmpty
    @Schema(description = "Number of counter terrorist alive after kill happened")
    private int ctAlive;

    @NotEmpty
    @Schema(description = "Number of terrorist alive after kill happened")
    private int tAlive;

    @NotEmpty
    @Schema(description = "If bomb was planted before kill happened")
    private boolean isBombPlanted;

    public WeaponType getWeaponType() {
        try {
            Weapon weaponEnum = Weapon.valueOf(this.weapon);
            return weaponEnum.getWeaponType();
        } catch (IllegalArgumentException e) {
            return WeaponType.Unknown;
        }
    }
    public void toKill(Kill kill, MatchService matchService){
        Match match = matchService.getMatchById(matchId);
        kill.setMatch(match);
        kill.setRound(round);
        kill.setAttackSide(attackSide);
        kill.setVictimSide(victimSide);
        kill.setWeapon(weapon);
        kill.setWeaponType(getWeaponType().toString());
        kill.setCtAlive(ctAlive);
        kill.setTAlive(tAlive);
        kill.setBombPlanted(isBombPlanted);
    }

}
