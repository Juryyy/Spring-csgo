package com.example.csgo.domain.round;

import com.example.csgo.domain.match.Match;
import com.example.csgo.domain.match.MatchRepository;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoundRequest {

    @NotEmpty
    @Schema(description = "Match ID, in which this round happened.", example = "1")
    private Long matchId;

    @NotEmpty
    @Schema(description = "Round number.", example = "1")
    private int round;

    @NotEmpty
    @Schema(description = "Winner side.", example = "Terrorist",
            allowableValues = {"CounterTerrorist", "Terrorist"})
    private String winnerSide;

    @NotEmpty
    @Schema(description = "CounterTerrorist equipment value that round", example="10250")
    private int ctEqVal;

    @NotEmpty
    @Schema(description = "Terrorist equipment value that round", example="10250")
    private int tEqVal;

    public void toRound(Round roundX, Match match) {
            match.setId(matchId);
            roundX.setMatch(match);
            roundX.setRound(round);
            roundX.setWinnerSide(winnerSide);
            roundX.setCtEqVal(ctEqVal);
            roundX.setTEqVal(tEqVal);
        }
}