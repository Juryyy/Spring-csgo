package com.example.csgo.domain.round;

import com.example.csgo.domain.match.MatchRepository;
import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.csgo.utils.interfaces.map.MapEnum;

import java.util.*;

@Service
public class RoundService {
    @Autowired
    private RoundRepository roundRepository;

    public List<MatchRoundCount> getMatchRoundCounts() {
        return roundRepository.countRoundsByMatch();
    }

    @Autowired
    private MatchRepository matchRepository;

    public List<String> getAllMaps() {
        return matchRepository.findAllMaps();
    }

    public List<Map<String, Object>> getRoundsCountForAllMaps() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (MapEnum mapEnum : MapEnum.values()) {
            Long roundsCount = roundRepository.countRoundsForMap(mapEnum.getMap());
            Map<String, Object> mapRoundsCount = new HashMap<>();
            mapRoundsCount.put("map", mapEnum.getMap());
            mapRoundsCount.put("roundsCount", roundsCount);
            result.add(mapRoundsCount);
        }

        return result;
    }

    //getRoundsByMatchId
    public List<Round> getRoundsByMatchId(Long matchId) {
        return roundRepository.getRoundsByMatchId(matchId);
    }

    public double getAverageRoundCount() {
        Double average = roundRepository.getAverageRoundCount();
        return average != null ? average : 0;
    }

    public double getAverageRoundCountForMap(String map) {
        Double roundsCount = roundRepository.getAvgCountRoundsForMap(map);
        return roundsCount != null ? roundsCount : 0;
    }


}