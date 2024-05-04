package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pellet {

    private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    
    private ScorePanel sPanel;


    public Pellet (Maze maze, JPanel mazePanel, PacMan pacMan) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.pacMan = pacMan;
    }
    
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

