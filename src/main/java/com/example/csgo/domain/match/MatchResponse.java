package com.example.csgo.domain.match;

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