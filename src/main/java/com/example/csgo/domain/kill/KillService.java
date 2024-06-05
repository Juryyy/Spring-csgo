package com.example.csgo.domain.kill;

import com.example.csgo.utils.interfaces.map.MapEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KillService {
    private final KillRepository killRepository;

    public KillService(KillRepository killRepository) {
        this.killRepository = killRepository;
    }

    public Kill createKill(Kill kill){
        return killRepository.save(kill);
    }

    public Kill getKillById(Long id){
        return killRepository.findById(id).orElse(null);
    }

    public List<Kill> getKillsByMatchId(Long matchId) {
        return killRepository.getKillsByMatchId(matchId);
    }

    public Map<String, BigDecimal> getAverageKillCount() {
        List<String> mapNames = Arrays.stream(MapEnum.values())
                .map(MapEnum::getMap)
                .collect(Collectors.toList());
        List<Object[]> killCountsPerMatch = killRepository.getKillsPerMatchForMaps(mapNames);

        Map<String, List<Long>> killsPerMatchForMaps = killCountsPerMatch.stream()
                .collect(Collectors.groupingBy(
                        killCount -> (String) killCount[0],
                        Collectors.mapping(killCount -> (Long) killCount[2], Collectors.toList())
                ));

        return killsPerMatchForMaps.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> BigDecimal.valueOf(e.getValue().stream().mapToLong(val -> val).average().orElse(0.0))
                                .setScale(2, RoundingMode.HALF_UP)
                ));
    }

    public BigDecimal getAverageKillCountOnMap(String mapName){
        return killRepository.getAverageKillCountOnMap(mapName);
    }

    public Map<String, Long> getKillCountForWeapons() {
        List<Object[]> killCounts = killRepository.getKillCountForWeapons();
        return killCounts.stream()
                .collect(Collectors.toMap(
                        killCount -> (String) killCount[0],
                        killCount -> (Long) killCount[1]
                ));
    }

    public Map<String, Map<String,BigDecimal>> getAverageKillCountForSides() {
        List<String> mapNames = Arrays.stream(MapEnum.values())
                .map(MapEnum::getMap)
                .toList();
        List<String> sides = Arrays.asList("CounterTerrorist", "Terrorist");

        Map<String, Map<String, BigDecimal>> result = new HashMap<>();

        for (String map : mapNames) {
            Map<String, BigDecimal> mapResult = new HashMap<>();
            for (String side : sides) {
                List<Long> killCounts = killRepository.getKillCountsForSideOnMap(map, side);
                double averageKills = killCounts.stream().mapToLong(Long::longValue).average().orElse(0);
                mapResult.put(side, BigDecimal.valueOf(averageKills));
            }
            result.put(map, mapResult);
        }

        return result;
    }
}
