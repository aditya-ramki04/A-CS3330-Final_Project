package edu.mu.PacManMain;

/**
 * Represents a cyan-colored ghost in the Pac-Man game.
 */
public class CyanGhost extends Ghost {
	
	private static final String CYAN_GHOST_IMAGE = "images/blueghost.png";
	
	/**
	 * Initializes a CyanGhost object with the specified maze and cell size.
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public CyanGhost(Maze maze, int cellSize) {
        super(CYAN_GHOST_IMAGE, maze, cellSize);
    }
    
    /**
     * Sets the position of the cyan ghost.
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