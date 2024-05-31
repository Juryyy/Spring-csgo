package com.example.csgo.domain.kill;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KillRepository extends CrudRepository<Kill, Long>{

    void deleteAllByMatch_Id(Long id);

    List<Kill> getKillsByMatchId(Long matchId);

}
