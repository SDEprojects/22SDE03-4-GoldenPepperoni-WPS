package com.games.pizzaquest.app;

import com.games.pizzaquest.objects.*;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import java.util.*;

public class PizzaQuestApp {

        //scanner for the game
        static Scanner scanner = new Scanner(System.in);
        //text parser for users to use
        //path for some ascii art


        //track turn may be moved to player
        private static final int turns = CommandsParser.getTurns();
        static final int END_OF_TURNS = 20;
        static final int WINNING_REPUTATION= 40;
        private static ArrayList<NonPlayerCharacter> npcList;
        private static Hashtable<String, Location> gameMap = Gamestate.hashNewMap(ExternalFileReader.getLocationListFromJson());
        private static List<Item> itemsList;
        private static Gamestate gamestate;
        private static int reputation;
        public final List<String> itemList = List.of("pizza_cutter","olive_oil", "prosciutto", "wine_glass", "lemons", "coin", "ancient_pizza_cookbook", "moped", "cannoli", "marble_sculpture", "espresso");
        private GameWindow gameWindow;
        //Initial State of the Player, inventory and starting location
        private static final Set<Item> inventory = new HashSet<>();
        public static final Player player = new Player(inventory);


        //keep the game running until win/lose condition is met
        private final boolean isGameOver = false;


        public void execute() {
                TextParser parser = new TextParser();
                CommandsParser.setGameOver(false);
                //temporary setting of description for npc
                //temporarily put in a 1 iteration loop to test user input
                npcList = ExternalFileReader.NpcGson();
                setNPC();
                ExternalFileReader.GameTextGson();
                itemsList = ExternalFileReader.getItemListFromJson();
                addItemsToLocationMap(gameMap, itemsList);

                String welcomeMsg = ExternalFileReader.welcome();
                gamestate = new Gamestate(gameMap.get("naples"), player);
                gameWindow = new GameWindow(gamestate);
                gameWindow.getGameLabel().setText(welcomeMsg);

                System.out.println(enterName());
                while(turns < END_OF_TURNS) {
                        //GameWindow(gamestate);
                        //send user input to parser to validate and return a List
                        //then runs logic in relation to the map, and list based on Noun Verb Relationship

                        CommandsParser.processCommands(parser.parse(scanner.nextLine()), getGamestate(), Gamestate.getPlayer());
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

        public static List<Item> getItemsList() {
                return itemsList;
        }

        public static Player getPlayer() {
                return player;
        }

        public static void checkIfGameIsWon() {
                if(reputation >=WINNING_REPUTATION){
                        System.out.println("You win");
                        CommandsParser.quitGame();
                }
        }

        private static String enterName() {
                System.out.println("Please enter your name: ");
                String playerName = scanner.nextLine();
                return ("Ciao " + playerName+ " you are in " + gamestate.getPlayerLocation());
        }

        public static void addItemsToLocationMap(Hashtable<String, Location> gameMap, List<Item> itemsList){
                itemsList.forEach(item -> {
                        gameMap.get(item.getRoom().toLowerCase()).getItems().add(item);
                });
        }


//        public static Hashtable<String, Location> hashNewMap(List<Location> initialMap) {
//                Hashtable<String, Location> newMap = new Hashtable<>();
//                for(Location location: initialMap){
//                        location.setItems(new ArrayList<>());
//                        newMap.put(location.getName(), location);
//                }
//                return newMap;
//        }

        public static void setNPC(){
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

        public static Hashtable<String, Location> getGameMap() {
                return gameMap;
        }

        public static Gamestate getGamestate() {
                return gamestate;
        }
}