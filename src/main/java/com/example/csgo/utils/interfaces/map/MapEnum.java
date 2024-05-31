package com.example.csgo.utils.interfaces.map;


public enum MapEnum {

    de_mirage("de_mirage"),
    de_overpass("de_overpass"),
    de_inferno("de_inferno"),
    de_cbble("de_cbble"),
    de_train("de_train"),
    de_cache("de_cache"),
    de_nuke("de_nuke"),
    de_dust2("de_dust2"),
    de_anubis("de_anubis"),
    de_ancient("de_ancient"),
    de_vertigo("de_vertigo");

    private final String map;

    MapEnum(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }
}
