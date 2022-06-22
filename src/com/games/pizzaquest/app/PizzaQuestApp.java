package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.*;
import com.games.pizzaquest.textparser.TextParser;

import java.util.*;

public class PizzaQuestApp {

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art


        //track turn may be moved to player
        private static final int turns = CommandsParser.getTurns();
        static final int END_OF_TURNS=10;
        static final int WINNING_REPUTATION= 40;
        private ArrayList<NonPlayerCharacter> npcList;
        private Hashtable<String, Location> gameMap;
        private List<Item> itemsList;
        private Gamestate gamestate;
        private int reputation;
        public final List<String> itemList = List.of("pizza_cutter","olive_oil", "prosciutto", "wine_glass", "lemons", "coin", "ancient_pizza_cookbook", "moped", "cannoli", "marble_sculpture", "espresso");

        //Initial State of the Player, inventory and starting location
        private final Set<Item> inventory = new HashSet<>();
        public final Player player = new Player(inventory);


        //keep the game running until win/lose condition is met
        private boolean isGameOver = CommandsParser.setGameOver(false);


        public void execute() {
                TextParser parser = new TextParser();
                CommandsParser.setGameOver(false);
                //temporary setting of description for npc
                //temporarily put in a 1 iteration loop to test user input
                npcList = ExternalFileReader.NpcGson();
                gameMap = hashNewMap(ExternalFileReader.getLocationListFromJson());
                setNPC();
                ExternalFileReader.GameTextGson();
                itemsList = ExternalFileReader.getItemListFromJson();

                addItemsToLocationMap(gameMap, itemsList);
                ExternalFileReader.welcome();
                gamestate = new Gamestate(gameMap.get("naples"));
                System.out.println(enterName());
                while(turns < END_OF_TURNS) {
                        //send user input to parser to validate and return a List
                        //then runs logic in relation to the map, and list based on Noun Verb Relationship

                        CommandsParser.processCommands(parser.parse(scanner.nextLine()), gameMap, player, itemsList, gamestate);
                        checkIfGameIsWon();
                        // Increment turns by 1
                        //Display player status including number of turns left
                        int turnsLeft = END_OF_TURNS - CommandsParser.getTurns();
                        System.out.println("It's day " + CommandsParser.getTurns() + ". You have " + turnsLeft + " days left." );
                        //Players reputation is displayed whenever status is updated
                        System.out.println("Your reputation is " + reputation);

                }
                CommandsParser.quitGame();
        }
        public boolean isGameOver() {
                return isGameOver;
        }


        public void checkIfGameIsWon() {
                if(reputation >=WINNING_REPUTATION){
                        System.out.println("You win");
                        CommandsParser.quitGame();
                }
        }

        private String enterName() {
                System.out.println("Please enter your name: ");
                String playerName = scanner.nextLine();
                return ("Ciao " + playerName+ " you are in " + gamestate.getPlayerLocation());
        }

//        private void quitGame() {
//                System.out.println("You'll always have a pizza our heart ... Goodbye!");
//                setGameOver(true);
//                System.exit(0);
//        }
//        public boolean isGameOver() {
//                return isGameOver;
//        }
//
//        public void setGameOver(boolean gameOver) {
//                isGameOver = gameOver;
//        }
//
//        private void resetGame() {
//                setGameOver(true);
//                turns = 0;
//                execute();
//        }

        //take the processed command and the delegates this to another
//        private void processCommands(List<String> verbAndNounList){
//                String noun = verbAndNounList.get(verbAndNounList.size()-1);
//                String verb = verbAndNounList.get(0);
//                String person= "";
//                ArrayList<String> validDirections= new ArrayList<String>();
//                validDirections.add("north");
//                validDirections.add("east");
//                validDirections.add("west");
//                validDirections.add("south");
//
//                switch (verb) {
//                        case "quit":
//                                quitGame();
//                                break;
//                        case "go":
//                                if (noun.equals("") || !validDirections.contains(noun)){
//                                        break;
//                                }
//                                String nextLoc = gamestate.getPlayerLocation().getNextLocation(noun);
//                                System.out.println();
//                                if(!nextLoc.equals("nothing")){
//                                        System.out.println(nextLoc);
//                                        gamestate.setPlayerLocation(gameMap.get(nextLoc.toLowerCase()));
//                                        System.out.println();
//                                        System.out.println(player.look(gamestate.getPlayerLocation()));
//                                        System.out.println();
//                                        turns++;
//                                }
//                                else{
//                                        System.out.println("There is nothing that way!");
//                                }
//                                break;
//                        case "look":
//                                //look(); //player location or item  description printed
//                                //will need a item list and a location list
//                                //todo - check size and get last
//                                //if room, do the first, else if item, do the second
//                                if (noun.equals("")){
//                                        break;
//                                }
//                                if(itemList.contains(noun)){
//                                        System.out.println(player.look(new Item(noun)));
//                                }else if (gamestate.getPlayerLocation().npc!= null && gamestate.getPlayerLocation().npc.getName().equals(noun)){
//                                        System.out.println(gamestate.getPlayerLocation().npc.getNpcDescription());
//                        }
//                                else{
//                                        System.out.println(player.look(gamestate.getPlayerLocation()));
//                                }
//                                break;
//                        case "take":
//                                //add item to inventory
//                                if(gamestate.getPlayerLocation().getItems().contains(noun)){
//                                        player.addToInventory(noun);
//                                }
//                                else{
//                                        System.out.println(noun + " is not in room");
//                                }
//
//                                gamestate.getPlayerLocation().getItems().removeIf(item -> item.getName().equals(noun));
//                                System.out.println("Player inventory: " + player.getInventory());
//                                System.out.println("Items in location: " + gamestate.getPlayerLocation().getItems());
//                                break;
//                        case "talk":
//                                //add item to inventory
//                                talk(noun);
//                                break;
//                        case "give":
//                                //removes item from inventory
//                                if (noun.equals("")){
//                                        break;
//                                }
//                                if(gamestate.getPlayerLocation().npc!=null){
//                                       reputation = gamestate.getPlayerLocation().npc.processItem(noun);
//                                }
//                                player.removeFromInventory(noun);
//                                break;
//                        case "inventory":
//                                Set<Item> tempInventory = player.getInventory();
//                                System.out.println("Items in the Inventory");
//                                for (Item item : tempInventory) {
//                                        System.out.println(item.getName());
//                                }
//                                break;
//                        case "help":
//                                ExternalFileReader.gameInstructions();
//                                break;
//                        case "reset":
//                                resetGame();
//                                break;
//                        default:
//                                System.out.printf("I don't understand '%s'%n", verbAndNounList);
//                                System.out.println("Type help if you need some guidance on command structure!");
//                                break;
//                }
//        }

//        private void talk(String noun) {
//                Location playerLocation = gamestate.getPlayerLocation();
//                if(playerLocation.npc != null && playerLocation.npc.getName().equals(noun)){
//                        System.out.println(playerLocation.npc.giveQuest());
//                }else{
//                        System.out.println("That player many not be in in this room or even exist!");
//                }
//        }

        public static void addItemsToLocationMap(Hashtable<String, Location> gameMap, List<Item> itemsList){
                itemsList.forEach(item -> {
                        gameMap.get(item.getRoom().toLowerCase()).getItems().add(item);
                });
        }


        public static Hashtable<String, Location> hashNewMap(List<Location> initialMap) {
                Hashtable<String, Location> newMap = new Hashtable<>();
                for(Location location: initialMap){
                        location.setItems(new ArrayList<>());
                        newMap.put(location.getName(), location);
                }
                return newMap;
        }

        public void setNPC(){
                String tempNPCLocation = "";
                Location setNPCLocation= null;
                for (NonPlayerCharacter person:npcList
                     ) {
                        tempNPCLocation= person.getNpcLocation();
                        setNPCLocation= gameMap.get(tempNPCLocation);
                        if(setNPCLocation != null){
                        setNPCLocation.setNpc(person);}
                }
        }

        public Hashtable<String, Location> getGameMap() {
                return gameMap;
        }

        public Gamestate getGamestate() {
                return gamestate;
        }
}