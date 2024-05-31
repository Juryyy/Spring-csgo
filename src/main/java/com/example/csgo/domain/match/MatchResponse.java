package com.example.csgo.domain.match;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchResponse {

    private Long id;
    private Long matchNumber;
    private String map;

    public MatchResponse(Match match) {
        this.id = match.getId();
        this.matchNumber = match.getMatchNumber();
        this.map = match.getMap();
    }
}