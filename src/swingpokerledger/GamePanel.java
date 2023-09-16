/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author DanKind
 */
public class GamePanel extends JPanel {
    private List<Player> players;
    private JComboBox<Player> playerComboBox;
    private JButton addPlayerButton;
    private JButton chooseWinnerButton;
    private JPanel playerContainer;
    private JLabel instanceLabel;
    private JPanel outcomePanel; // Panel to display game outcomes
    private JPanel innerPanel;
    public static boolean done = true;
    public static int instanceNumber;
    public GamePanel() {
        done = false;
        instanceNumber ++;
        players = new ArrayList<>();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerComboBox = new JComboBox<>();
        playerComboBox.setRenderer(new PlayerComboBoxRenderer());
        addPlayerButton = new JButton("Add Player");
        JButton addRecentPlayersButton = new JButton("Add Recent Players");
        JButton removePlayerButton = new JButton("Remove Player");
        chooseWinnerButton = new JButton("Choose Winner");
        this.instanceLabel = new JLabel("Game " + instanceNumber);
        
         // Create an outer panel with a black border
        JPanel outerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        
        outerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        outerPanel.setBackground(Color.WHITE); // Set the outer panel background to white
        
        
        // Create the inner panel with a white background
        this.innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(Color.WHITE); // Set the inner panel background to white
        
        this.playerContainer = new JPanel();
        this.playerContainer.setLayout(new BoxLayout(playerContainer, BoxLayout.Y_AXIS));
        
        
        topPanel.add(instanceLabel);
        topPanel.add(playerComboBox);
        topPanel.add(addPlayerButton);
        topPanel.add(chooseWinnerButton);
        topPanel.add(addRecentPlayersButton);
        topPanel.add(removePlayerButton);
        innerPanel.add(topPanel);
        innerPanel.add(playerContainer);
        outerPanel.add(innerPanel);
        
        add(outerPanel);
        
        // Create a scrollable container for player panels
        
        
        
        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlayer();
            }
        });

        addRecentPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            List<Player> recentPlayers = LedgerPanel.recentPlayers;
                if (recentPlayers != null && !recentPlayers.isEmpty()) {
                    
                    // Clear the existing items in the playerComboBox
                    
                    // Add the recent players to the playerComboBox
                    for (Player player : recentPlayers) {
                        if(!players.contains(player)){
                            players.add(player);
                            playerComboBox.addItem(player);
                            playerContainer.add(new PlayerPanel(player));
                        }
                        
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No recent players found", "No Players", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewPlayer();
            }
        });

        chooseWinnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseWinner();
            }
        });
        
        //Init the outcome panel
        outcomePanel = new JPanel();
        
        //outcomePanel.setPreferredSize(new Dimension(300, 100)); // Adjust size as needed
        outcomePanel.setVisible(false); // Initially hidden
        innerPanel.add(outcomePanel);
        
    }
    public void removePlayer() {
        if (players.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No players to choose from.");
            return;
        }

        Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
        players.remove(selectedPlayer);
        playerComboBox.removeItem(selectedPlayer);

        // Remove the player's panel from the playerContainer
        for (Component component : playerContainer.getComponents()) {
            if (component instanceof PlayerPanel) {
                PlayerPanel playerPanel = (PlayerPanel) component;
                if (playerPanel.getPlayer() == selectedPlayer) {
                    playerContainer.remove(playerPanel);
                    break;
                }
            }
        }
        selectedPlayer.setCurrentDebt(0);
        // Repaint the playerContainer to update the UI
        playerContainer.revalidate();
        playerContainer.repaint();
    }

    private void addNewPlayer() {
    // Create an array of button labels
    String[] options = {"Add New Player", "Add Existing Player"};

    // Show the option dialog with the custom buttons
    int choice = JOptionPane.showOptionDialog(
        this,
        "Choose an option:",
        "Add Player",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]
    );

    // Handle the user's choice
    switch (choice) {
        case 0:
            // Add a new player
            addNewPlayerDialog();
            break;
        case 1:
            // Add an existing player
            addExistingPlayerDialog();
            break;
        default:
            // User canceled the dialog
            break;
    }
}

    private void addNewPlayerDialog() {
        // Create a JPanel to hold input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        // Add labels and text fields for name and phone number
        JTextField nameField = new JTextField(15);
        JTextField phoneNumberField = new JTextField(15);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneNumberField);

        // Show the input dialog
        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter New Player Information", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String playerName = nameField.getText();
            String phoneNumber = phoneNumberField.getText();

            // Check if the player name is not empty
            if (!playerName.isEmpty()) {
                Player newPlayer = new Player(playerName, phoneNumber);
                players.add(newPlayer);
                playerComboBox.addItem(newPlayer);
                playerContainer.add(new PlayerPanel(newPlayer));
            }
        }
    }

    private void addExistingPlayerDialog() {
        // Create a combo box to select an existing player
        JComboBox<Player> playerSelectionComboBox = new JComboBox<>(Player.getPlayerList().toArray(new Player[0]));
        playerSelectionComboBox.setRenderer(new PlayerComboBoxRenderer());
        // Show the dialog to select an existing player
        int result = JOptionPane.showConfirmDialog(
            this,
            playerSelectionComboBox,
            "Select Existing Player",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            Player selectedPlayer = (Player) playerSelectionComboBox.getSelectedItem();
            if (selectedPlayer != null) {
                players.add(selectedPlayer);
                playerComboBox.addItem(selectedPlayer);
                playerContainer.add(new PlayerPanel(selectedPlayer));
            }
        }
    }

    
    


    private void chooseWinner() {
        if (players.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No players to choose from.");
        return;
        }

        Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
        selectedPlayer.setWins(selectedPlayer.getWins() + 1);
        
        displayGameOutcomes(selectedPlayer);
        
        //Update recent players
        LedgerPanel.recentPlayers = players;
        
        // Update player debts based on the winner
        updatePlayerDebts(selectedPlayer);
        
        // Call updateSummary to refresh the summary panel
        SummaryPanel summaryPanel = SummaryPanel.instance; // Replace with your actual reference
        summaryPanel.updateSummary();
        
        
        JOptionPane.showMessageDialog(this, selectedPlayer.getName() + " is the winner!");
        done = true;
    }
    
    private void updatePlayerDebts(Player winner) {
        int totalDebtChange = 0;

        // Calculate the total debt change (sum of current debts of all players)
        for (Player player : players) {
            if (player != winner) {
                totalDebtChange += player.getCurrentDebt();
            }
        }

        // Update the winner's debt
        winner.setTotalDebt(winner.getTotalDebt() + totalDebtChange );
        winner.setCurrentDebt(0);
        // Reset the current debts of all players to 0 (excluding the winner)
        for (Player player : players) {
            if (player != winner) {
                player.setTotalDebt(player.getTotalDebt() - player.getCurrentDebt());
                player.setCurrentDebt(0);
            }
        }
    }

    private void displayGameOutcomes(Player selectedPlayer) {
        // Remove all components inside the inner panel
        innerPanel.removeAll();

        // Loop through the players to display their debts
        for (Player player : players) {
            JLabel debtLabel = new JLabel(player.getName() + ": " + player.getCurrentDebt());

            // Highlight the selected player (the winner)
            if (player == selectedPlayer) {
                debtLabel.setForeground(Color.GREEN); // Set text color to green for the winner
            }

            outcomePanel.add(debtLabel);
        }

        // Make the outcome panel visible
        innerPanel.add(outcomePanel);
        outcomePanel.setVisible(true);

        // Repaint the inner panel to display the changes
        innerPanel.revalidate();
        innerPanel.repaint();
    }


    
    private class PlayerComboBoxRenderer extends JLabel implements ListCellRenderer<Player> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getName());
            } else {
                setText("");
            }

            return this;
        }
    }

}