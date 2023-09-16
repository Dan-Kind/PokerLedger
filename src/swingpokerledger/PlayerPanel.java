/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swingpokerledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel {
    private Player player;
    private JSpinner debtSpinner;
    private JButton plusButton;
    private JButton minusButton;

    public PlayerPanel(Player player) {
        this.player = player;

        setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Name: " + player.getName());

        // Create a panel for debt-related components
        JPanel debtPanel = new JPanel();
        debtSpinner = new JSpinner(new SpinnerNumberModel(player.getCurrentDebt(), 0, Integer.MAX_VALUE, 1));
        plusButton = new JButton("+");
        minusButton = new JButton("-");

        // Add action listeners to plus and minus buttons
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incrementDebt();
            }
        });

        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrementDebt();
            }
        });

        debtPanel.add(debtSpinner);
        debtPanel.add(plusButton);
        debtPanel.add(minusButton);

        add(nameLabel, BorderLayout.WEST);
        add(debtPanel, BorderLayout.CENTER);
    }

    public void updateDebtField() {
        // Update the debt spinner value based on the player's current debt
        debtSpinner.setValue(player.getCurrentDebt());
    }

    private void incrementDebt() {
        // Implement the logic to increment the player's debt
        int currentDebt = (int) debtSpinner.getValue();
        int newDebt = currentDebt + SettingsPanel.getInstance().getIncrementSize(); // For example, increment debt by 10
        debtSpinner.setValue(newDebt);
        player.setCurrentDebt(newDebt);
    }

    private void decrementDebt() {
        // Implement the logic to decrement the player's debt
        int currentDebt = (int) debtSpinner.getValue();
        int newDebt = currentDebt - SettingsPanel.getInstance().getIncrementSize(); // For example, decrement debt by 10
        if (newDebt < 0) {
            newDebt = 0; // Ensure debt doesn't go below 0
        }
        debtSpinner.setValue(newDebt);
        player.setCurrentDebt(newDebt);
    }

    Player getPlayer() {
        return player;
    }
}
