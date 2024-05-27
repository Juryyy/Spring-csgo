package com.example.csgo.domain.match;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long>{

    List<Match> findByMap(String map_name);

}
