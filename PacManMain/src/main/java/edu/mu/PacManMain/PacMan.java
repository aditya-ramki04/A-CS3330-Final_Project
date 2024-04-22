package edu.mu.PacManMain;

public class PacMan {
    
    private int x;
    private int y;

    public PacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }
    
    public void movePac(int changeX, int changeY, Maze maze) {
        int newX = x + changeX;
        int newY = y + changeY;


    // Check for maze boundaries and wrap around if necessary
    // Corrected the maze boundary conditions according to the grid dimensions
    int width = maze.getGrid()[0].length;
    int height = maze.getGrid().length;

    if (newX < 0) {
        newX = width - 1;  // Wrap to the right side of the maze
    } else if (newX >= width) {
        newX = 0;  // Wrap to the left side of the maze
    }

    if (newY < 0) {
        newY = height - 1;  // Wrap to the bottom of the maze
    } else if (newY >= height) {
        newY = 0;  // Wrap to the top of the maze
    }

    // Checking if the new position is a wall
    if (maze.getTile(newX, newY) != 1) {  // Assuming 1 represents walls
        x = newX;
        y = newY;
    }
}
//    private void processMove(int x, int y) {
//        int currentTile = maze.getTile(x, y);
//
//        switch (currentTile) {
//            case 2: // Normal pellet
//                ScoreTracking.eatDot();
//                maze.setTile(x, y, 0); // Set the tile to empty after eating the pellet
//                break;
//            case 3: // Power pellet
//                scoreTracker.eatPowerPellet();
//                maze.setTile(x, y, 0); // Set the tile to empty after eating the power pellet
//                break;
//        }
//        updateGameDisplay();
//    }

public int getX() {
    return x;
}

public int getY() {
    return y;
}
}

