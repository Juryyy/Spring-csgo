package com.example.csgo.domain.match;

import com.example.csgo.utils.interfaces.map.MapCount;
import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface MatchRepository extends CrudRepository<Match, Long>{

    List<Match> findByMap(String map_name);

    @Query("SELECT m.map as map, COUNT(m) as count FROM Match m GROUP BY m.map")
    List<MapCount> countMatchesByMap();

    @Query("SELECT DISTINCT m.map FROM Match m")
    List<String> findAllMaps();

    @Query(value = "SELECT m.map AS map, COUNT(r.id) AS rounds, r.match_id AS match_id " +
            "FROM rounds r " +
            "JOIN matches m ON m.id = r.match_id " +
            "WHERE m.map = :map " +
            "GROUP BY m.map, r.match_id " +
            "ORDER BY rounds DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Map<String, Object> findMatchWithHighestRoundsForMap(String map);
}
