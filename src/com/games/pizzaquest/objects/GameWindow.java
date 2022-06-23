package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

abstract public class GameWindow {
    private static TextParser parser = new TextParser();
    private static PizzaQuestApp app;

    private static JFrame frame;
    private static JTextField entry;
    private static JButton send;
    private static JButton exitButton;
    private static JTextArea gameText;
    private static JTextArea locationText;
    private static JTextArea inventoryText;
    private static JLabel errorLabel;

    public GameWindow(){

    }

    public static void createGameWindow(Gamestate gamestate) {
        frame = new JFrame("TextField Example");
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        entry = new JTextField("Enter command");
        entry.setBounds(10, frame.getHeight() - 70, 300, 20);
        entry.addActionListener(e -> processCommand(gamestate));
        frame.add(entry);

        send = new JButton("Send");
        send.setBounds(entry.getX() + entry.getWidth() + 10, frame.getHeight() - 70, 60, 20);
        send.setMargin(new Insets(2, 2, 3, 2));
        send.addActionListener(e -> processCommand(gamestate));
        frame.add(send);

        gameText = new JTextArea("This is the game field");
        gameText.setEditable(false);
        gameText.setOpaque(false);
        gameText.setBorder(BorderFactory.createLineBorder(Color.black));
        gameText.setBounds(10, 10, 300, 370);
        frame.add(gameText);

        locationText = new JTextArea(setLocationLabel(gamestate));
        locationText.setEditable(false);
        locationText.setOpaque(false);
        locationText.setBorder(BorderFactory.createLineBorder(Color.black));
        locationText.setBounds(320, 10, 300, 190);
        frame.add(locationText);

        inventoryText = new JTextArea(String.valueOf(setInventoryLabel(gamestate)));
        inventoryText.setEditable(false);
        inventoryText.setOpaque(false);
        inventoryText.setBorder(BorderFactory.createLineBorder(Color.black));
        inventoryText.setBounds(320, 210, 300, 190);
        frame.add(inventoryText);

        errorLabel = new JLabel("Errors show here");
        errorLabel.setBackground(Color.RED);
        errorLabel.setOpaque(true);
        errorLabel.setBounds(10, frame.getHeight()-95, 300, 20 );
        frame.add(errorLabel);

        exitButton = new JButton("Quit");
        exitButton.setBounds(frame.getWidth() - 60, frame.getHeight() - 70, 40, 20);
        exitButton.setMargin(new Insets(2, 2, 3, 2));
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        frame.add(exitButton);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static void processCommand(Gamestate gamestate) {
        gameText.setText("Command sent would be: " + entry.getText());
        List<String> commandParsed = parser.parse(entry.getText());

        if (commandParsed.get(0).equals("go")){
            CommandsParser.processCommands(commandParsed, gamestate, Gamestate.getPlayer());
            locationText.setText(setLocationLabel(gamestate));

        }

        if (entry.getText().isEmpty()) {
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
        }

        entry.setText(null);
    }


//    public JLabel getGameLabel() {
//        return gameText;
//    }

//    public JLabel getLocationLabel() {
//        return locationText;
//    }

//    public JLabel getInventoryLabel() {
//        return inventoryText;
//    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public static String setLocationLabel(Gamestate gamestate) {
        return gamestate.getPlayerLocation().toString();
    }

    public static StringBuilder setInventoryLabel(Gamestate gamestate) {
        StringBuilder inventoryString = new StringBuilder();
        inventoryString.append("Items in room: \n");
        for (Item item : gamestate.getPlayerLocation().getItems()){
            inventoryString.append("  " + item.getName() + "\n");
        }

        return inventoryString;
    }

}