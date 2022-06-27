package com.games.pizzaquest.textparser;

import com.games.pizzaquest.objects.ExternalFileReader;

import java.util.*;

public class TextParser {
    /*
    A class that is designed to take in user input and call other function depending on
    what the user types.
         */
    String currentInput = "";
    Map<String, ArrayList<String>> synonyms = ExternalFileReader.getSynonymListFromJson();
    Set<String> synonymKeys = synonyms.keySet();

    public List<String> parse(String userInput) {
        currentInput = userInput;
        System.out.println(synonymKeys);
        //takes in user input and then splits it on the spaces. Logic comes later
        List<String> parsedUserInput = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
        //after we break up the user input send it to be process
        if (parsedUserInput.size() == 3) {
            String multiWordCommandOrNoun = parsedUserInput.get(0).concat(" " + parsedUserInput.get(1));
            System.out.println(multiWordCommandOrNoun);
            for (String key : synonymKeys){
                if (synonyms.get(key).contains(multiWordCommandOrNoun)){
                    return formatTwoWordActionSingleWordNounCommand(parsedUserInput);
                }
            }
            parsedUserInput = formatOneWordActionMultiWordNounCommand(parsedUserInput);
        }
        else if(parsedUserInput.size() >= 4){
            parsedUserInput = formatTwoWordActionMultiWordNounCommand(parsedUserInput);
        }
        else{
            parsedUserInput.set(0, getCommandFromSynonym(parsedUserInput.get(0)));
        }
        return parsedUserInput;
    }

    private List<String> formatOneWordActionMultiWordNounCommand(List<String> rawInput){
        List<String> formattedCommand = rawInput;
        String formattedNoun = rawInput.get(1).concat(" " + rawInput.get(2));
        formattedCommand.set(0, getCommandFromSynonym(rawInput.get(0)));
        formattedCommand.set(1, getCommandFromSynonym(formattedNoun));
        formattedCommand.remove(2);

        return formattedCommand;
    }
    private List<String> formatTwoWordActionMultiWordNounCommand(List<String> rawInput){
        List<String> formattedCommand = rawInput;
        String formattedAction = formattedCommand.get(0).concat(" " + formattedCommand.get(1));
        String formattedNoun = formattedCommand.get(2).concat(" " + formattedCommand.get(3));

        formattedCommand.set(0, getCommandFromSynonym(formattedAction));
        formattedCommand.set(1, formattedNoun);
        formattedCommand.remove(3);
        formattedCommand.remove(2);

        return formattedCommand;
    }
    private List<String> formatTwoWordActionSingleWordNounCommand(List<String> rawInput){
        List<String> formattedCommand = rawInput;
        String formattedAction = rawInput.get(0).concat(" " + rawInput.get(1));
        formattedCommand.set(0, getCommandFromSynonym(formattedAction));
        formattedCommand.remove(1);

        return formattedCommand;
    }

    private String getCommandFromSynonym(String userInput){
        String inputSynonym = userInput;
        for(String key : synonymKeys){
            if (synonyms.get(key).contains(userInput)){
                inputSynonym = key;
            }
        }
        return inputSynonym;
    }


}