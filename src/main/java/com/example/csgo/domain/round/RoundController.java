package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import com.example.csgo.utils.response.ArrayResponse;
import com.example.csgo.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ArrayResponse<MatchRoundCount> getMatchRoundCounts() {
        List<MatchRoundCount> matchRoundCounts = roundService.getMatchRoundCounts();
        return ArrayResponse.of(matchRoundCounts, matchRoundCount -> matchRoundCount);
    }

    @GetMapping(value = "/maps", produces = "application/json")
    @Operation(
            summary = "Get number of rounds for each map",
            description = "Get the number of rounds played for each map."
    )
    @ApiResponse(responseCode = "200", description = "List of maps and their round counts")
    public ArrayResponse<Map<String, Object>> getAllMaps() {
        List<Map<String, Object>> roundCounts = roundService.getRoundsCountForAllMaps();
        return ArrayResponse.of(roundCounts, roundCount -> roundCount);
    }

    @GetMapping(value = "/average", produces = "application/json")
    @Operation(
            summary = "Get average number of rounds",
            description = "Get the average number of rounds played for all matches."
    )
    @ApiResponse(responseCode = "200", description = "Average number of rounds")
    public double getAverageRoundCount() {
        return roundService.getAverageRoundCount();
    }

    @GetMapping(value = "/average/{map}", produces = "application/json")
    @Operation(
            summary = "Get average number of rounds for a map",
            description = "Get the average number of rounds played for a specific map."
    )
    @ApiResponse(responseCode = "200", description = "Average number of rounds for a map")
    @ApiResponse(responseCode = "404", description = "Map not found")
    public ObjectResponse<Map<String, Double>> getAverageRoundCountForMap(@PathVariable String map) {
        double averageRoundCount = roundService.getAverageRoundCountForMap(map);
        Map<String, Double> response = Map.of(map, averageRoundCount);
        return ObjectResponse.of(response, responseMap -> responseMap);



    }



}
