package com.games.pizzaquest.textparser;

import com.games.pizzaquest.objects.ExternalFileReader;

import java.util.*;

public class TextParser{
    /*
    A class that is designed to take in user input and call other function depending on
    what the user types.
         */

    Map<String, ArrayList<String>> synonyms = ExternalFileReader.getSynonymListFromJson();
    Set<String> synonymKeys = synonyms.keySet();

    public List<String> parse(String userInput) {
        //takes in user input and then splits it on the spaces. Logic comes later
        if (userInput == null){
            userInput = "";
        }
        List<String> parsedUserInput = new ArrayList<>(Arrays.asList(userInput.trim().toLowerCase().split(" ")));
        parsedUserInput.removeIf(string -> string.equals(""));
        if (parsedUserInput.size() > 1) {
            String multiWordCommandOrNoun = parsedUserInput.get(0).concat(" " + parsedUserInput.get(1));
            for (String key : synonymKeys) {
                if (synonyms.get(key).contains(multiWordCommandOrNoun)) {
                    parsedUserInput.set(0, key);
                    if (parsedUserInput.size() > 2) {
                        parsedUserInput.remove(1);
                    }
                    parsedUserInput.set(1,formatMultiWordNoun(parsedUserInput));
                    trimUserInputList(parsedUserInput);
                    return parsedUserInput;
                }
            }
            parsedUserInput.set(0,getCommandFromSynonym(parsedUserInput.get(0)));
            parsedUserInput.set(1, formatMultiWordNoun(parsedUserInput));
        }
        else{
            parsedUserInput.set(0, getCommandFromSynonym(parsedUserInput.get(0)));
        }

        //after we break up the user input send it to be process
        trimUserInputList(parsedUserInput);
        return parsedUserInput;
    }

    private String formatMultiWordNoun(List<String> multiWordNoun){
        String formattedNoun = "";
        for (int i = 1; i < multiWordNoun.size(); i++) {
            formattedNoun = formattedNoun.concat(multiWordNoun.get(i) + " ");
        }
        return formattedNoun.trim();
    }

    private void trimUserInputList(List<String> listOverTwoElements){
        while (listOverTwoElements.size() > 2){
            listOverTwoElements.remove(listOverTwoElements.size()-1);
        }
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