package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {
	
	protected JLabel label;
    protected int x, y;
    private Maze maze;
    private static final int GHOST_SPEED = 38; // Adjust speed as needed
    private int cell_size = 38; // Rounded from 38.09 for simplicity


    // Define integer constants representing directions
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    // Define image paths for different directions
   // private static final String GHOST_IMAGE = "images/ghostright.png";

    private ImageIcon Icon;
    
    private int currentDirection;
    private Random random;

    public Ghost(String imagePath, Maze maze, int cellSize) {
        this.maze = maze;
        this.label = new JLabel(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH)));

        this.Icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

        this.random = new Random();
        this.currentDirection = getRandomDirection();
    }

    public JLabel getLabel() {
        return label;
    }
    
 // Getter for x-coordinate
    public int getX() {
        return x; // Returns the current x-coordinate of the ghost
    }

    // Getter for y-coordinate
    public int getY() {
        return y; // Returns the current y-coordinate of the ghost
    }

    // Getter for ghost size
    public int getGhostSize() {
        return 30; // Returns the size of the ghost
    }

    public void move() {
    	int nextX = x;
        int nextY = y;

        // Update next position based on the current direction
        switch (currentDirection) {
            case UP:
                nextY -= GHOST_SPEED;
                break;
            case DOWN:
                nextY += GHOST_SPEED;
                break;
            case LEFT:
                nextX -= GHOST_SPEED;
                break;
            case RIGHT:
                nextX += GHOST_SPEED;
                break;
            default:
                break;
        }

        // Check if the next position is valid (not hitting a wall)
        if (!isValidMove(nextX, nextY)) {
            // If hitting a wall, choose a new random direction
            currentDirection = getRandomDirection();
        } else {
            // Otherwise, update position
            x = nextX;
            y = nextY;
        }
    }

    private boolean isValidMove(int x, int y) {
    	int cellX = (int) (x / cell_size); // Convert pixel position to grid cell
        int cellY = (int) (y / cell_size);

        // Ensure Pacman stays within the maze bounds
        if (cellX < 0 || cellX >= maze.getMapGrid()[0].length || cellY < 0 || cellY >= maze.getMapGrid().length) {
            return false; // Out of bounds
        }

        // Check if the cell is a wall
        int cellValue = maze.getMapGrid()[cellY][cellX];
        boolean isWall = cellValue == 1 || cellValue == 10 || cellValue == 11 || cellValue == 12 ||
                         cellValue == 13 || cellValue == 14 || cellValue == 15 || cellValue == 16 || cellValue == 5
                         ;

        return !isWall; // If not a wall, the move is valid
    }

    public void updatePosition() {
        label.setLocation(x, y);
    }

    // Method to get a random direction
    private int getRandomDirection() {
    	return random.nextInt(4); // 0: UP, 1: DOWN, 2: LEFT, 3: RIGHT
    }
    
    public void setImage(String imagePath) {
        this.Icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(cell_size, cell_size, Image.SCALE_SMOOTH));
        this.label.setIcon(Icon);
    }

}
