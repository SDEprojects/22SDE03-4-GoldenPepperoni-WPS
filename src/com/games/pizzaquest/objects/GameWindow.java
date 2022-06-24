package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class GameWindow {
    private static final TextParser parser = new TextParser();
    private static JTextArea gameText;
    private static JTextArea locationText;
    private static JTextArea inventoryText;
    private final Font FIELD_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private final JFrame frame;
    private final JTextField entry;
    private final JButton send;
    private final JButton exitButton;
    private final JLabel errorLabel;
    private PizzaQuestApp app;

    public GameWindow(Gamestate gamestate) {
        frame = new JFrame("Golden Pepperoni Pizza");
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        entry = new JTextField();
        entry.setText("Enter Command");
        // This code below helps with placeholder text
        entry.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (entry.getText().equals("Enter Command")) {
                    entry.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (entry.getText().equals("")) {
                    entry.setText("Enter Command");
                }
            }
        });
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
        errorLabel.setBounds(10, frame.getHeight() - 95, 300, 20);
        frame.add(errorLabel);

        exitButton = new JButton("Quit");
        exitButton.setBounds(frame.getWidth() - 60, frame.getHeight() - 70, 40, 20);
        exitButton.setMargin(new Insets(2, 2, 3, 2));
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        frame.add(exitButton);

        // logo on top
        ImageIcon logo = new ImageIcon("resources/roundPizza.jpg");
        frame.setIconImage(logo.getImage());

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static JTextArea getInventoryLabel() {
        return inventoryText;
    }

    private void processCommand(Gamestate gamestate) {
        gameText.setText("Command sent would be: " + entry.getText());
        List<String> commandParsed = parser.parse(entry.getText());
        CommandsParser.processCommands(commandParsed, gamestate, gamestate.getPlayer());
        getLocationLabel().setText(setLocationLabel(gamestate));
        getInventoryLabel().setText(setInventoryLabel(gamestate));


        errorLabel.setVisible(entry.getText().isEmpty());

        entry.setText(null);
    }

    public JTextArea getGameLabel() {
        return gameText;
    }

    public JTextArea getLocationLabel() {
        return locationText;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public String setLocationLabel(Gamestate gamestate) {
        return gamestate.getPlayerLocation().toString();
    }

    public String setInventoryLabel(Gamestate gamestate) {
        String inventoryString = "Items in room: \n";

        Location location = gamestate.getPlayerLocation();
        ArrayList<Item> items = gamestate.getPlayerLocation().getItems();
        for (Item item : gamestate.getPlayerLocation().getItems()) {
            inventoryString = String.format(inventoryString + "  " + item.getName() + "\n");
        }

        return inventoryString;
    }

}