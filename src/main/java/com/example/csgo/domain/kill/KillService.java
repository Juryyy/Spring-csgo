package com.example.csgo.domain.kill;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KillService {
    private final KillRepository killRepository;

    public KillService(KillRepository killRepository) {
        this.killRepository = killRepository;
    }

    public Kill createKill(Kill kill){
        return killRepository.save(kill);
    }

    public Kill getKillById(Long id){
        return killRepository.findById(id).orElse(null);
    }

    public List<Kill> getKillsByMatchId(Long matchId) {
        return killRepository.getKillsByMatchId(matchId);
    }
}
