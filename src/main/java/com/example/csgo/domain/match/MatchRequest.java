package com.example.csgo.domain.match;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MatchRequest {

    @NotEmpty
    @Schema(description = "Match number.", example = "1379")
    private Long matchNumber;

    @NotEmpty
    @Schema(description = "Map name.", example = "de_dust2",
            allowableValues = {"de_dust2", "de_inferno", "de_mirage", "de_overpass", "de_train", "de_nuke", "de_vertigo", "de_ancient", "de_anubis", "de_cache", "de_cbble"})
    private String map;

    public void toMatch(Match match) {
        match.setMatchNumber(matchNumber);
        match.setMap(map);
    }

}
