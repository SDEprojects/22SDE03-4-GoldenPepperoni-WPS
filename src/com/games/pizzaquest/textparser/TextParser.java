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
        //takes in user input and then splits it on the spaces. Logic comes later
        List<String> parsedUserInput = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
        //after we break up the user input send it to be process
        if (parsedUserInput.size() == 3) {
            String multiWordCommandOrNoun = parsedUserInput.get(0).concat(" " + parsedUserInput.get(1));
            for (String key : synonymKeys){
                if (synonyms.get(key).contains(multiWordCommandOrNoun)){
                    return formatTwoWordActionSingleWordNounCommand(parsedUserInput);
                }
            }
            formatOneWordActionMultiWordNounCommand(parsedUserInput);
        }
        else if(parsedUserInput.size() >= 4){
            formatTwoWordActionMultiWordNounCommand(parsedUserInput);
        }
        else{
            parsedUserInput.set(0, getCommandFromSynonym(parsedUserInput.get(0)));
        }
        return parsedUserInput;
    }

    private void formatOneWordActionMultiWordNounCommand(List<String> rawInput){
        String formattedNoun = rawInput.get(1).concat(" " + rawInput.get(2));
        rawInput.set(0, getCommandFromSynonym(rawInput.get(0)));
        rawInput.set(1, getCommandFromSynonym(formattedNoun));
        rawInput.remove(2);
    }
    private void formatTwoWordActionMultiWordNounCommand(List<String> rawInput){
        String formattedAction = rawInput.get(0).concat(" " + rawInput.get(1));
        String formattedNoun = rawInput.get(2).concat(" " + rawInput.get(3));

        rawInput.set(0, getCommandFromSynonym(formattedAction));
        rawInput.set(1, formattedNoun);
        rawInput.remove(3);
        rawInput.remove(2);
    }
    private List<String> formatTwoWordActionSingleWordNounCommand(List<String> rawInput){
        String formattedAction = rawInput.get(0).concat(" " + rawInput.get(1));
        rawInput.set(0, getCommandFromSynonym(formattedAction));
        rawInput.remove(1);

        return rawInput;
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