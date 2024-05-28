package com.example.csgo.domain.round;

import lombok.Data;

@Data
public class RoundResponse {

    private Long id;
    private Long matchId;
    private int round;
    private String winnerSide;
    private int ctEqVal;
    private int tEqVal;

    public RoundResponse(Round round) {
        this.id = round.getId();
        this.matchId = round.getMatch().getId();
        this.round = round.getRound();
        this.winnerSide = round.getWinnerSide();
        this.ctEqVal = round.getCtEqVal();
        this.tEqVal = round.getTEqVal();
    }
}