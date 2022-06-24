package com.games.pizzaquest.textparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParser {
    /*
    A class that is designed to take in user input and call other function depending on
    what the user types.
         */
    String currentInput = "";

    public List<String> parse(String userInput) {
        currentInput = userInput;
        //takes in user input and then splits it on the spaces. Logic comes later
        List<String> parsedUserInput = new ArrayList<>(Arrays.asList(userInput.toLowerCase().split(" ")));
        //after we break up the user input send it to be process
        if (parsedUserInput.size() < 2) {
            parsedUserInput.add("");
        }
        return parsedUserInput;
    }

}