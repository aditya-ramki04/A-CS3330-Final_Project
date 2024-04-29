package edu.mu.PacManMain;

public class Maze {
    private int[][] mapGrid;

    public Maze(int[][] mapGrid) {
        this.mapGrid = mapGrid;
    }

    public boolean isValidMove(int x, int y) {
        int cellX = (int) (x / 38.09); // Convert pixel position to grid cell
        int cellY = (int) (y / 38.09);

        // Check if Pacman is within bounds of the maze
        if (cellX >= 0 && cellX < mapGrid[0].length && cellY >= 0 && cellY < mapGrid.length) {
            int cellValue = mapGrid[cellY][cellX];
            return cellValue != 1 && cellValue != 10 && cellValue != 11 && cellValue != 12 &&
                    cellValue != 13 && cellValue != 14 && cellValue != 15 && cellValue != 16; // Assuming 1 represents a wall
        }
        return false;
    }

    public int[][] getMapGrid() {
        return mapGrid;
    }
}
