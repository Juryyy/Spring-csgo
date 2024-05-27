package com.example.csgo.domain.match;

import com.example.csgo.utils.interfaces.map.MapCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long>{

    List<Match> findByMap(String map_name);

    @Query("SELECT m.map as map, COUNT(m) as count FROM Match m GROUP BY m.map")
    List<MapCount> countMatchesByMap();
}
