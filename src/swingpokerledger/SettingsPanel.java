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
    private LedgerPanel ledgerPanel;

    public SettingsPanel(LedgerPanel ledgerPanel) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adjust layout and spacing

        // Create buttons
        newGameButton = new JButton("New Game");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        
        // Add buttons to the panel
        add(newGameButton);
        add(saveButton);
        add(loadButton);
        // Add the ledgerPanel refernce to instance
        this.ledgerPanel = ledgerPanel;
        // Add action listeners to the buttons
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new GamePanel
                GamePanel gamePanel = new GamePanel();
                
                // Add the new GamePanel to LedgerPanel
                ledgerPanel.addGamePanel(gamePanel);

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for saving the data to a file
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for loading data from a file
            }
        });
    }
}