package com.example.csgo.domain.match;

import com.example.csgo.domain.kill.Kill;
import com.example.csgo.domain.round.Round;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MatchResponse {

    private Long id;
    private Long matchNumber;
    private String map;

    private List<Round> rounds;
    private List<Kill> kills;

    public MatchResponse(Match match) {
        this.id = match.getId();
        this.matchNumber = match.getMatchNumber();
        this.map = match.getMap();
    }

    public MatchResponse(Match match, List<Round> rounds, List<Kill> kills) {
        this(match); // Call the other constructor to set id, matchNumber, and map
        this.rounds = rounds;
        this.kills = kills;
    }
}