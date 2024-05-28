package com.example.csgo.domain.round;

import com.example.csgo.utils.interfaces.round.MatchRoundCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface RoundRepository extends CrudRepository<Round, Long> {
    @Query(value = "SELECT r.match.id as matchId, m.map as map, COUNT(r) as count FROM Round r JOIN r.match m GROUP BY r.match.id, m.map")
    List<MatchRoundCount> countRoundsByMatch();

}
