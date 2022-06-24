package com.games.pizzaquest.objects;

import java.util.List;

public class Item {
    String name;
    String type;
    String room;
    String description;

    public Item(String name) {
        this.name = name;
        setType(name);
        setDescription(name);
    }

    public Item(String name, String type, String room) {
        this.name = name;
        setType(name);
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRoom() {
        return room;
    }

    public String getDescription(){
        return description;
    }

    public void setType(String name){
        List<Item> itemList = ExternalFileReader.getItemListFromJson();
        for (int i = 0; i < itemList.size(); i++){
            if (itemList.get(i).getName().equals(name)){
                type = itemList.get(i).type;
            }
        }
    }


    public void setDescription(String name){
        List<Item> itemList = ExternalFileReader.getItemListFromJson();
        for (int i = 0; i < itemList.size(); i++){
            if (itemList.get(i).getName().equals(name)){
                description = itemList.get(i).description;
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}