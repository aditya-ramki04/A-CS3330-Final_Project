package edu.mu.PacManMain;

public class CyanGhost extends Ghost {
	
	private static final String CYAN_GHOST_IMAGE = "images/blueghost.png";
	
	/**
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public CyanGhost(Maze maze, int cellSize) {
        super(CYAN_GHOST_IMAGE, maze, cellSize);
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