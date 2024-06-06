package com.example.csgo.domain.match;

import com.example.csgo.utils.interfaces.map.MapCount;

public class ConcreteMapCount implements MapCount {
    private String map;
    private Long count;

    public ConcreteMapCount(String map, Long count) {
        this.map = map;
        this.count = count;
    }

    @Override
    public String getMap() {
        return map;
    }

    @Override
    public Long getCount() {
        return count;
    }
}