/*
 * HelloWorld
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swingpokerledger;

/**
 *
 * @author DanKind
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable{
    private int totalDebt;
    private int currentDebt;
    private int matches;
    private int wins;
    private String name;
    private String phoneNumber;

    // A list to store all players
    private static List<Player> playerList = new ArrayList<>();

    public Player(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.totalDebt = 0;
        this.currentDebt = 0;
        this.matches = 0;
        this.wins = 0;
        playerList.add(this);
    }
    
     public static void savePlayers(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            // Serialize the playerList to a file
            out.writeObject(playerList);
            System.out.println("Player data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save player data.");
        }
    }

    // Add a method to load player data
    public static void loadPlayers(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // Deserialize the playerList from the file
            List<Player> loadedPlayers = (List<Player>) in.readObject();
            playerList.clear();
            playerList.addAll(loadedPlayers);
            System.out.println("Player data loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to load player data.");
        }
    }
    
    // Getters and setters for player attributes
    public int getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(int totalDebt) {
        this.totalDebt = totalDebt;
    }

    public int getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(int currentDebt) {
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