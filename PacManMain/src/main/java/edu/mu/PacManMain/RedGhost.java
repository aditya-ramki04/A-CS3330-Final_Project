package edu.mu.PacManMain;

import javax.swing.JLabel;

/**
 * Represents a red-colored ghost in the Pac-Man game.
 */
public class RedGhost extends Ghost {
	private static final String RED_GHOST_IMAGE = "images/redghost.png";
		
	/**
	 * Initializes a RedGhost object with the specified maze and cell size.
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public RedGhost(Maze maze, int cellSize) {
        super(RED_GHOST_IMAGE, maze, cellSize);
    }
    
    /**
     * Sets the position of the pink ghost.
     * 
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 50, 50);
    }
}
