package com.example.csgo.domain.match;

import com.example.csgo.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}