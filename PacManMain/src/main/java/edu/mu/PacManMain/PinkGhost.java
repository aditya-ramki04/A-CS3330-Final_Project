package edu.mu.PacManMain;

/**
 * Represents a pink-colored ghost in the Pac-Man game.
 */
public class PinkGhost extends Ghost {

	private static final String PINK_GHOST_IMAGE = "images/pinkghost.png";
	
	/**
	 * Initializes a PinkGhost object with the specified maze and cell size.
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public PinkGhost(Maze maze, int cellSize) {
        super(PINK_GHOST_IMAGE, maze, cellSize);
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
