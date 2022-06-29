package com.games.pizzaquest.textparser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParserTest {

    List<String> parsedText;
    String oneWordVerbOneWordNounString;
    String oneWordVerbTwoWordNounString;
    String twoWordVerbOneWordNounString;
    String twoWordVerbTwoWordNounString;

    TextParser textParser;

    @Before
    public void setUp() {
        oneWordVerbOneWordNounString = "go south";
        oneWordVerbTwoWordNounString = "take wine glass";
        twoWordVerbOneWordNounString = "pick up coin";
        twoWordVerbTwoWordNounString = "pick up pizza cutter";
        textParser = new TextParser();
    }

    @Test
    public void textParser_ShouldReturnTwoElementList_WhenInputStringContainsOneWordVerbAndOneWordNoun() {
        parsedText = (textParser.parse(oneWordVerbOneWordNounString));
        Assert.assertEquals(2,parsedText.size());
    }
    @Test
    public void textParser_ShouldReturnTwoElementList_WhenInputStringContainsOneWordVerbAndTwoWordNoun() {
        parsedText = (textParser.parse(oneWordVerbTwoWordNounString));
        Assert.assertEquals(2,parsedText.size());
    }
    @Test
    public void textParser_ShouldReturnTwoElementList_WhenInputStringContainsTwoWordVerbAndOneWordNoun() {
        parsedText = (textParser.parse(twoWordVerbOneWordNounString));
        Assert.assertEquals(2,parsedText.size());
    }
    @Test
    public void textParser_ShouldReturnTwoElementList_WhenInputStringContainsTwoWordVerbAndTwoWordNoun() {
        parsedText = (textParser.parse(twoWordVerbTwoWordNounString));
        Assert.assertEquals(2,parsedText.size());
    }

    //***Synonym testing****//
    //Take
    @Test
    public void textParser_ShouldReturnTake_WhenInputStringContainsTakeTwoWordSynonym() {
        parsedText = (textParser.parse(twoWordVerbOneWordNounString));
        Assert.assertEquals("take", parsedText.get(0));
    }

    @Test
    public void textParser_ShouldReturnTake_WhenInputStringContainsTakeSynonym() {
        ArrayList<String> takeSynonym = new ArrayList<>(Arrays.asList( "grab","obtain","get"));
        for (String synonym : takeSynonym) {
            parsedText = (textParser.parse(synonym + " PLACEHOLDER"));
            Assert.assertEquals("take", parsedText.get(0));
        }
    }

    //Talk
    @Test
    public void textParser_ShouldReturnTalk_WhenInputStringContainsTalkTwoWordSynonym() {
        parsedText = (textParser.parse("talk to caligula"));
        Assert.assertEquals("talk", parsedText.get(0));
    }

    @Test
    public void textParser_ShouldReturnTalk_WhenInputStringContainsTalkSynonym() {
        ArrayList<String> takeSynonym = new ArrayList<>(List.of("speak"));
        for (String synonym : takeSynonym) {
            parsedText = (textParser.parse(synonym + " PLACEHOLDER"));
            Assert.assertEquals("talk", parsedText.get(0));
        }
    }

    //Go
    @Test
    public void textParser_ShouldReturnGo_WhenInputStringContainsGoSynonym() {
        ArrayList<String> takeSynonym = new ArrayList<>(List.of("move","travel","walk","ride","drive"));
        for (String synonym : takeSynonym) {
            parsedText = (textParser.parse(synonym + " PLACEHOLDER"));
            Assert.assertEquals("go", parsedText.get(0));
        }
    }

    //Look
    @Test
    public void textParser_ShouldReturnLook_WhenInputStringContainsLookSynonym() {
        ArrayList<String> takeSynonym = new ArrayList<>(List.of("inspect"));
        for (String synonym : takeSynonym) {
            parsedText = (textParser.parse(synonym + " PLACEHOLDER"));
            Assert.assertEquals("look", parsedText.get(0));
        }
    }
    //Quit
    @Test
    public void textParser_ShouldReturnQuit_WhenInputStringContainsQuitSynonym() {
        ArrayList<String> takeSynonym = new ArrayList<>(List.of("close", "exit"));
        for (String synonym : takeSynonym) {
            parsedText = (textParser.parse(synonym + " PLACEHOLDER"));
            Assert.assertEquals("quit", parsedText.get(0));
        }
    }
}