package com.games.pizzaquest.objects;

import java.util.ArrayList;

public class GameTexts {
    ArrayList<GameText> texts;

    public ArrayList<GameText> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<GameText> texts) {
        this.texts = texts;
    }

    // Create a method that will print all the GameText in the ArrayList
    public String displayCommands(Gamestate gamestate) {
        StringBuilder commands = new StringBuilder();
        commands.append("----------------------- Game Help -----------------------\n\n");
        for (GameText gt : texts) {
            commands.append(gt.printHelp());
        }

        if (gamestate.isGodMode()){
            commands.append("--------------------- God Mode Help ---------------------\n\n");
            commands.append("God Mode allows you to take any item in the game regardless of where you are and you get unlimited moves!\n\n");
            commands.append("\t\tAvailable Items in God Mode\n");
            ArrayList<Item> allItems = (ArrayList<Item>) ExternalFileReader.getItemListFromJson();
            for (Item item : allItems){
                commands.append("  " + item.getName() + "\n");
            }
        }
        return commands.toString();
    }

    private static class GameText {
        private String command;
        private String option;
        private String description;


        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String printHelp() {
            String help = "\"" + command + translateOption() + "\" " + description + ".\n\n";
            return help;
        }

        private String translateOption() {
            if (option.isEmpty()) {
                return "";
            }
            return " [" + option + "]";
        }
    }
}
