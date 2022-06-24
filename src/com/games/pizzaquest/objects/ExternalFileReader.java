package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract public class ExternalFileReader {

    private static final String npcFilePath = "npc.json";
    private static final String locationFilePath = "gamemap.json";
    private static final String textFilePath = "instructions.json";
    private static final String itemFilePath = "items.json";
    private static final String bannerFilePath = "WelcomeSplash.txt";
    private static final Type locationListType = new TypeToken<ArrayList<Location>>() {
    }.getType();
    private static final Type itemListType = new TypeToken<List<Item>>() {
    }.getType();
    private static GameTexts gameTexts;
    private static List<Location> locationList;

    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = PizzaQuestApp.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    public static ArrayList<NonPlayerCharacter> NpcGson() {
        // create Gson instance
        Gson gson = new Gson();
        ArrayList<NonPlayerCharacter> npcList = new ArrayList<>();
        InputStream npcJSON = getFileFromResourceAsStream(npcFilePath);
        // convert JSON file to map
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(npcJSON, StandardCharsets.UTF_8))) {
            Map<String, ArrayList<String>> map = gson.fromJson(reader, Map.class);
            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                ArrayList<String> JSONnpc = map.get(entry.getKey());
                NonPlayerCharacter npc = new NonPlayerCharacter(entry.getKey(), JSONnpc.get(0), JSONnpc.get(1), JSONnpc.get(2), JSONnpc.get(3), JSONnpc.get(4));
                npcList.add(npc);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return npcList;
    }

    public static List<Location> getLocationListFromJson() {
        ArrayList<Location> locationList = new ArrayList<>();
        Gson gson = new Gson();
        InputStream locationJSON = getFileFromResourceAsStream(locationFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(locationJSON, StandardCharsets.UTF_8))) {
            locationList = gson.fromJson(reader, locationListType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return locationList;
    }

    public static List<Item> getItemListFromJson() {
        ArrayList<Item> itemsList = new ArrayList<>();
        Gson gson = new Gson();
        InputStream locationJSON = getFileFromResourceAsStream(itemFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(locationJSON, StandardCharsets.UTF_8))) {
            itemsList = gson.fromJson(reader, itemListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemsList;
    }

    public static void GameTextGson() {
        Gson gson = new Gson();
        InputStream locationJSON = getFileFromResourceAsStream(textFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(locationJSON, StandardCharsets.UTF_8))) {
            // create Gson instance

            // convert JSON file to GameTexts Object which contains the GameText
            gameTexts = gson.fromJson(reader, GameTexts.class);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String welcome() {
        InputStream welcomeSplash = getFileFromResourceAsStream(bannerFilePath);
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (welcomeSplash, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
            return textBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textBuilder.toString();
    }

    public static String gameInstructions() {

        return gameTexts.displayCommands();
    }

}