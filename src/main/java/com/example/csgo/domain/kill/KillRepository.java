package com.example.csgo.domain.kill;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface KillRepository extends CrudRepository<Kill, Long>{

    void deleteAllByMatch_Id(Long id);

    List<Kill> getKillsByMatchId(Long matchId);
    @Query("SELECT m.map, m.id, COUNT(k) FROM Kill k JOIN k.match m WHERE m.map IN (:mapNames) GROUP BY m.map, m.id")
    List<Object[]> getKillsPerMatchForMaps(@Param("mapNames") List<String> mapNames);

    @Query("SELECT AVG(killCount) FROM (SELECT COUNT(k) as killCount FROM Kill k JOIN k.match m WHERE m.map = ?1 GROUP BY m.id)")
    BigDecimal getAverageKillCountOnMap(String mapName);

    @Query("SELECT k.weapon, COUNT(k) FROM Kill k GROUP BY k.weapon")
    List<Object[]> getKillCountForWeapons();

    @Query("SELECT COUNT(k) FROM Kill k WHERE k.match.map = :map AND k.attackSide = :side GROUP BY k.match.id")
    List<Long> getKillCountsForSideOnMap(@Param("map") String map, @Param("side") String side);

}
