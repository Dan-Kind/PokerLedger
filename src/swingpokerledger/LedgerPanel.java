/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author DanKind
 */
public class LedgerPanel extends JPanel{
    // Private static instance of the class
    private static LedgerPanel instance;
    private List<GamePanel> gamePanels;
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;
    public static List<Player> recentPlayers;
     // Private constructor to prevent external instantiation
    public LedgerPanel() {
        // Initialize the JScrollPane as needed
        gamePanels = new ArrayList<>();
        scrollPane = new JScrollPane();
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new GridLayout(0,1));
        scrollPane.setViewportView(contentPanel);
        
         // Set preferred size and visibility for scrollPane
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setVisible(true);

        // Set scrolling policies
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);
        // Add a MouseWheelListener to the JScrollPane
            scrollPane.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    // Adjust the scroll speed by multiplying the delta by a factor
                    int notches = e.getWheelRotation();
                    int scrollSpeed = 25; // Adjust this value to control the scroll speed
                    scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + notches * scrollSpeed);
                }
            });
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(800, 600)); // Set preferred size
        setBackground(Color.GRAY);
        setVisible(true); // Make the panel visible
        
        
    }
    
    public void addGamePanel(GamePanel gamePanel) {
        gamePanels.add(gamePanel); // Add the GamePanel to the existing list
        refreshScrollPane();
        System.out.println("ADDED GAME PANEL");
        System.out.println(gamePanels.size());
    }
    
    public void refreshScrollPane() { 
        contentPanel.removeAll(); // Clear the existing content from contentPanel

        for (GamePanel gamePanel : gamePanels) {
            contentPanel.add(gamePanel);
        }

        contentPanel.revalidate();
        repaint();
    }

    // Method to get the singleton instance
    public static LedgerPanel getInstance() {
        if (instance == null) {
            instance = new LedgerPanel();
        }
        return instance;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
