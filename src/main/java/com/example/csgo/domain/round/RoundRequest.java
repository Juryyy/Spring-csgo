package com.example.csgo.domain.round;

import com.example.csgo.domain.match.Match;
import com.example.csgo.utils.exceptions.MapNotInEnumException;
import com.example.csgo.utils.exceptions.SideNotInEnumException;
import com.example.csgo.utils.interfaces.map.MapEnum;
import com.example.csgo.utils.interfaces.round.SideEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class RoundRequest {

    @NotNull
    @Schema(description = "Match ID, in which this round happened.", example = "1")
    private Long matchId;

    @NotNull
    @Min(1)
    @Schema(description = "Round number.", example = "1")
    private int round;

    @NotEmpty
    @Schema(description = "Winner side.", example = "Terrorist",
            allowableValues = {"CounterTerrorist", "Terrorist"})
    private String winnerSide;

    @NotNull
    @Min(0)
    @Schema(description = "CounterTerrorist equipment value that round", example="10250")
    private int ctEqVal;

    @NotNull
    @Min(0)
    @Schema(description = "Terrorist equipment value that round", example="10250")
    private int tEqVal;

    public void toRound(Round roundX, Match match) {
        try {
            SideEnum.valueOf(winnerSide);
        } catch (IllegalArgumentException e) {
            throw new SideNotInEnumException("Side not in SideEnum");
        }
            roundX.setMatch(match);
            roundX.setRound(round);
            roundX.setWinnerSide(winnerSide);
            roundX.setCtEqVal(ctEqVal);
            roundX.setTEqVal(tEqVal);
        }
}