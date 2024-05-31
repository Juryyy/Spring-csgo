package com.example.csgo.domain.match;

import com.example.csgo.domain.kill.Kill;
import com.example.csgo.domain.kill.KillService;
import com.example.csgo.domain.round.Round;
import com.example.csgo.domain.round.RoundService;
import com.example.csgo.utils.response.ArrayResponse;
import com.example.csgo.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private KillService killService;

    @GetMapping(value = "", produces = "application/json")
    @Operation(
            summary = "Get all matches",
            description = "Get all matches in the system."
    )
    @ApiResponse(responseCode = "200", description = "List of matches")
    @Cacheable("allMatches")
    @ResponseStatus(HttpStatus.OK)
    public ArrayResponse<MatchResponse> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return ArrayResponse.of(matches, MatchResponse::new);
    }

    @GetMapping(value = "/map/{map_name}", produces = "application/json")
    @Operation(
            summary = "Get matches by map",
            description = "Get all matches in the system by map."
    )
    @ApiResponse(responseCode = "200", description = "List of matches")
    @Cacheable(value = "matchesByMap", key = "#map_name")
    @ResponseStatus(HttpStatus.OK)
    public ArrayResponse<MatchResponse> getMatchesByMap(@PathVariable String map_name) {
        List<Match> matches = matchService.getMatchesByMap(map_name);
        return ArrayResponse.of(matches, MatchResponse::new);
    }

    @PostMapping(value = "", produces = "application/json")
    @Operation(
            summary = "Create a match",
            description = "Create a match in the system."
    )
    @ApiResponse(responseCode = "201", description = "Match created")
    @Caching(evict = {
            @CacheEvict(value = "allMatches", allEntries = true),
            @CacheEvict(value = "matchesByMap", key = "#match.map")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ObjectResponse<MatchResponse> createMatch(@RequestBody @Valid MatchRequest match) {
        Match newMatch = new Match();
        match.toMatch(newMatch);
        matchService.createMatch(newMatch);
        return ObjectResponse.of(newMatch, MatchResponse::new);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Delete a match",
            description = "Delete a match in the system."
    )
    @ApiResponse(responseCode = "204", description = "Match deleted")
    @ApiResponse(responseCode = "404", description = "Match not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @Caching(evict = {
            @CacheEvict(value = "allMatches", allEntries = true),
            @CacheEvict(value = "matchesByMap", allEntries = true)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
    }

    @GetMapping(value = "/maps", produces = "application/json")
    @Operation(
            summary = "Get counts of each map",
            description = "Get counts of each map in the system."
    )
    @ApiResponse(responseCode = "200", description = "List of counts")
    @Cacheable("mapCounts")
    @ResponseStatus(HttpStatus.OK)
    public ObjectResponse<Map<String, Integer>> getMapCounts() {
        Map<String, Long> mapCounts = matchService.getMapCounts();
        return ObjectResponse.of(mapCounts, longMap -> longMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Math.toIntExact(e.getValue()))));
    }

    @GetMapping(value = "/top-map-matches", produces = "application/json")
    @Operation(
            summary = "Get matches with the highest rounds for each map",
            description = "Get matches with the highest rounds for each map."
    )
    @ApiResponse(responseCode = "200", description = "List of maps with matches with the highest rounds")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getTopMatches() {
        return matchService.getMatchesWithHighestRounds();
    }


    //!Not happy with this method, need update
    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Get a match by id, containing all rounds and kills",
            description = "Get a match in the system by id."
    )
    @ApiResponse(responseCode = "200", description = "Match found")
    @ApiResponse(responseCode = "404", description = "Match not found")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getMatchById(@PathVariable Long id) {
        Match match = matchService.getMatchById(id);
        List<Round> rounds = roundService.getRoundsByMatchId(match.getId());
        List<Kill> kills = killService.getKillsByMatchId(match.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("match", match);
        response.put("rounds", rounds);
        response.put("kills", kills);

        return response;
    }
}