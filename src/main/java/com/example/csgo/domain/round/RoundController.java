package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rounds")
public class RoundController {
    @Autowired
    private RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping("/counts")
    public List<MatchRoundCount> getMatchRoundCounts() {
        return roundService.getMatchRoundCounts();
    }

}
