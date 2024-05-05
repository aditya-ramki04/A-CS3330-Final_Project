package edu.mu.PacManMain;

import javax.swing.JLabel;

public class OrangeGhost extends Ghost {
	
	private static final String ORANGE_GHOST_IMAGE = "images/orangeghost.png";
	
	/**
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public OrangeGhost(Maze maze, int cellSize) {
        super(ORANGE_GHOST_IMAGE, maze, cellSize);
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
