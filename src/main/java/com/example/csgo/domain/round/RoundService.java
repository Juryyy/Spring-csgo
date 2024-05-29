package com.example.csgo.domain.round;

import com.example.csgo.domain.match.Match;
import com.example.csgo.domain.match.MatchRepository;
import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundService {
    @Autowired
    private RoundRepository roundRepository;

    public List<MatchRoundCount> getMatchRoundCounts() {
        return roundRepository.countRoundsByMatch();
    }

    @Autowired
    private MatchRepository matchRepository;

    public Round convertToRound(RoundRequest roundRequest) {
        Round round = new Round();
        Optional<Match> matchOptional = matchRepository.findById(roundRequest.getMatchId());
        if (matchOptional.isPresent()) {
            round.setMatch(matchOptional.get());
            round.setRound(roundRequest.getRound());
            round.setWinnerSide(roundRequest.getWinnerSide());
            round.setCtEqVal(roundRequest.getCtEqVal());
            round.setTEqVal(roundRequest.getTEqVal());
        } else {
            // Handle the case where no Match with the given matchId exists.
            // This could involve throwing an exception, returning an error response, etc.
        }
        return round;
    }

}