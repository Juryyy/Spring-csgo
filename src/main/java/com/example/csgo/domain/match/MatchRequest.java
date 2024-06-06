package com.example.csgo.domain.match;

import com.example.csgo.utils.exceptions.MapNotInEnumException;
import com.example.csgo.utils.interfaces.map.MapEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
public class MatchRequest {

    @NotNull
    @Positive
    @Min(1)
    @Schema(description = "Match number.", example = "1379")
    Long matchNumber;

    @NotEmpty
    @Schema(description = "Map name.", example = "de_dust2",
            allowableValues = {"de_dust2", "de_inferno", "de_mirage", "de_overpass", "de_train", "de_nuke", "de_vertigo", "de_ancient", "de_anubis", "de_cache", "de_cbble"})
    String map;

    public void toMatch(Match match) {
        try {
            MapEnum.valueOf(map.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new MapNotInEnumException("Map is not in MapEnum");
        }

        match.setMatchNumber(matchNumber);
        match.setMap(map);
    }

    public MatchRequest(){

    }

}
