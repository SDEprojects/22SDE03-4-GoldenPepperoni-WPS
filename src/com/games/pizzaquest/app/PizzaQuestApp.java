package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.*;
import com.games.pizzaquest.textparser.TextParser;

import java.util.*;

public class PizzaQuestApp {
    private static final Hashtable<String, Location> gameMap = Gamestate.hashNewMap(ExternalFileReader.getLocationListFromJson());
    private static final List<Item> itemsList = ExternalFileReader.getItemListFromJson();
    //Initial State of the Player, inventory and starting location
    private static final Set<Item> inventory = new HashSet<>();
    public static final Player player = new Player(inventory);
    private static ArrayList<NonPlayerCharacter> npcList;
    private static Gamestate gamestate;
    public final List<String> itemList = List.of("pizza_cutter", "olive_oil", "prosciutto", "wine_glass", "lemons", "coin", "ancient_pizza_cookbook", "moped", "cannoli", "marble_sculpture", "espresso");
    // Game window serves as the controller/game loop.
    private GameWindow gameWindow;

    public void execute() {
        TextParser parser = new TextParser();

        //temporary setting of description for npc
        //temporarily put in a 1 iteration loop to test user input
        npcList = ExternalFileReader.NpcGson();
        setNPC();
        ExternalFileReader.GameTextGson();
        //itemsList = ExternalFileReader.getItemListFromJson();
        addItemsToLocationMap(gameMap, itemsList);

        String welcomeMsg = ExternalFileReader.welcome();
        gamestate = new Gamestate(gameMap.get("naples"), player);
        gameWindow = new GameWindow(gamestate);
        gameWindow.getGameLabel().setText(welcomeMsg);
    }

    public static List<Item> getItemsList() {
        return itemsList;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void addItemsToLocationMap(Hashtable<String, Location> gameMap, List<Item> itemsList) {
        itemsList.forEach(item -> {
            gameMap.get(item.getRoom().toLowerCase()).getItems().add(item);
        });
    }

    public static void setNPC() {
        String tempNPCLocation = "";
        Location setNPCLocation = null;
        for (NonPlayerCharacter person : npcList
        ) {
            tempNPCLocation = person.getNpcLocation();
            setNPCLocation = gameMap.get(tempNPCLocation);
            if (setNPCLocation != null) {
                setNPCLocation.setNpc(person);
            }
        }
    }

    public static Hashtable<String, Location> getGameMap() {
        return gameMap;
    }

    public static Gamestate getGamestate() {
        return gamestate;
    }

    public void setGamestate(Gamestate gamestate) {
        PizzaQuestApp.gamestate = gamestate;
    }
}