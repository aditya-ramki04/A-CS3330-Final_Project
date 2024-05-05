package edu.mu.PacManMain;

public class Maze {
    private int[][] mapGrid;

    /**
     * Constructs a new Maze object with the specified grid layout.
     *
     * @param mapGrid The 2D array representing the grid layout of the maze.
     */
    public Maze(int[][] mapGrid) {
        this.mapGrid = mapGrid;
    }

    /**
     * Checks if the given coordinates represent a valid move within the maze.
     * A move is considered valid if it falls within the bounds of the maze and does not collide with a wall.
     *
     * @param x The X coordinate of the move.
     * @param y The Y coordinate of the move.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(int x, int y) {
        int cellX = (int) (x / 38.09); 
        int cellY = (int) (y / 38.09);

      
        if (cellX >= 0 && cellX < mapGrid[0].length && cellY >= 0 && cellY < mapGrid.length) {
            int cellValue = mapGrid[cellY][cellX];
            return cellValue != 1 && cellValue != 10 && cellValue != 11 && cellValue != 12 &&
                    cellValue != 13 && cellValue != 14 && cellValue != 15 && cellValue != 16; 
        }
        return false;
    }

    /**
     * Gets the grid layout of the maze.
     *
     * @return The 2D array representing the grid layout of the maze.
     */
    public int[][] getMapGrid() {
        return mapGrid;
    }
}
