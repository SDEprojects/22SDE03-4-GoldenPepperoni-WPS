package com.games.pizzaquest.objects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Gamestate {
    private Location playerLocation;
    private static Player player;
    private static Hashtable<String, Location> gameMap = hashNewMap(ExternalFileReader.getLocationListFromJson());


    public Gamestate(Location playerLocation){
        this.playerLocation = playerLocation;
    }

    public Gamestate(Location playerLocation, Player player){
        this(playerLocation);
        this.player = player;
    }

    public Location getPlayerLocation() {
        return this.playerLocation;
    }

    public void setPlayerLocation(Location playerLocation){
        this.playerLocation = playerLocation;
    }
    public static Hashtable<String, Location> hashNewMap(List<Location> initialMap) {
        Hashtable<String, Location> newMap = new Hashtable<>();
        for(Location location: initialMap){
            location.setItems(new ArrayList<>());
            newMap.put(location.getName(), location);
        }
        return newMap;
    }

    public static Hashtable<String, Location> getGameMap() {
        return gameMap;
    }

    public static Player getPlayer() {
        return player;
    }
}