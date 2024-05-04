package edu.mu.PacManMain;

import javax.swing.JLabel;

public class RedGhost extends Ghost {
	private static final String RED_GHOST_IMAGE = "images/redghost.png";
		
    public RedGhost(Maze maze, int cellSize) {
        super(RED_GHOST_IMAGE, maze, cellSize);
    }
    
  
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 50, 50);
    }
}
