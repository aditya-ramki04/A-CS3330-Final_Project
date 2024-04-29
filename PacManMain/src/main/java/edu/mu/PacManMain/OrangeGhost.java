package edu.mu.PacManMain;

import javax.swing.JLabel;

public class OrangeGhost extends Ghost {
	
	private static final String ORANGE_GHOST_IMAGE = "images/orangeghost.png";
	

    public OrangeGhost(Maze maze, int cellSize) {
        super(ORANGE_GHOST_IMAGE, maze, cellSize);
    }
    
    
    public void setPosition(int x, int y) {
        // Implement the setPosition method specific to CyanGhost
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 50, 50);
    }
}
