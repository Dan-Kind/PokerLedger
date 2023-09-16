/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

import javax.swing.JPanel;

/**
 *
 * @author DanKind
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

class SummaryPanel extends JPanel {
    private static JTextArea topWinnersTextArea;
    private static JTextArea playerDebtsTextArea;
    private JButton repayButton; // Add the "Repay" button
    public static SummaryPanel instance; // Singleton
    public SummaryPanel() {
        setLayout(new GridLayout(1, 3)); // Use a 1x2 grid layout

        // Create and initialize text areas
        topWinnersTextArea = new JTextArea("Top Winners:\n", 10, 20);
        topWinnersTextArea.setEditable(false); // Make it read-only
        JScrollPane topWinnersScrollPane = new JScrollPane(topWinnersTextArea);

        playerDebtsTextArea = new JTextArea("Player Debts:\n", 10, 20);
        playerDebtsTextArea.setEditable(false); // Make it read-only
        JScrollPane playerDebtsScrollPane = new JScrollPane(playerDebtsTextArea);
        
         repayButton = new JButton("Repay");
        repayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        // Add the "Repay" button to the panel
        
        // Add the scroll panes to the summary panel
        add(topWinnersScrollPane);
        add(playerDebtsScrollPane);
        //add(repayButton);
        this.instance = this;
    }

    // Update the summary panel with the top winners and player debts
    public static void updateSummary() {
        updateTopWinners();
        updatePlayerDebts();
    }

    private static void updateTopWinners() {
        // Get the list of players sorted by wins (descending order)
        List<Player> sortedPlayers = new ArrayList<>(Player.getPlayerList());
        sortedPlayers.sort(Comparator.comparingInt(Player::getWins).reversed());

        // Display the top winners in the text area
        StringBuilder topWinnersText = new StringBuilder("Top Winners:\n");
        int topCount = Math.min(5, sortedPlayers.size()); // Show the top 5 winners
        for (int i = 0; i < topCount; i++) {
            Player player = sortedPlayers.get(i);
            topWinnersText.append(player.getName()).append(": ").append(player.getWins()).append(" wins\n");
        }
        topWinnersTextArea.setText(topWinnersText.toString());
    }

    private static void updatePlayerDebts() {
        // Calculate and display player debts in the text area
        StringBuilder playerDebtsText = new StringBuilder("Player Debts:\n");
        for (Player player : Player.getPlayerList()) {
            playerDebtsText.append(player.getName()).append(": ").append(player.getTotalDebt()).append(SettingsPanel.currencySymbol).append("\n");
        }
        playerDebtsTextArea.setText(playerDebtsText.toString());
    }
}
