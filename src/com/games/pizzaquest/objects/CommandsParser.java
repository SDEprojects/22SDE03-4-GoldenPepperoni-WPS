package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;

import java.util.ArrayList;
import java.util.List;

import static com.games.pizzaquest.objects.MusicPlayer.*;

public class CommandsParser {
    private static ArrayList<Item> itemList = (ArrayList<Item>) ExternalFileReader.getItemListFromJson();
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

                if (!nextLoc.equals("nothing")) {
                    gamestate.setPlayerLocation(PizzaQuestApp.getGameMap().get(nextLoc.toLowerCase()));
                    String message = String.format("You travel %s to %s.\n", verb, noun) +
                            gamestate.getPlayerLocation().getDescription();
                    window.getGameLabel().setText(message);
                } else {
                    String message = window.getGameLabel().getText() +
                            String.format("\n\nThere is nothing to the %s.", noun);
                    window.getGameLabel().setText(message);
                    break;
                }

                window.getLocationLabel().setText(window.setLocationLabel(gamestate));
                window.getInventoryLabel().setText(window.setInventoryLabel(gamestate));

                if (gamestate.isGodMode()) {
                    turns = 0;
                }
                else {
                    turns += 1;
                }

                break;
            case "look":
                Item item = ExternalFileReader.getSingleItem(noun);

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
                String message = null;
                //add item to inventory
                for (Item i : gamestate.getPlayerLocation().getItems()) {
                    if (i.getName().equals(noun) && !gamestate.isGodMode()) {
                        gamestate.getPlayer().addToInventory(noun);
                        itemFound = true;
                    }
                    else if (gamestate.isGodMode() && itemList.contains(i)){
                        gamestate.getPlayer().addToInventory(noun);
                        itemFound = true;
                    }
                }

                gamestate.getPlayerLocation().getItems().removeIf(i -> i.getName().equals(noun));

                if (!itemFound) {
                    if (gamestate.isGodMode()){
                        message = "\n\nEven with your godly powers, you cannot add " + noun + " to your inventory";
                    }
                    else {
                        message = "\n\nYou try to take the " + noun + " but you don't see it.";
                        validCommand = false;
                    }
                }
                else {
                    message = "\n\nYou take the " + noun;
                }

                window.getGameLabel().setText(window.getGameLabel().getText() + message);
                window.getInventoryLabel().setText(window.setInventoryLabel(gamestate));
                break;
            case "talk":
                //add item to inventory
                talk(gamestate, noun);
                String npcTalk = gamestate.getPlayerLocation().npc.getName();
                npcTalk = String.format("\n\nYou attempt to talk with %s.\n", npcTalk);
                StringBuilder currentText = new StringBuilder(window.getGameLabel().getText());
                currentText.append(npcTalk);
                currentText.append(CommandsParser.talk(gamestate, verbAndNounList.get(1)));
                window.getGameLabel().setText(currentText.toString());
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
                window.getInventoryLabel().setText(window.setInventoryLabel(gamestate));
                break;
            /*case "inventory":
                Set<Item> tempInventory = gamestate.getPlayer().getInventory();
                System.out.println("Items in the Inventory");
                for (Item i : tempInventory) {
                    System.out.println(i.getName());
                }
                break;*/
            case "help":
                    window.getGameLabel().setText(ExternalFileReader.gameInstructions(gamestate));
                break;
            case "reset":
                //resetGame();
                break;
            case "mute":
                stopMusic();
                break;
            case "unmute":
                if(!clip.isRunning()){
                    playMusic();
                }
                break;
            case "god":
                gamestate.setGodMode(true);
                break;
            default:
                String response = String.format("\n\nI don't understand '%s'%n\n", verbAndNounList) +
                        "Type help if you need some guidance on command structure!";
                window.getGameLabel().setText(response);
                validCommand = false;
                break;
        }
        // Make a gameover check after the command is processed.
        gamestate.checkGameOver(turns, reputation);
        return validCommand;
    }

    public static void quitGame() {
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
            return playerLocation.npc.giveQuest();
        }

        return String.format("There doesn't seem to be someone named \"%s\" here", noun);
    }

    public static ArrayList<Item> getItemList() {
        return itemList;
    }
}