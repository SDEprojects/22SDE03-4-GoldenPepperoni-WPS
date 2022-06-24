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
    public String displayCommands() {
        System.out.println("You must use the following commands to navigate: ");
        System.out.println();
        StringBuilder commands = new StringBuilder();
        commands.append("\t\t\tGAME HELP\n---------------------------------------------------------\n");
        for (GameText gt : texts) {
            commands.append(gt.printHelp());
            System.out.println();
        }
        commands.append("---------------------------------------------------------");
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
            System.out.println(help);
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
