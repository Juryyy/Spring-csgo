package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rounds")
public class RoundController {
    @Autowired
    private RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping("/counts")
    @Operation(
            summary = "Get number of rounds for each match",
            description = "Get the number of rounds played for each match."
    )
    @ApiResponse(responseCode = "200", description = "List of matches and their round counts")
    public List<MatchRoundCount> getMatchRoundCounts() {
        return roundService.getMatchRoundCounts();
    }

    @GetMapping(value = "/maps", produces = "application/json")
    @Operation(
            summary = "Get number of rounds for each map",
            description = "Get the number of rounds played for each map."
    )
    @ApiResponse(responseCode = "200", description = "List of maps and their round counts")
    public List<Map<String, Object>> getAllMaps() {
        return roundService.getRoundsCountForAllMaps();
    }


}
