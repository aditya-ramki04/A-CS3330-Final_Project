package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pellet {

    private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    
     /**
     * Constructs a Pellet object with the specified maze, maze panel, and Pac-Man character.
     *
     * @param maze      The maze grid containing information about the game layout.
     * @param mazePanel The panel representing the maze in the user interface.
     * @param pacMan    The Pac-Man character interacting with the pellets.
     */
    public Pellet (Maze maze, JPanel mazePanel, PacMan pacMan) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.pacMan = pacMan;
    }
    
    /**
     * Attempts to consume a pellet at the specified row and column.
     * If a pellet is present at the specified location, it is consumed,
     * and the Pac-Man's score is incremented by 100 points.
     *
     * @param row The row index of the pellet.
     * @param col The column index of the pellet.
     */
    public void eatPellet(int row, int col) {
        if (maze.getMapGrid()[row][col] == 2) 
        { 
            maze.getMapGrid()[row][col] = 0; 
            pacMan.incrementScore(100);
            Component[] components = mazePanel.getComponents();
            int cellIndex = row * maze.getMapGrid()[0].length + col;
            if (cellIndex >= 0 && cellIndex < components.length) {
                JLabel cellLabel = (JLabel) components[cellIndex];
                cellLabel.setBackground(Color.BLACK);
                cellLabel.setIcon(null); 
            }

            
        }
    }
}

