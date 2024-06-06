package com.example.csgo.domain.kill;

import com.example.csgo.utils.interfaces.map.MapEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KillServiceUnitTest {

    @InjectMocks
    private KillService killService;

    @Mock
    private KillRepository killRepository;

    @Test
    public void testGetAverageKillCount() {
        // given
        List<String> allMaps = Arrays.stream(MapEnum.values())
                .map(MapEnum::getMap)
                .collect(Collectors.toList());

        List<Object[]> mockKillCounts = new ArrayList<>();
        for (String map : allMaps) {
            mockKillCounts.add(new Object[]{map, "match1", 10L});
            mockKillCounts.add(new Object[]{map, "match2", 20L});
        }

        when(killRepository.getKillsPerMatchForMaps(allMaps)).thenReturn(mockKillCounts);

        // when
        Map<String, BigDecimal> result = killService.getAverageKillCount();

        // then
        for (String map : allMaps) {
            assertEquals(BigDecimal.valueOf(15.00).setScale(2, RoundingMode.HALF_UP), result.get(map));
        }
    }

    @Test
    public void testGetAverageKillCountOnMap() {
        // given
        String mapName = "map1";
        BigDecimal mockAverage = BigDecimal.valueOf(15.5);

        when(killRepository.getAverageKillCountOnMap(mapName)).thenReturn(mockAverage);

        // when
        BigDecimal result = killService.getAverageKillCountOnMap(mapName);

        // then
        assertEquals(mockAverage, result);
    }

    @Test
    public void testGetKillCountForWeapons() {
        // given
        List<Object[]> mockKillCounts = Arrays.asList(
                new Object[]{"weapon1", 10L},
                new Object[]{"weapon2", 20L}
        );

        when(killRepository.getKillCountForWeapons()).thenReturn(mockKillCounts);

        // when
        Map<String, Long> result = killService.getKillCountForWeapons();

        // then
        assertEquals(2, result.size());
        assertEquals(10L, result.get("weapon1"));
        assertEquals(20L, result.get("weapon2"));
    }

    @Test
    public void testGetAverageKillCountForSides() {
        // given
        List<String> allMaps = Arrays.stream(MapEnum.values())
                .map(MapEnum::getMap)
                .toList();

        List<Long> mockKillCountsCT = Arrays.asList(10L, 20L);
        List<Long> mockKillCountsT = Arrays.asList(15L, 25L);

        for (String map : allMaps) {
            when(killRepository.getKillCountsForSideOnMap(map, "CounterTerrorist")).thenReturn(mockKillCountsCT);
            when(killRepository.getKillCountsForSideOnMap(map, "Terrorist")).thenReturn(mockKillCountsT);
        }

        // when
        Map<String, Map<String, BigDecimal>> result = killService.getAverageKillCountForSides();

        // then
        for (String map : allMaps) {
            assertEquals(BigDecimal.valueOf(15.0), result.get(map).get("CounterTerrorist"));
            assertEquals(BigDecimal.valueOf(20.0), result.get(map).get("Terrorist"));
        }
    }
}
