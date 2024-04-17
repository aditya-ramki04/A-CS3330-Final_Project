package edu.mu.PacManMain;

public class Maze {
	
	private int[][] mapGrid;
	
	public Maze() {
		//created a 26 by 26 array for the mapGrid
		//1 is for the wall, 2 is for the pellets, 3 is for the power up, and 0 is the empty space
		this.mapGrid = new int[][]
		{
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		    {1, 3, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 3, 1},
		    {1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1},
		    {1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1},
		    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		    {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
		    {1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 2, 1},
		    {1, 2, 2, 2, 2, 2, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 2, 2, 1},
		    {1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1},
		    {6, 2, 1, 2, 1, 1, 1, 2, 1, 5, 5, 5, 1, 2, 1, 1, 1, 1, 1, 2, 6},
		    {1, 2, 1, 2, 2, 2, 1, 2, 1, 5, 5, 5, 1, 2, 2, 2, 2, 2, 2, 2, 1},
		    {1, 2, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 1},
		    {1, 2, 1, 2, 1, 1, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 1, 2, 1},
		    {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
		    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		    {1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1},
		    {1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1},
		    {1, 3, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 3, 1},
		    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
	}
	public int[][] getGrid() {
        return mapGrid;
    }

    public int getTile(int x, int y) {
        if (x >= 0 && y >= 0 && x < mapGrid[0].length && y < mapGrid.length) {
            return mapGrid[y][x];
        } else {
            return -1; // Return -1 or another number indicating out-of-bounds
        }
    }

    public void setTile(int x, int y, int value) {
        if (x >= 0 && y >= 0 && x < mapGrid[0].length && y < mapGrid.length) {
            mapGrid[y][x] = value;
        }
    }

    public int getWidth() {
        return mapGrid[0].length;
    }

    public int getHeight() {
        return mapGrid.length;
    }
}


