package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {
    @Autowired
    private RoundRepository roundRepository;

    public List<MatchRoundCount> getMatchRoundCounts() {
        return roundRepository.countRoundsByMatch();
    }

}