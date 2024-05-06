package edu.mu.PacManMain;

import javax.swing.JLabel;

/**
 * Represents a orange-colored ghost in the Pac-Man game.
 */
public class OrangeGhost extends Ghost {
	
	private static final String ORANGE_GHOST_IMAGE = "images/orangeghost.png";
	
	/**
	 * Initializes an OrangeGhost object with the specified maze and cell size.
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public OrangeGhost(Maze maze, int cellSize) {
        super(ORANGE_GHOST_IMAGE, maze, cellSize);
    }
    
    /**
     * Sets the position of the orange ghost.
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
