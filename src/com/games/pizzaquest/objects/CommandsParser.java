package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class CommandsParser {
    private static int turns;
    private static int reputation;
    private static GameWindow gameWindow;
    private static Hashtable<String, Location> gameMap = Gamestate.getGameMap();
    private static Player player = PizzaQuestApp.getPlayer();
    private static List<Item> itemList = PizzaQuestApp.getItemsList();
    private static Gamestate gamestate = PizzaQuestApp.getGamestate();



    public static void processCommands(List<String> verbAndNounList, Gamestate gamestate, Player player){
        String noun = verbAndNounList.get(verbAndNounList.size()-1);
        String verb = verbAndNounList.get(0);
        String person= "";
        ArrayList<String> validDirections= new ArrayList<String>();
        validDirections.add("north");
        validDirections.add("east");
        validDirections.add("west");
        validDirections.add("south");

        switch (verb) {
            case "quit":
                quitGame();
                break;
            case "go":
                if (noun.equals("") || !validDirections.contains(noun)){
                    break;
                }

                String nextLoc = gamestate.getPlayerLocation().getNextLocation(noun);
                System.out.println();
                if(!nextLoc.equals("nothing")){
                    System.out.println(nextLoc);
                    gamestate.setPlayerLocation(gameMap.get(nextLoc.toLowerCase()));
                    System.out.println();
                    GameWindow.setLocationLabel(gamestate);
                    System.out.println(player.look(gamestate.getPlayerLocation()));
                    System.out.println();

                }
                else{
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
                if (noun.equals("")){
                    break;
                }
                if(itemList.contains(noun)){
                    System.out.println(player.look(new Item(noun)));
                }else if (gamestate.getPlayerLocation().npc!= null && gamestate.getPlayerLocation().npc.getName().equals(noun)){
                    System.out.println(gamestate.getPlayerLocation().npc.getNpcDescription());
                }
                else{
                    System.out.println(player.look(gamestate.getPlayerLocation()));
                }
                break;
            case "take":
                //add item to inventory
                for(Item item : gamestate.getPlayerLocation().getItems()){
                    if (item.getName().equals(noun)){
                        player.addToInventory(noun);
                    }
                }

                gamestate.getPlayerLocation().getItems().removeIf(item -> item.getName().equals(noun));
                System.out.println("Player inventory: " + player.getInventory());
                System.out.println("Items in location: " + gamestate.getPlayerLocation().getItems());
                break;
            case "talk":
                //add item to inventory
                talk(gamestate,noun);
                break;
            case "give":
                //removes item from inventory
                if (noun.equals("")){
                    break;
                }
                if(gamestate.getPlayerLocation().npc!=null){
                    reputation = gamestate.getPlayerLocation().npc.processItem(noun);
                }
                player.removeFromInventory(noun);
                break;
            case "inventory":
                Set<Item> tempInventory = player.getInventory();
                System.out.println("Items in the Inventory");
                for (Item item : tempInventory) {
                    System.out.println(item.getName());
                }
                break;
            case "help":
                ExternalFileReader.gameInstructions();
                break;
            case "reset":
                //resetGame();
                break;
            default:
                System.out.printf("I don't understand '%s'%n", verbAndNounList);
                System.out.println("Type help if you need some guidance on command structure!");
                break;
        }
    }

    public static int getTurns(){
        return turns;
    }
    public static void quitGame() {
        System.out.println("You'll always have a pizza our heart ... Goodbye!");
        setGameOver(true);
        System.exit(0);
    }

    public static boolean setGameOver(boolean gameOver) {
        return gameOver;
    }

//    private static void resetGame() {
//        PizzaQuestApp resetGame = new PizzaQuestApp();
//        setGameOver(true);
//        turns = 0;
//        resetGame.execute();
//    }

    private static void talk(Gamestate gamestate, String noun) {
        Location playerLocation = gamestate.getPlayerLocation();
        if(playerLocation.npc != null && playerLocation.npc.getName().equals(noun)){
            System.out.println(playerLocation.npc.giveQuest());
        }else{
            System.out.println("That player many not be in in this room or even exist!");
        }
    }

}