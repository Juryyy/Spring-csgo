package com.example.csgo.domain.match;

import com.example.csgo.domain.kill.Kill;
import com.example.csgo.domain.kill.KillService;
import com.example.csgo.domain.round.Round;
import com.example.csgo.domain.round.RoundService;
import com.example.csgo.utils.exceptions.MapNotInEnumException;
import com.example.csgo.utils.exceptions.NotFoundException;
import com.example.csgo.utils.response.ArrayResponse;
import com.example.csgo.utils.response.ObjectResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matches")
@Validated
@Tag(
        name="Matches",
        description="Management of matches."
)
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
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<MatchResponse> createMatch(@Valid @RequestBody MatchRequest matchRequest) {
        try {
            Match match = new Match();
            matchRequest.toMatch(match);
            match = matchService.createMatch(match);
            return ObjectResponse.of(match, MatchResponse::new);
        } catch (MapNotInEnumException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Update a match",
            description = "Update a match in the system."
    )
    @ApiResponse(responseCode = "200", description = "Match updated")
    @ApiResponse(responseCode = "404", description = "Match not found")
    @ResponseStatus(HttpStatus.OK)
    public ObjectResponse<MatchResponse> updateMatch(@PathVariable Long id, @RequestBody @Valid MatchRequest matchRequest) {
        try {
            Match match = matchService.getMatchById(id);
            if (match == null) {
                throw new NotFoundException();
            }
            matchRequest.toMatch(match);
            Match updatedMatch = matchService.updateMatch(id, match);
            return ObjectResponse.of(updatedMatch, MatchResponse::new);
        } catch(MapNotInEnumException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Delete a match",
            description = "Delete a match in the system."
    )
    @ApiResponse(responseCode = "204", description = "Match deleted")
    @ApiResponse(responseCode = "404", description = "Match not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
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
    public ArrayResponse<Map<String, Object>> getTopMatches() {
        List<Map<String, Object>> mapCounts = matchService.getMatchesWithHighestRounds();
        return ArrayResponse.of(mapCounts, m -> m);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Get a match by id, containing all rounds and kills",
            description = "Get a match in the system by id."
    )
    @ApiResponse(responseCode = "200", description = "Match found")
    @ApiResponse(responseCode = "404", description = "Match not found")
    @ResponseStatus(HttpStatus.OK)
    public ObjectResponse<MatchResponse> getMatchById(@PathVariable Long id) {
        Match match = matchService.getMatchById(id);
        List<Round> rounds = roundService.getRoundsByMatchId(match.getId());
        List<Kill> kills = killService.getKillsByMatchId(match.getId());

        return ObjectResponse.of(match, m -> new MatchResponse(m, rounds, kills));
    }
}