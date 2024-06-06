package com.example.csgo.utils.interfaces.round;

public enum SideEnum {
    CounterTerrorist("CounterTerrorist"),

    Terrorist("Terrorist");

    private final String side;

    SideEnum(String side){
        this.side = side;
    }

    public String getSide(){
        return side;
    }
}
