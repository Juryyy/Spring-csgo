package com.example.csgo.domain.match;

import com.example.csgo.utils.exceptions.NotFoundException;
import com.example.csgo.utils.interfaces.map.MapCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> getAllMatches() {
        return (List<Match>) matchRepository.findAll();
    }

    public List<Match> getMatchesByMap(String map_name) {
        return matchRepository.findByMap(map_name);
    }

    public void createMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new NotFoundException();
        }
        matchRepository.deleteById(id);
    }

    public Map<String, Long> getMapCounts() {
        List<MapCount> counts = matchRepository.countMatchesByMap();
        return counts.stream().collect(Collectors.toMap(MapCount::getMap, MapCount::getCount));
    }
}