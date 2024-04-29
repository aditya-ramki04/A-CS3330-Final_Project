package edu.mu.PacManMain;

public class CyanGhost extends Ghost {
	
	private static final String CYAN_GHOST_IMAGE = "images/blueghost.png";
	
	
    public CyanGhost(Maze maze, int cellSize) {
        super(CYAN_GHOST_IMAGE, maze, cellSize);
    }
    

    public void setPosition(int x, int y) {
        // Implement the setPosition method specific to CyanGhost
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 50, 50);
    }
}