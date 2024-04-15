package edu.mu.PacManMain;

public class PacMan {
    
    private int x;
    private int y;

    public PacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    
    public void movePac(Movement direction, GameBoard gboard) {
        int newX = x;
        int newY = y;

        // Handling movement direction with if statements
        if (direction == Movement.UP) {
            newY++;
        }
        if (direction == Movement.DOWN) {
            newY--;
        }
        if (direction == Movement.LEFT) {
            newX--;
        }
        if (direction == Movement.RIGHT) {
            newX++;
        }

        // Check for maze boundaries and wrap around if necessary
        if (newX < 0) {
            newX = gboard.getWidth() - 1;  // Assuming getWidth() returns the width of the board
        } else if (newX >= gboard.getWidth()) {
            newX = 0;  // Wrap to the opposite side
        }

        if (newY < 0) {
            newY = gboard.getHeight() - 1;  // Assuming getHeight() returns the height of the board
        } else if (newY >= gboard.getHeight()) {
            newY = 0;  // Wrap to the opposite side
        }

//        // Check if the new position is a wall or free to move
//        if (!gboard.isWall(newX, newY)) {
//            x = newX;
//            y = newY;
//        }
    }
}

