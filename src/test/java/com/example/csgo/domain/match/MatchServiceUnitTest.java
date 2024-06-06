package com.example.csgo.domain.match;
import com.example.csgo.utils.interfaces.map.MapCount;
import com.example.csgo.utils.interfaces.map.MapEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class MatchServiceUnitTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    @Test
    public void testGetMapCounts() {
        // given
        List<MapCount> mockCounts = new ArrayList<>();
        mockCounts.add(new ConcreteMapCount("map1", 5L));
        mockCounts.add(new ConcreteMapCount("map2", 3L));

        when(matchRepository.countMatchesByMap()).thenReturn(mockCounts);

        // when
        Map<String, Long> result = matchService.getMapCounts();

        // then
        assertEquals(2, result.size());
        assertEquals(5L, result.get("map1"));
        assertEquals(3L, result.get("map2"));
    }

    @Test
    public void testGetMatchesWithHighestRounds() {
        // given
        Map<String, Object> mockMatch = new HashMap<>();
        mockMatch.put("match_id", 1);
        mockMatch.put("rounds", 10);

        when(matchRepository.findMatchWithHighestRoundsForMap(anyString())).thenReturn(mockMatch);

        // when
        List<Map<String, Object>> result = matchService.getMatchesWithHighestRounds();

        // then
        assertEquals(MapEnum.values().length, result.size());
        for (Map<String, Object> match : result) {
            assertEquals(mockMatch, match);
        }
    }
}
