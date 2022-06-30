package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GameWindow {
    private final Font FIELD_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private final int NAV_WIDTH = 22;
    private final int NAV_HEIGHT = 22;

    private static final TextParser parser = new TextParser();
    private static JTextArea gameText;
    private static JTextArea locationText;
    private static JTextArea inventoryText;
    private final JFrame frame;
    private final JTextField entry;
    private final JButton send;
    private final JButton exitButton;
    private final JButton mapButton;
    private final JLabel errorLabel;
    private final JPanel mainPanel;
    private final JPanel topRightPanel;
    private final JPanel bottomRightPanel;
    private final JPanel navigationPanel;
    private final JButton northButton;
    private final JButton westButton;
    private final JButton eastButton;
    private final JButton southButton;
    private PizzaQuestApp app;
    ImageIcon logo = new ImageIcon("roundPizza.jpg");

//    ImageIcon mapPicture;
//    JLabel mapLabel;

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
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.pink);
        mainPanel.setBounds(5, 0, 410, 500);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.setBackground(Color.cyan);
        mainPanel.setLayout(new GridLayout(0, 1));
        mainPanel.add(gameText);

        // Top Right Panel
        topRightPanel = new JPanel();
        topRightPanel.setBackground(Color.orange);
        topRightPanel.setBounds(420, 0, 230, 250);
        topRightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        topRightPanel.setLayout(new GridLayout(0, 1));
        topRightPanel.add(locationText);

        // Bottom Right Panel
        bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.orange);
        bottomRightPanel.setBounds(420, 255, 230, 245);
        bottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        bottomRightPanel.setLayout(new GridLayout(0, 1));
        bottomRightPanel.add(inventoryText);

        // Navigation Panel
        navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.white);
        navigationPanel.setBounds(653, 2, 100, 100);
        navigationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        navigationPanel.setLayout(new GridLayout(3, 3));

        // Frame
        frame = new JFrame("Golden Pepperoni Pizza");
        frame.setSize(770, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // prevents resizing the window
        frame.add(mainPanel);
        frame.add(topRightPanel);
        frame.add(bottomRightPanel);
        frame.add(navigationPanel);

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

        // Send button
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

        // Map button
        mapButton = new JButton("Map");
        mapButton.setBounds(frame.getWidth()-120, frame.getHeight()-70, 40, 20);
        mapButton.setMargin((new Insets(2, 2, 3, 2)));
        mapButton.setBackground(Color.darkGray);
        mapButton.setForeground(Color.WHITE);
        mapButton.addActionListener(e -> {
            try {
                mapPage();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        frame.add(mapButton);

        // Quit button
        exitButton = new JButton("Quit");
        exitButton.setBounds(frame.getWidth() - 60, frame.getHeight() - 70, 40, 20);
        exitButton.setMargin(new Insets(2, 2, 3, 2));
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        frame.add(exitButton);

        // Create Nav buttons
        northButton = createNavButton("N", "go north", gamestate, this);
        westButton = createNavButton("W", "go west", gamestate, this);
        eastButton = createNavButton("E", "go east", gamestate, this);
        southButton = createNavButton("S", "go south", gamestate, this);
        navigationPanel.add(new JLabel());
        navigationPanel.add(northButton);
        navigationPanel.add(new JLabel());
        navigationPanel.add(westButton);
        navigationPanel.add(new JLabel());
        navigationPanel.add(eastButton);
        navigationPanel.add(new JLabel());
        navigationPanel.add(southButton);

        // logo on top
        frame.setIconImage(logo.getImage());

        // Added background color
        frame.getContentPane().setBackground(new Color(Color.pink.getRGB()));

        frame.setLayout(null);
        frame.setVisible(true);
    }

    // Display game map page
    private void mapPage() throws URISyntaxException {

        ImageIcon mapPicture = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameMapPicture.png")));
        JLabel mapLabel = new JLabel();

        mapLabel.setIcon(mapPicture);
        JDialog mapFrame = new JDialog();
        mapFrame.setModal(true);

        mapFrame.add(mapLabel);
        mapFrame.pack();

        mapFrame.setSize(900, 800);
        mapFrame.setResizable(true);

        mapFrame.setIconImage(logo.getImage());
        mapFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mapFrame.setVisible(true);
    }

    public JTextArea getInventoryLabel() {
        return inventoryText;
    }

    private void sendCommand(Gamestate gamestate, GameWindow gameWindow) {
        List<String> commandParsed = parser.parse(entry.getText());
        errorLabel.setVisible(!CommandsParser.processCommands(commandParsed, gamestate, gameWindow));
        processGameOver(gamestate.getGameOver());
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
        return gamestate.getPlayerLocation().windowToString();
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

    public void processGameOver(int gameOverValue) {
        if (gameOverValue == 0) {
            return;
        }
        else {
            send.setEnabled(false);
            entry.setEnabled(false);
            northButton.setEnabled(false);
            westButton.setEnabled(false);
            eastButton.setEnabled(false);
            southButton.setEnabled(false);
        }

        if (gameOverValue == -1) {
            gameText.setText("PLACEHOLDER YOU LOSE MESSAGE");
        }
        else {
            gameText.setText("PLACEHOLDER YOU WIN MESSAGE");
        }
    }
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }
    }

    private JButton createNavButton(String name, String target, Gamestate game, GameWindow window) {
        List<String> commandParsed = parser.parse(target);

        JButton button = new JButton(name);
        button.setSize(NAV_WIDTH, NAV_HEIGHT);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.addActionListener(e -> CommandsParser.processCommands(commandParsed, game, window));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }
}