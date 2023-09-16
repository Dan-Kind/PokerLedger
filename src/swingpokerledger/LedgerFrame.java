/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import swingpokerledger.LedgerPanel;
/**
 *
 * @author DanKind
 */
public class LedgerFrame extends JFrame{
    private SettingsPanel settingsPanel;
    private LedgerPanel ledgerPanel;
    private SummaryPanel summaryPanel;
    public LedgerFrame(){
        //Setting up the Ledger frame
        this.setTitle("Ledger");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        // Create and add the game panel scroll pane to the middle
        this.ledgerPanel = new LedgerPanel();
        this.add(ledgerPanel, BorderLayout.CENTER);

        // Create and add the settings panel to the top
        this.settingsPanel = new SettingsPanel(ledgerPanel);
        this.add(settingsPanel, BorderLayout.NORTH);

        // Create and add the summary panel to the bottom
        this.summaryPanel = new SummaryPanel();
        this.add(summaryPanel, BorderLayout.SOUTH);
        
        //Show Ledger Frame
        setSize(800, 600);

            // Center the JFrame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) (screenSize.getWidth() - this.getWidth()) / 2;
        int centerY = (int) (screenSize.getHeight() - this.getHeight()) / 2;
        setLocation(centerX, centerY);
        //pack();
        this.setVisible(true);
    }
    
}
