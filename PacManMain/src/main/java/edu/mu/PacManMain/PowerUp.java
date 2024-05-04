package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PowerUp {
    private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    private Timer powerUpTimer;
    private int remainingTime = 0; // Initialize remainingTime
   
    private ScorePanel sPanel;

    
    public PowerUp(Maze maze, JPanel mazePanel, PacMan pacMan) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.pacMan = pacMan;
    }
    
    public void eatPowerUp(int row, int col) {
        if (maze.getMapGrid()[row][col] == 3) { // Check if Pacman's position corresponds to a power-up
            maze.getMapGrid()[row][col] = 0; // Remove the power-up from the maze grid
            pacMan.incrementScore(500); // Increment Pac-Man's score
//            sPanel.increaseScore(500);
            pacMan.setPowerUpActive(true); // Set the power-up active flag to true
            
            if (powerUpTimer == null) { // If no timer is running, start a new one
                startPowerUpTimer();
            } else { // If a timer is running, add the remaining time
                powerUpTimer.cancel(); // Cancel the current timer
                startPowerUpTimer(); // Start a new timer with the remaining time
            }
            
            // Update the appearance of the corresponding JLabel in the maze panel to represent an empty space
            Component[] components = mazePanel.getComponents();
            int cellIndex = row * maze.getMapGrid()[0].length + col;
            if (cellIndex >= 0 && cellIndex < components.length) {
                JLabel cellLabel = (JLabel) components[cellIndex];
                cellLabel.setBackground(Color.BLACK);
                cellLabel.setIcon(null); // Clear any existing icon
            }

        }
    }
    private void startPowerUpTimer() {
        pacMan.setPowerUpActive(true); // Set the power-up active flag to true

        powerUpTimer = new Timer();
        powerUpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    System.out.println("Power-up countdown: " + remainingTime + " seconds");
                    remainingTime--;
                } else {
                    pacMan.setPowerUpActive(false); // Set the power-up active flag to false after 5 seconds
                    System.out.println("Power-up expired!");
                    powerUpTimer.cancel(); // Cancel the timer task
                    powerUpTimer = null; // Set the timer reference to null
                }
            }
        }, 0, 1000); // Schedule the task to run every 1 second (1000 milliseconds)

        remainingTime = 5; // Set initial remaining time to 5 seconds
    }

    // Method to cancel the power-up timer
    public void cancelPowerUpTimer() {
        if (powerUpTimer != null) {
            powerUpTimer.cancel(); // Cancel the power-up timer if it's running
            powerUpTimer = null; // Set the timer reference to null
        }
    }
}
