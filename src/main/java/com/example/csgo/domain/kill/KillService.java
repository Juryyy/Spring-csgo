package com.example.csgo.domain.kill;

import org.springframework.stereotype.Service;

@Service
public class KillService {
    private final KillRepository killRepository;

    public KillService(KillRepository killRepository) {
        this.killRepository = killRepository;
    }

    public Kill createKill(Kill kill){
        return killRepository.save(kill);
    }
}
