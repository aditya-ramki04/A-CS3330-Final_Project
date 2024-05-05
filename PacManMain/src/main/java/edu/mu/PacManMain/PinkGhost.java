package edu.mu.PacManMain;

public class PinkGhost extends Ghost {

	private static final String PINK_GHOST_IMAGE = "images/pinkghost.png";
	
	/**
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public PinkGhost(Maze maze, int cellSize) {
        super(PINK_GHOST_IMAGE, maze, cellSize);
    }
    
    /**
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
