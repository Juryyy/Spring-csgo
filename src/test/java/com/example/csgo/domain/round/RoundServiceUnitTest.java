package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.map.MapEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoundServiceUnitTest {

    @InjectMocks
    private RoundService roundService;

    @Mock
    private RoundRepository roundRepository;

    @Test
    public void testGetWinRateForTeamsOnMap() {
        when(roundRepository.countRoundsForMap("de_dust2")).thenReturn(100L);
        when(roundRepository.countRoundsWonByCT("de_dust2")).thenReturn(66L);
        when(roundRepository.countRoundsWonByT("de_dust2")).thenReturn(34L);

        Map<String, Double> result = roundService.getWinRateForTeamsOnMap("de_dust2");

        assertEquals(66.0, result.get("CounterTerrorist"));
        assertEquals(34.0, result.get("Terrorist"));
    }

    @Test
    public void testGetRoundsCountForAllMaps() {
        for (MapEnum mapEnum : MapEnum.values()) {
            when(roundRepository.countRoundsForMap(mapEnum.getMap())).thenReturn(10L);
        }

        List<Map<String, Object>> result = roundService.getRoundsCountForAllMaps();

        assertEquals(MapEnum.values().length, result.size());
        for (Map<String, Object> mapRoundsCount : result) {
            assertEquals(10L, mapRoundsCount.get("roundsCount"));
        }
    }
}