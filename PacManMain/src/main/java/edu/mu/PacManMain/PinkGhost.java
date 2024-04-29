package edu.mu.PacManMain;

public class PinkGhost extends Ghost {

	private static final String PINK_GHOST_IMAGE = "images/pinkghost.png";

    public PinkGhost(Maze maze, int cellSize) {
        super(PINK_GHOST_IMAGE, maze, cellSize);
    }
    
    
    public void setPosition(int x, int y) {
        // Implement the setPosition method specific to CyanGhost
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 50, 50);
    }
}
