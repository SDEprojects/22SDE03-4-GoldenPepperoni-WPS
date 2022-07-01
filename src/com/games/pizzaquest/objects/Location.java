package com.games.pizzaquest.objects;

import java.awt.*;
import java.util.ArrayList;

public class Location {
    private final String name;
    private final String north;
    private final String east;
    private final String south;
    private final String west;
    private final int xCoord;
    private final int yCoord;

    private final String description;
    public NonPlayerCharacter npc = null;
    private ArrayList<Item> items;

    public Location(String name, String north, String south, String east, String west, String description, int xCoord, int yCoord) {
        this.name = name;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.description = description;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Location(String name, NonPlayerCharacter NPC, String north, String south, String east, String west, String description, int xCoord, int yCoord) {
        this.name = name;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.npc = NPC;
        this.description = description;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
    public String getDescription() {
        return description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getNorth() {
        return north;
    }

    public String getEast() {
        return east;
    }

    public String getSouth() {
        return south;
    }

    public String getWest() {
        return west;
    }

    public String getNextLocation(String direction) {
        String nextLoc = null;
        switch (direction.toLowerCase()) {
            case "north":
                nextLoc = getNorth();
                break;
            case "east":
                nextLoc = getEast();
                break;
            case "south":
                nextLoc = getSouth();
                break;
            case "west":
                nextLoc = getWest();
                break;
            default:
                break;
        }
        return nextLoc;
    }

    private String printBoarders() {
        return "\n  To the north we have " + getNorth() + "\n\n  To the east we have " + getEast() +
                "\n\n  To the south we have " + getSouth() + "\n\n  To the west we have " + getWest();
    }

    private StringBuilder printItems() {
        StringBuilder showItems = new StringBuilder();
        if (items != null) {
            for (Item item : getItems()) {
                showItems.append("  " + item.getName() + ".\n");
            }
        }
        return showItems;
    }

    public String getName() {
        return name;
    }

    public void setNpc(NonPlayerCharacter npc) {
        this.npc = npc;
    }


    @Override
    public String toString() {
        return "\nDetailed location information\n------------------------------ " + "\nItems in room:" + "\n" + printItems() +
                "\nNPCs in room:\n  " +
                (npc != null ? npc.getName() + " is in the room" : "There is no one in the room");
    }

    public String windowToString() {
        return "Location: " + getName() + ".\n\nExits:" + printBoarders();
    }

    public Point getMapLocation() {
        return new Point(xCoord, yCoord);
    }
}