package com.games.pizzaquest.objects;

import java.util.*;

public class Location {
    private final String name;
    public NonPlayerCharacter npc = null;
    private final String north;
    private final String east;
    private final String south;
    private final String west;
    private ArrayList<Item> items;

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
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

    public Location (String name, String north, String south, String east, String west ){
        this.name = name;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public Location (String name, NonPlayerCharacter NPC,String north, String south, String east, String west ){
        this.name = name;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.npc = NPC;
    }

    public String getNextLocation(String direction){
        String nextLoc = null;
        switch (direction.toLowerCase()){
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

    private String printBoarders(){
        return "\nTo the north we have " + getNorth() + ",\nTo the east we have " + getEast()+
                ",\nTo the south we have " + getSouth() + ",\nTo the west we have " + getWest()+".";
    }

    private StringBuilder printItems(){
        StringBuilder showItems = new StringBuilder();
        if ( items != null) {
            for (Item item : getItems()){
                showItems.append("I see a " + item.getName() + ".\n");
            }
        }
        return showItems;
    }

    public String getName() {
        return name;
    }
    public void setNpc(NonPlayerCharacter npc){
        this.npc = npc;
    }


    @Override
    public String toString(){
        return "You are in the " + getName() + "." + printBoarders() +"\n" + printItems() +
                (npc != null?npc.getName()+" is standing in the room": "there is no one in the room");
    }


}