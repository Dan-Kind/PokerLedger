/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DanKind
 */
public class GamePanel extends JPanel {
    private List<Player> players;
    private JComboBox<Player> playerComboBox;
    private JTextField debtField;
    private JButton addPlayerButton;
    private JButton chooseWinnerButton;

    public GamePanel() {
        players = new ArrayList<>();

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        playerComboBox = new JComboBox<>();
        debtField = new JTextField(10);
        addPlayerButton = new JButton("Add Player");
        chooseWinnerButton = new JButton("Choose Winner");

        add(playerComboBox);
        add(debtField);
        add(addPlayerButton);
        add(chooseWinnerButton);
        
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.GRAY);
        
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
    }

    private void addNewPlayer() {
        String playerName = playerComboBox.getSelectedItem().toString();
        double debt = Double.parseDouble(debtField.getText());

        Player newPlayer = new Player(playerName, debt);
        players.add(newPlayer);
        playerComboBox.addItem(newPlayer);
    }

    private void chooseWinner() {
        if (players.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No players to choose from.");
            return;
        }

        Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
        selectedPlayer.setWins(selectedPlayer.getWins() + 1);
        JOptionPane.showMessageDialog(this, selectedPlayer.getName() + " is the winner!");
    }
}