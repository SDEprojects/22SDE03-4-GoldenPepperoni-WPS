package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandsParser {
    private static final ArrayList<Item> itemList = (ArrayList<Item>) ExternalFileReader.getItemListFromJson();
    private static int turns;
    private static int reputation;

    public static boolean processCommands(List<String> verbAndNounList, Gamestate gamestate, GameWindow window) {
        boolean validCommand = true;
        String noun = verbAndNounList.get(verbAndNounList.size() - 1);
        String verb = verbAndNounList.get(0);
        ArrayList<String> validDirections = new ArrayList<>();
        validDirections.add("north");
        validDirections.add("east");
        validDirections.add("west");
        validDirections.add("south");

        switch (verb) {
            case "quit":
                quitGame();
                break;
            case "go":
                if (noun.equals("") || !validDirections.contains(noun)) {
                    validCommand = false;
                    break;
                }

                String nextLoc = gamestate.getPlayerLocation().getNextLocation(noun);
                System.out.println();
                if (!nextLoc.equals("nothing")) {
                    System.out.println(nextLoc);
                    gamestate.setPlayerLocation(PizzaQuestApp.getGameMap().get(nextLoc.toLowerCase()));

                    System.out.println();
                    System.out.println(gamestate.getPlayer().look(gamestate.getPlayerLocation()));
                    System.out.println();

                } else {
                    System.out.println("There is nothing that way!");
                    break;
                }
                turns += 1;
                break;
            case "look":
                //look(); //player location or item  description printed
                //will need a item list and a location list
                //todo - check size and get last
                //if room, do the first, else if item, do the second

                Item item;
                if (noun.equals("")) {
                    validCommand = false;
                    break;
                } else {
                    item = ExternalFileReader.getSingleItem(noun);
                }

                if (itemList.contains(item)) {
                    window.getGameLabel().setText(item.getDescription());
                } else if (gamestate.getPlayerLocation().npc != null && gamestate.getPlayerLocation().npc.getName().equals(noun)) {
                    window.getGameLabel().setText(gamestate.getPlayerLocation().npc.getNpcDescription());
                } else {
                    window.getGameLabel().setText(gamestate.getPlayer().look(gamestate.getPlayerLocation()));
                }
                break;
            case "take":
                boolean itemFound = false;
                //add item to inventory
                for (Item i : gamestate.getPlayerLocation().getItems()) {
                    if (i.getName().equals(noun)) {
                        gamestate.getPlayer().addToInventory(noun);
                        itemFound = true;
                    }
                }

                gamestate.getPlayerLocation().getItems().removeIf(i -> i.getName().equals(noun));
                System.out.println("Player inventory: " + gamestate.getPlayer().getInventory());
                System.out.println("Items in location: " + gamestate.getPlayerLocation().getItems());
                if (!itemFound) {
                    validCommand = false;
                }
                break;
            case "talk":
                //add item to inventory
                talk(gamestate, noun);
                window.getGameLabel().setText(CommandsParser.talk(gamestate, verbAndNounList.get(1)));
                break;
            case "give":
                //removes item from inventory
                if (noun.equals("")) {
                    validCommand = false;
                    break;
                }
                if (gamestate.getPlayerLocation().npc != null) {
                    reputation += gamestate.getPlayerLocation().npc.processItem(noun);
                }
                gamestate.getPlayer().removeFromInventory(noun);
                break;
            case "inventory":
                Set<Item> tempInventory = gamestate.getPlayer().getInventory();
                System.out.println("Items in the Inventory");
                for (Item i : tempInventory) {
                    System.out.println(i.getName());
                }
                break;
            case "help":
                window.getGameLabel().setText(ExternalFileReader.gameInstructions());
                break;
            case "reset":
                //resetGame();
                break;
            default:
                System.out.printf("I don't understand '%s'%n", verbAndNounList);
                System.out.println("Type help if you need some guidance on command structure!");
                validCommand = false;
                break;
        }
        // Make a gameover check after the command is processed.
        gamestate.checkGameOver(turns, reputation);
        return validCommand;
    }

    public static void quitGame() {
        System.out.println("You'll always have a pizza our heart ... Goodbye!");
        System.exit(0);
    }

//    private static void resetGame() {
//        PizzaQuestApp resetGame = new PizzaQuestApp();
//        setGameOver(true);
//        turns = 0;
//        resetGame.execute();
//    }

    public static String talk(Gamestate gamestate, String noun) {
        Location playerLocation = gamestate.getPlayerLocation();
        if (playerLocation.npc != null && playerLocation.npc.getName().equals(noun)) {
            System.out.println(playerLocation.npc.giveQuest());
            return playerLocation.npc.giveQuest();
        }

        return "That player many not be in in this room or even exist!";

    }

}