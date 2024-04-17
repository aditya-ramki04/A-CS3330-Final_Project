package edu.mu.PacManMain;

public class GhostHouse {
    private Maze maze;
    private int ghostStartX = 12; // Adjust to center the ghost house appropriately
    private int ghostStartY = 10;
    private int ghostHouseWidth = 3;
    private int ghostHouseHeight = 2;

    public GhostHouse(Maze maze) {
        this.maze = maze;
        initializeGhosts();
    }

    private void initializeGhosts() {
        // Set initial positions of ghosts in the ghost house
        for (int y = ghostStartY; y < ghostStartY + ghostHouseHeight; y++) {
            for (int x = ghostStartX; x < ghostStartX + ghostHouseWidth; x++) {
                maze.setTile(x, y, 10); // Assuming '10' is a ghost
            }
        }
    }

    public void rotateGhosts() {
        // Rotate ghosts clockwise within the 3x2 section
        int temp = maze.getGrid()[ghostStartY][ghostStartX];
        maze.setTile(ghostStartX, ghostStartY, maze.getGrid()[ghostStartY + 1][ghostStartX]);
        maze.setTile(ghostStartX, ghostStartY + 1, maze.getGrid()[ghostStartY + 1][ghostStartX + 1]);
        maze.setTile(ghostStartX + 1, ghostStartY + 1, maze.getGrid()[ghostStartY + 1][ghostStartX + 2]);
        maze.setTile(ghostStartX + 2, ghostStartY + 1, maze.getGrid()[ghostStartY][ghostStartX + 2]);
        maze.setTile(ghostStartX + 2, ghostStartY, maze.getGrid()[ghostStartY][ghostStartX + 1]);
        maze.setTile(ghostStartX + 1, ghostStartY, temp);
    }

    public void releaseGhost() {
        // Release a ghost from the center of the top row of the ghost house
        maze.setTile(ghostStartX + 1, ghostStartY - 1, maze.getGrid()[ghostStartY][ghostStartX + 1]);
        maze.setTile(ghostStartX + 1, ghostStartY, 0); // Clear the position
    }
}
