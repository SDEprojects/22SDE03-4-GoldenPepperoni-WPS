package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Set;

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
    private final JPanel MainPanel;
    private final JPanel TopRightPanel;
    private final JPanel BottomRightPanel;
    private PizzaQuestApp app;

    public GameWindow(Gamestate gamestate) {
        // Game text
        gameText = new JTextArea("This is the game field");
        gameText.setEditable(false);
        gameText.setOpaque(false);
        gameText.setBorder(BorderFactory.createLineBorder(Color.black));
        gameText.setBounds(10, 10, 400, 370);
        gameText.setFont(FIELD_FONT);
        gameText.setLineWrap(true);
        gameText.setWrapStyleWord(true); // wraps the word

        // Location text
        locationText = new JTextArea(setLocationLabel(gamestate));
        locationText.setEditable(false);
        locationText.setOpaque(false);
        locationText.setBorder(BorderFactory.createLineBorder(Color.black));
        locationText.setBounds(420, 10, 200, 190);

        // Inventory text
        inventoryText = new JTextArea(String.valueOf(setInventoryLabel(gamestate)));
        inventoryText.setEditable(false);
        inventoryText.setOpaque(false);
        inventoryText.setBorder(BorderFactory.createLineBorder(Color.black));
        inventoryText.setBounds(420, 210, 200, 190);

        // Main Panel = Big Left Panel
        MainPanel = new JPanel();
        MainPanel.setBackground(Color.pink);
        MainPanel.setBounds(5,0,410, 500);
        MainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        MainPanel.setBackground(Color.cyan);
        MainPanel.setLayout(new GridLayout(0,1));
        MainPanel.add(gameText);

        // Top Right Panel
        TopRightPanel = new JPanel();
        TopRightPanel.setBackground(Color.orange);
        TopRightPanel.setBounds(420,0,230,250);
        TopRightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        TopRightPanel.setLayout(new GridLayout(0,1));
        TopRightPanel.add(locationText);

        // Bottom Right Panel
        BottomRightPanel = new JPanel();
        BottomRightPanel.setBackground(Color.orange);
        BottomRightPanel.setBounds(420,255,230,245);
        BottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        BottomRightPanel.setLayout(new GridLayout(0,1));
        BottomRightPanel.add(inventoryText);

        // Frame
        frame = new JFrame("Golden Pepperoni Pizza");
        frame.setSize(654, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // prevents resizing the window
        frame.add(MainPanel);
        frame.add(TopRightPanel);
        frame.add(BottomRightPanel);

        // User entry
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
        entry.addActionListener(e -> sendCommand(gamestate, this));
        frame.add(entry);

        send = new JButton("Send");
        send.setBounds(entry.getX() + entry.getWidth() + 10, frame.getHeight() - 70, 60, 20);
        send.setMargin(new Insets(2, 2, 3, 2));
        send.addActionListener(e -> sendCommand(gamestate, this));
        frame.add(send);

        errorLabel = new JLabel("Invalid command");
        errorLabel.setBackground(Color.RED);
        errorLabel.setOpaque(true);
        errorLabel.setBounds(10, frame.getHeight() - 95, 300, 20);
        errorLabel.setVisible(false);
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

        // Added background color
        frame.getContentPane().setBackground(new Color(Color.pink.getRGB()));

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static JTextArea getInventoryLabel() {
        return inventoryText;
    }

    private void sendCommand(Gamestate gamestate, GameWindow gameWindow) {
        gameText.setText("Command sent would be: " + entry.getText());
        List<String> commandParsed = parser.parse(entry.getText());
        errorLabel.setVisible(!CommandsParser.processCommands(commandParsed, gamestate, gameWindow));
        getLocationLabel().setText(setLocationLabel(gamestate));
        getInventoryLabel().setText(setInventoryLabel(gamestate));

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
        StringBuilder inventoryString = new StringBuilder("Items in inventory: \n");

        Set<Item> playerItems = gamestate.getPlayer().getInventory();

        if (playerItems.isEmpty()){
            inventoryString.append("  EMPTY");
        }
        else {
            for (Item item : playerItems) {
                inventoryString.append("  ").append(item.getName()).append("\n");
            }
        }

        return inventoryString.toString();
    }

}