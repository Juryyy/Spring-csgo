package com.example.csgo.domain.kill;


import com.example.csgo.utils.response.ObjectResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/kills")
@Tag(
        name = "Kills",
        description = "Management of kills."
)
public class KillController {

    @Autowired
    private KillService killService;


    @GetMapping(value = "/avg", produces = "application/json")
    @Operation(
            summary = "Get average number of kills for each map",
            description = "Get the average number of kills for all matches for each map."
    )
    @ApiResponse(responseCode = "200", description = "Average number of kills")
    public ObjectResponse<Map<String, BigDecimal>> getAverageKillCount() {
        Map<String, BigDecimal> response = killService.getAverageKillCount();
        return ObjectResponse.of(response, x -> x);
    }

    @GetMapping(value = "/avg/{mapName}", produces = "application/json")
    @Operation(
            summary = "Get average number of kills on specific map",
            description = "Get the average number of kills for all matches on a specific map."
    )
    @ApiResponse(responseCode = "200", description = "Average number of kills on specific map")
    public ObjectResponse<Map<String, BigDecimal>> getAverageKillCountOnMap(@PathVariable String mapName) {
        BigDecimal avgKills = killService.getAverageKillCountOnMap(mapName);
        Map<String, BigDecimal> response = Map.of(mapName, avgKills);
        return ObjectResponse.of(response, x -> x);
    }

    @GetMapping(value = "/weapons", produces = "application/json")
    @Operation(
            summary = "Get number of kills for each weapon",
            description = "Get the number of kills for all matches for each weapon."
    )
    @ApiResponse(responseCode = "200", description = "Number of kills for each weapon")
    public ObjectResponse<Map<String, Long>> getKillCountForWeapons() {
        Map<String, Long> response = killService.getKillCountForWeapons();
        return ObjectResponse.of(response, x -> x);
    }

    @GetMapping(value = "/avg/sides", produces = "application/json")
    @Operation(
            summary = "Get average number of kills for each side on each map",
            description = "Get the average number of kills for all matches for each side on each map."
    )
    @ApiResponse(responseCode = "200", description = "Average number of kills for each side on each map")
    public ObjectResponse<Map<String, Map<String, BigDecimal>>> getAverageKillCountForSides() {
        Map<String, Map<String, BigDecimal>> response = killService.getAverageKillCountForSides();
        return ObjectResponse.of(response, x -> x);
    }

}
