package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Food {

	private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    
	public Food(Maze maze, JPanel mazePanel, PacMan pacMan) {
		this.maze = maze;
		this.mazePanel = mazePanel;
		this.pacMan = pacMan;
	}
	
	public void eatCherryBonus(int row, int col) {
        
        if (maze.getMapGrid()[row][col] == 4) { 
            maze.getMapGrid()[row][col] = 0; 
            System.out.println("Cherry Bonus eaten at row: " + row + ", col: " + col);
            pacMan.incrementScore(1000);
            
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
