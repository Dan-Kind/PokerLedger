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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SettingsPanel extends JPanel {
    private JButton newGameButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton changeButton;
    private LedgerPanel ledgerPanel;
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
    
}
