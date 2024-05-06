package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Food {

	private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    
    /**
     * Constructs a Food object with the specified maze, maze panel, and Pac-Man character.
     *
     * @param maze      The maze grid containing information about the game layout.
     * @param mazePanel The panel representing the maze in the user interface.
     * @param pacMan    The Pac-Man character interacting with the food items.
     */
	public Food(Maze maze, JPanel mazePanel, PacMan pacMan) {
		this.maze = maze;
		this.mazePanel = mazePanel;
		this.pacMan = pacMan;
	}
	
	 /**
     * Attempts to consume a cherry bonus at the specified row and column.
     * If a cherry bonus is present at the specified location, it is consumed,
     * and the Pac-Man's score is incremented by 1000 points.
     *
     * @param row The row index of the cherry bonus.
     * @param col The column index of the cherry bonus.
     */
	public void eatCherryBonus(int row, int col) {
        
        if (maze.getMapGrid()[row][col] == 4) { 
            maze.getMapGrid()[row][col] = 0; 
            System.out.println("Cherry Bonus eaten");
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
