package com.games.pizzaquest.objects;

import com.games.pizzaquest.app.PizzaQuestApp;
import com.games.pizzaquest.textparser.TextParser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.games.pizzaquest.objects.MusicPlayer.*;

public class GameWindow {
    private final Font FIELD_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private final int NAV_WIDTH = 22;
    private final int NAV_HEIGHT = 22;
    private final int PROGRESS_BAR_MAX = Gamestate.TURN_LIMIT * 100;
    private int currentVolume = 100;

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
    private final JButton lookButton;
    private final JPanel volumePanel;
    private final JLabel volumeLabel;
    private final JSlider volumeSlider;
    private final JProgressBar reputationBar;
    private final JProgressBar timeBar;
    private final JButton muteButton;
//    private final JButton unmuteButton;
    private final JPanel sliderPanel;
    private final JPanel mutePanel;
    private PizzaQuestApp app;
    ImageIcon logo = new ImageIcon("roundPizza.jpg");
    private GameWindow gameWindow;

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
        //inventoryText.setBorder(BorderFactory.createLineBorder(Color.black));
        inventoryText.setBounds(420, 210, 200, 210);

        // Reputation bar
        reputationBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        reputationBar.setString("REPUTATION");
        reputationBar.setValue(0);
        reputationBar.setStringPainted(true);
        reputationBar.setSize(200, 20);
        reputationBar.setForeground(new Color(102, 0, 102));
        reputationBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.black; }
            protected Color getSelectionForeground() { return Color.white; }
        });

        // Time bar
        timeBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, PROGRESS_BAR_MAX);
        timeBar.setString("TIME REMAINING");
        timeBar.setValue(PROGRESS_BAR_MAX);
        timeBar.setStringPainted(true);
        timeBar.setSize(200, 20);
        timeBar.setForeground(Color.green);
        timeBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.black; }
            protected Color getSelectionForeground() { return Color.black; }
        });

        JPanel barPanel = new JPanel();
        barPanel.setLayout(new BorderLayout(0, 0));
        barPanel.add(timeBar, BorderLayout.NORTH);
        barPanel.add(reputationBar, BorderLayout.SOUTH);

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
        bottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        bottomRightPanel.setLayout(new BorderLayout(2, 2));
        bottomRightPanel.add(inventoryText, BorderLayout.NORTH);
        bottomRightPanel.add(barPanel, BorderLayout.SOUTH);

        // Navigation Panel
        navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.white);
        navigationPanel.setBounds(653, 2, 100, 100);
        navigationPanel.setLayout(new GridLayout(3, 3));

        // Volume Slider
        volumeLabel = new JLabel();
        volumeSlider = new JSlider(0, 100, 100);
        volumeSlider.setPreferredSize(new Dimension(200, 200));
        volumeSlider.setPaintTicks(true);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setPaintTrack(true);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setFont(new Font("MV Boli", Font.PLAIN, 8));
        volumeLabel.setFont(new Font("MV Boli", Font.PLAIN, 10));
        volumeSlider.setOrientation(SwingConstants.VERTICAL);
        volumeLabel.setText("Vol =" + volumeSlider.getValue());
        volumeSlider.addChangeListener(this::stateChanged);

        // Volume Panel
        volumePanel = new JPanel();
        volumePanel.setBounds(653, 110, 100, 150);
        volumePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        volumePanel.setLayout(new GridLayout(1, 0));
        volumePanel.add(volumeSlider);
        volumePanel.add(volumeLabel);

        // Slider Panel
        sliderPanel = new JPanel();
        sliderPanel.setBounds(651, 108, 98, 248);
        sliderPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        sliderPanel.setLayout(new GridLayout(1, 0));
        sliderPanel.add(volumePanel);

        // Mute/Unmute Panel
        mutePanel = new JPanel();

        // Mute button
        muteButton = new JButton("Mute");
        muteButton.setBounds(668, 265, 70, 20);
        muteButton.setBackground(Color.red);
        muteButton.setForeground(Color.WHITE);
        muteButton.setBorder(BorderFactory.createRaisedBevelBorder());
        muteButton.addActionListener(e -> {
            if (!clip.isRunning()) {
                volumeLabel.setText("Vol = " + getCurrentVolume());
                volumeSlider.setEnabled(true);
                playMusic(getCurrentVolume());
                muteButton.setText("Mute");
                muteButton.setBackground(Color.RED);
            } else {
                currentVolume = volumeSlider.getValue();
                stopMusic();
                muteButton.setText("Unmute");
                muteButton.setBackground(Color.GREEN);
                volumeSlider.setEnabled(false);
                volumeLabel.setText("Muted");
            }

        });

        // Frame
        frame = new JFrame("Golden Pepperoni Pizza");
        frame.setSize(770, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // prevents resizing the window
        frame.add(mainPanel);
        frame.add(topRightPanel);
        frame.add(bottomRightPanel);
        frame.add(navigationPanel);
        frame.add(volumePanel);
        frame.add(muteButton);

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
                mapPage(gamestate);
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
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.white);
        frame.add(exitButton);

        // Create Nav buttons
        northButton = createNavButton("N", "go north", gamestate, this);
        westButton = createNavButton("W", "go west", gamestate, this);
        eastButton = createNavButton("E", "go east", gamestate, this);
        southButton = createNavButton("S", "go south", gamestate, this);
        lookButton = createNavButton("", "look", gamestate, this);
        lookButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/look20.png"))));
        lookButton.setBackground(Color.lightGray);

        navigationPanel.add(new JLabel());
        navigationPanel.add(northButton);
        navigationPanel.add(new JLabel());
        navigationPanel.add(westButton);
        navigationPanel.add(lookButton);
        navigationPanel.add(eastButton);
        navigationPanel.add(new JLabel());
        navigationPanel.add(southButton);

        // logo on top
        frame.setIconImage(logo.getImage());

        // Added background color
        frame.getContentPane().setBackground(new Color(Color.pink.getRGB()));
        navigationPanel.setBackground(Color.pink);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void stateChanged(ChangeEvent e) {
        volumeLabel.setText("Vol = " + volumeSlider.getValue());
        MusicPlayer.setVolume(volumeSlider.getValue());
    }

    // Display game map page
    private void mapPage(Gamestate gamestate) throws URISyntaxException {

        ImageIcon mapPicture = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameMapPicture.png")));
        JLabel mapLabel = new JLabel();

        mapLabel.setIcon(mapPicture);
        mapLabel.setBounds(0,0,849, 732);
        JDialog mapFrame = new JDialog();
        mapFrame.setModal(true);
        mapFrame.setLayout(null);


        mapFrame.pack();

        mapFrame.setSize(849, 732);
        mapFrame.setResizable(true);

        mapFrame.setIconImage(logo.getImage());
        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon locationIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/look32.png")));

        JLabel youAreHere = new JLabel();
        youAreHere.setIcon(locationIcon);
        youAreHere.setSize( 34, 34);
        youAreHere.setLocation(getMapCoords(gamestate));
        youAreHere.setVisible(true);

        // Order of add matters for visibility.
        mapFrame.add(youAreHere);
        mapFrame.add(mapLabel);

        mapFrame.setVisible(true);
    }

    private Point getMapCoords(Gamestate gamestate) {
        return gamestate.getPlayerLocation().getMapLocation();
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

    public int getCurrentVolume() {
        return currentVolume;
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
        StringBuilder inventoryString = new StringBuilder("Player Information: \n");
        Set<Item> playerItems = gamestate.getPlayer().getInventory();

        inventoryString.append("  Turns taken: ").append(CommandsParser.getTurns()).append("/").append(gamestate.getTURN_LIMIT()).append("\n");
        inventoryString.append("  Total Reputation: ").append(CommandsParser.getReputation()).append("/").append(gamestate.getWINNING_REPUTATION()).append("\n\n");
        inventoryString.append("  Inventory:\n");

        if (playerItems.isEmpty()){
            inventoryString.append("    EMPTY");
        }
        else {
            for (Item item : playerItems) {
                inventoryString.append("   -").append(item.getName()).append("\n");
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
            String youLoseMsg = ExternalFileReader.youLose();
            gameText.setText(youLoseMsg);
        }
        else {
            String youWinMsg = ExternalFileReader.youWin();
            gameText.setText(youWinMsg);
        }
    }

    private JButton createNavButton(String name, String target, Gamestate game, GameWindow window) {
        List<String> commandParsed = parser.parse(target);

        JButton button = new JButton(name);
        button.setSize(NAV_WIDTH, NAV_HEIGHT);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.addActionListener(e -> navButtonPress(commandParsed, game, window));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void navButtonPress(List<String> commandParsed, Gamestate gamestate, GameWindow window) {
        CommandsParser.processCommands(commandParsed, gamestate, window);
        processGameOver(gamestate.getGameOver());
    }

    public void updateReputation(int reputation) {
        reputationBar.setValue((int)(100.0 * reputation / Gamestate.WINNING_REPUTATION));
    }

    public void updateTimeRemaining(double turns) {
        // Set convert turns to inverted value and set progress bar.
        double percentageDbl = PROGRESS_BAR_MAX - (turns * 100);
        timeBar.setValue((int)(percentageDbl));

        // Convert to percentage of bar and set colors.
        percentageDbl = percentageDbl / PROGRESS_BAR_MAX * 100;

        if (percentageDbl <= 60.0 && percentageDbl > 30.0) {
            timeBar.setForeground(Color.yellow);
        }
        else if (percentageDbl <= 30.0) {
            timeBar.setForeground(Color.red);
            timeBar.setUI(new BasicProgressBarUI() {
                protected Color getSelectionBackground() { return Color.red; }
                protected Color getSelectionForeground() { return Color.white; }
            });
        }
    }
}