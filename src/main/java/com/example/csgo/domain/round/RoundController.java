package com.example.csgo.domain.round;

import com.example.csgo.domain.match.Match;
import com.example.csgo.domain.match.MatchService;
import com.example.csgo.utils.exceptions.NotFoundException;
import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import com.example.csgo.utils.response.ArrayResponse;
import com.example.csgo.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rounds")
public class RoundController {
    @Autowired
    private RoundService roundService;

    @Autowired
    private MatchService matchService;

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

    @GetMapping(value = "/winrate/{map}", produces = "application/json")
    @Operation(
            summary = "Get win rate for each team on a map",
            description = "Get the win rate for each team on a specific map."
    )
    @ApiResponse(responseCode = "200", description = "Win rate for each team on a map")
    @ApiResponse(responseCode = "404", description = "Map not found")
    public ObjectResponse<Map<String, Double>> getWinRateForTeamsOnMap(@PathVariable String map) {
        Map<String, Double> winRates = roundService.getWinRateForTeamsOnMap(map);
        return ObjectResponse.of(winRates, winRateMap -> winRateMap);
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(
            summary = "Create a round",
            description = "Create a new round."
    )
    @ApiResponse(responseCode = "201", description = "Round created")
    @ApiResponse(responseCode = "400", description = "Invalid round data")
    public ObjectResponse<Round> createRound(@RequestBody RoundRequest roundRequest) {
        Round round = new Round();
        Match match = matchService.getMatchById(roundRequest.getMatchId());
        roundRequest.toRound(round, match);

        roundService.createRound(round);

        return ObjectResponse.of(round, createdRoundResponse -> createdRoundResponse);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Update a round",
            description = "Update an existing round."
    )
    @ApiResponse(responseCode = "202", description = "Round updated")
    @ApiResponse(responseCode = "400", description = "Invalid round data")
    @ApiResponse(responseCode = "404", description = "Round not found")
    public ObjectResponse<Round> updateRound(@PathVariable Long id, @RequestBody RoundRequest roundRequest) {
        Round round = roundService.getRoundById(id).orElseThrow(NotFoundException::new);

        roundRequest.toRound(round, matchService.getMatchById(roundRequest.getMatchId()));

        roundService.updateRound(id, round);

        return ObjectResponse.of(round, updatedRoundResponse -> updatedRoundResponse);
    }

    //@DeleteMapping(value = "/{id}", produces = "application/json")
    //@Operation(
    //        summary = "Delete a round",
    //        description = "Delete an existing round."
    //)
    //@ApiResponse(responseCode = "204", description = "Round deleted")
    //@ApiResponse(responseCode = "404", description = "Round not found")
    //public void deleteRound(@PathVariable Long id) {
    //    roundService.deleteRound(id);
    //}





}
