package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PowerUp {
    private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    
    public PowerUp(Maze maze, JPanel mazePanel, PacMan pacMan) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.pacMan = pacMan;
    }
    
    public void eatPowerUp(int row, int col) {
        if (maze.getMapGrid()[row][col] == 3) { // Check if Pacman's position corresponds to a power-up
            maze.getMapGrid()[row][col] = 0; // Remove the power-up from the maze grid
            pacMan.incrementScore(500); // Increment Pac-Man's score
            pacMan.setPowerUpActive(true); // Set the power-up active flag to true
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
}
