package com.example.csgo.domain.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {


    @Autowired
    private MatchService matchService;

    @GetMapping(value = "", produces = "application/json")
    public List<Match> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return matches;
    }

    @GetMapping(value = "/{map_name}", produces = "application/json")
    public List<Match> getMatchesByMap(@PathVariable String map_name) {
        List<Match> matches = matchService.getMatchesByMap(map_name);
        return matches;
    }
}