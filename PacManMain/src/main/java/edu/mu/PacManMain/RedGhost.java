package edu.mu.PacManMain;

import javax.swing.JLabel;

public class RedGhost extends Ghost {
	private static final String RED_GHOST_IMAGE = "images/redghost.png";
		
	/**
	 * 
	 * @param maze
	 * @param cellSize
	 */
    public RedGhost(Maze maze, int cellSize) {
        super(RED_GHOST_IMAGE, maze, cellSize);
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
