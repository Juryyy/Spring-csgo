package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends CrudRepository<Round, Long> {
    @Query(value = "SELECT r.match.id as matchId, m.map as map, COUNT(r) as count FROM Round r JOIN r.match m GROUP BY r.match.id, m.map")
    List<MatchRoundCount> countRoundsByMatch();

    @Query(value = "SELECT COUNT(r) FROM Round r WHERE r.match.map = :map")
    Long countRoundsForMap(String map);

    void deleteAllByMatch_Id(Long id);
    List<Round> getRoundsByMatchId(Long matchId);

    @Query(value = "SELECT AVG(subquery.count_id) FROM (SELECT COUNT(r.id) AS count_id FROM rounds r GROUP BY r.match_id) AS subquery", nativeQuery = true)
    Double getAverageRoundCount();

    @Query(value = "SELECT AVG(subquery.count_id) FROM (SELECT COUNT(r.id) AS count_id FROM rounds r JOIN matches m ON r.match_id = m.id WHERE m.map = :map GROUP BY r.match_id) AS subquery", nativeQuery = true)
    Double getAvgCountRoundsForMap(String map);

    @Query(value = "SELECT COUNT(r) FROM Round r WHERE r.match.map = :map AND r.winnerSide = 'CounterTerrorist'")
    Long countRoundsWonByCT(String map);

    @Query(value = "SELECT COUNT(r) FROM Round r WHERE r.match.map = :map AND r.winnerSide = 'Terrorist'")
    Long countRoundsWonByT(String map);

    boolean existsByMatch_IdAndRound(Long matchId, int round);
}
