package com.games.pizzaquest.objects;

import java.util.List;
import java.util.Objects;

public class Item {
    private String name;
    private String type;
    private String room;
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getName().equals(item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}