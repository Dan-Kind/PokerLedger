/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

/**
 *
 * @author DanKind
 */
import java.util.ArrayList;
import java.util.List;

public class Player {
    private double totalDebt;
    private double currentDebt;
    private int matches;
    private int wins;
    private String name;
    private String phoneNumber;

    // A list to store all players
    private static List<Player> playerList = new ArrayList<>();

    public Player(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.totalDebt = 0.0;
        this.currentDebt = 0.0;
        this.matches = 0;
        this.wins = 0;
        playerList.add(this);
    }

    Player(String playerName, double debt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters and setters for player attributes
    public double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Get the list of all players
    public static List<Player> getPlayerList() {
        return playerList;
    }
}