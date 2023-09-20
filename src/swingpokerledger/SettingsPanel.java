/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

/**
 *
 * @author DanKind
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SettingsPanel extends JPanel {
    private JButton newGameButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton changeButton;
    private LedgerPanel ledgerPanel;
    private JPanel playerListPanel;
    private JSpinner incrementSpinner; // Added number spinner
    private static SettingsPanel instance; // Singleton instance
    public int incrementSize;
    
    private String[] currencies = {"$", "€", "£", "¥", "₹", "kr"};
    public static String currencySymbol = "kr";

    public SettingsPanel(LedgerPanel ledgerPanel) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Create buttons
        newGameButton = new JButton("New Game");
        saveButton = new JButton("Save player info");
        loadButton = new JButton("Load player info");
        changeButton = new JButton("Change player info");
        // Create number spinner with default value and range
        incrementSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
        
        // Create ComboBox for currency selection
        JComboBox<String> currencyComboBox = new JComboBox<>(currencies);
        
        // Add buttons and spinner to the panel
        add(newGameButton);
        add(saveButton);
        add(loadButton);
        add(changeButton);
        add(new JLabel("Increment Size:"));
        add(incrementSpinner);
        add(new JLabel("Currency:"));
        add(currencyComboBox); // Add the currency selector ComboBox
        
        // Add the ledgerPanel reference to instance
        this.ledgerPanel = ledgerPanel;
        
        // Initialize the increment size to the default value
        incrementSize = (int) incrementSpinner.getValue();
        
        playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        JScrollPane playerScrollPane = new JScrollPane(playerListPanel);
        playerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        playerScrollPane.setPreferredSize(new Dimension(300, 200));
        

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerList(); // Update the player list when the button is clicked
                showPopup(playerScrollPane);
            }

            private void showPopup(JScrollPane playerScrollPane) {
                // Create a JDialog as the popup window
                JDialog popup = new JDialog();
                popup.setTitle("Player List");
                popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
                // Add the JScrollPane containing the player list panel to the popup
                popup.getContentPane().add(playerScrollPane);

                // Set the size of the popup
                popup.setSize(new Dimension(1000, 600));

                // Center the popup window on the screen
                popup.setLocationRelativeTo(null);

                // Make the popup visible
                popup.setVisible(true);
            }
        });
        // Add action listeners to the buttons
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(GamePanel.done){
                    // Create a new GamePanel
                    GamePanel gamePanel = new GamePanel();

                    // Add the new GamePanel to LedgerPanel
                    ledgerPanel.addGamePanel(gamePanel);
                } else{
                    JOptionPane.showMessageDialog(null, "Current game isn't finished", "Game Incomplete", JOptionPane.WARNING_MESSAGE);
                }
                
            }
        });
        
        // Add action listener to the spinner to update increment size
        incrementSpinner.addChangeListener(e -> {
            incrementSize = (int) incrementSpinner.getValue();
        });

        saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.savePlayers("players.ser");
            JOptionPane.showMessageDialog(null, "Player data saved successfully!", "Save Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    });

    loadButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           // Call the Player.loadPlayers method to load player data
            Player.loadPlayers("players.ser");
            // After loading, you may want to update your player list in the UI.
            // This depends on your UI implementation.
            SummaryPanel.updateSummary();
            JOptionPane.showMessageDialog(null, "Player data loaded successfully!", "Load Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    });

        this.instance = this;
    }

    // Singleton pattern: Get the instance of SettingsPanel
    public static SettingsPanel getInstance() {
        return instance;
    }

    public int getIncrementSize() {
        return incrementSize;
    }
    // Method to update the player list panel
    private void updatePlayerList() {
        playerListPanel.removeAll(); // Clear the existing list

        // Create a panel for the "Clear all debt" button
        JPanel clearPanel = new JPanel();
        JButton clearAllDebtButton = new JButton("Clear all debt");
        JButton clearAllWinsButton = new JButton("Clear all wins");
        clearAllDebtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code here to clear all debts for all players
                for (Player player : Player.getPlayerList()) {
                    player.setTotalDebt(0);
                }
                Player.savePlayers("players.ser");
                SummaryPanel.updateSummary();
                
            }
        });
        clearAllWinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code here to clear all debts for all players
                // You'll need to iterate through your player list and set the debt to 0 for each player
                for (Player player : Player.getPlayerList()) {
                    player.setWins(0);
                }
                Player.savePlayers("players.ser");
                SummaryPanel.updateSummary();
            }
        });
        
        
        clearPanel.add(clearAllDebtButton);
        clearPanel.add(clearAllWinsButton);
        playerListPanel.add(clearPanel); // Add the clear debt button panel at the top

        // Iterate through the list of players and add their information
        for (Player player : Player.getPlayerList()) {
            JPanel playerInfoPanel = new JPanel();
            playerInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Create editable text fields for name, phone number, and debt
            JTextField nameField = new JTextField(player.getName(), 20);
            JTextField phoneNumberField = new JTextField(player.getPhoneNumber(), 20);
            JTextField debtField = new JTextField(String.valueOf(player.getTotalDebt()), 10);

            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update the player's information based on the edited fields
                    player.setName(nameField.getText());
                    player.setPhoneNumber(phoneNumberField.getText());
                    try {
                        int newDebt = Integer.parseInt(debtField.getText());
                        player.setTotalDebt(newDebt);
                    } catch (NumberFormatException ex) {
                        // Handle invalid input (e.g., non-integer value in debt field)
                    }
                    Player.savePlayers("players.ser");
                    SummaryPanel.updateSummary();
                }
            });

            playerInfoPanel.add(new JLabel("Name:"));
            playerInfoPanel.add(nameField);
            playerInfoPanel.add(new JLabel("Phone Number:"));
            playerInfoPanel.add(phoneNumberField);
            playerInfoPanel.add(new JLabel("Debt:"));
            playerInfoPanel.add(debtField);
            playerInfoPanel.add(saveButton);

            playerListPanel.add(playerInfoPanel); // Add the player info panel to the list
        }

        revalidate(); // Refresh the panel
        repaint();
    }



}
