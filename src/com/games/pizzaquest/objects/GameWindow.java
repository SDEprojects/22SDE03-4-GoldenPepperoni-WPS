package com.games.pizzaquest.objects;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JFrame frame;
    private JTextField entry;
    private JButton send;
    private JButton exitButton;
    private JLabel gameLabel;
    private JLabel locationLabel;
    private JLabel inventoryLabel;
    private JLabel errorLabel;

    public GameWindow() {
        frame = new JFrame("TextField Example");
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        entry = new JTextField("Enter command");
        entry.setBounds(10, frame.getHeight() - 70, 300, 20);
        entry.addActionListener(e -> processCommand());
        frame.add(entry);

        send = new JButton("Send");
        send.setBounds(entry.getX() + entry.getWidth() + 10, frame.getHeight() - 70, 60, 20);
        send.setMargin(new Insets(2, 2, 3, 2));
        send.addActionListener(e -> processCommand());
        frame.add(send);

        gameLabel = new JLabel("This is the game field");
        gameLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameLabel.setBounds(10, 10, 300, 370);
        gameLabel.setVerticalAlignment(SwingConstants.TOP);
        frame.add(gameLabel);

        locationLabel = new JLabel("This is the location field");
        locationLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        locationLabel.setBounds(320, 10, 300, 190);
        locationLabel.setVerticalAlignment(SwingConstants.TOP);
        frame.add(locationLabel);

        inventoryLabel = new JLabel("This is the inventory field");
        inventoryLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        inventoryLabel.setBounds(320, 210, 300, 190);
        inventoryLabel.setVerticalAlignment(SwingConstants.TOP);
        frame.add(inventoryLabel);

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

    private void processCommand() {
        gameLabel.setText("Command sent would be: " + entry.getText());

        if (entry.getText().isEmpty()) {
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
        }

        entry.setText(null);
    }


    public JLabel getGameLabel() {
        return gameLabel;
    }

    public JLabel getLocationLabel() {
        return locationLabel;
    }

    public JLabel getInventoryLabel() {
        return inventoryLabel;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }
}