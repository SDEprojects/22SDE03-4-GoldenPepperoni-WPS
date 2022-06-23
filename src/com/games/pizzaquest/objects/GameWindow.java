package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameWindow {
    private final Font FIELD_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private static TextParser parser = new TextParser();
    private PizzaQuestApp app;

    private JFrame frame;
    private JTextField entry;
    private JButton send;
    private JButton exitButton;
    private JTextArea gameText;
    private JTextArea locationText;
    private JTextArea inventoryText;
    private JLabel errorLabel;

    public GameWindow(Gamestate gamestate) {
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
        gameText.setBounds(10, 10, 400, 370);
        gameText.setFont(FIELD_FONT);
        gameText.setLineWrap(true);
        frame.add(gameText);

        locationText = new JTextArea(setLocationLabel(gamestate));
        locationText.setEditable(false);
        locationText.setOpaque(false);
        locationText.setBorder(BorderFactory.createLineBorder(Color.black));
        locationText.setBounds(420, 10, 200, 190);
        frame.add(locationText);

        inventoryText = new JTextArea(String.valueOf(setInventoryLabel(gamestate)));
        inventoryText.setEditable(false);
        inventoryText.setOpaque(false);
        inventoryText.setBorder(BorderFactory.createLineBorder(Color.black));
        inventoryText.setBounds(420, 210, 200, 190);
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

    private void processCommand(Gamestate gamestate) {
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


    public JTextArea getGameLabel() {
        return gameText;
    }

    public JTextArea getLocationLabel() {
        return locationText;
    }

    public JTextArea getInventoryLabel() {
        return inventoryText;
    }

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