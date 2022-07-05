package com.games.pizzaquest.objects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Gamestate {
    public static final int WINNING_REPUTATION = 100;
    public static final int TURN_LIMIT = 15;
    private final Hashtable<String, Location> gameMap = hashNewMap(ExternalFileReader.getLocationListFromJson());
    private Location playerLocation;
    private Player player;
    private int gameOver = 0;
    private boolean isGodMode = false;


    public Gamestate(Location playerLocation) {
        this.playerLocation = playerLocation;
    }

    public Gamestate(Location playerLocation, Player player) {
        this(playerLocation);
        this.player = player;
    }

    public static Hashtable<String, Location> hashNewMap(List<Location> initialMap) {
        Hashtable<String, Location> newMap = new Hashtable<>();
        for (Location location : initialMap) {
            location.setItems(new ArrayList<>());
            newMap.put(location.getName(), location);
        }
        return newMap;
    }

    public void checkGameOver(int turns, int reputation) {
        if (turns > TURN_LIMIT) {
            this.gameOver = -1;
        }
        else if (reputation >= WINNING_REPUTATION) {
            this.gameOver = 1;
        }
    }

    public Location getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(Location newPlayerLocation) {
        this.playerLocation = newPlayerLocation;
    }

    public Hashtable<String, Location> getGameMap() {
        return gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public int getGameOver() {
        return gameOver;
    }

    public boolean isGodMode() {
        return isGodMode;
    }

    public void setGodMode(boolean godMode) {
        isGodMode = godMode;
    }

    public static int getTURN_LIMIT() {
        return TURN_LIMIT;
    }

    public static int getWINNING_REPUTATION() {
        return WINNING_REPUTATION;
    }
}