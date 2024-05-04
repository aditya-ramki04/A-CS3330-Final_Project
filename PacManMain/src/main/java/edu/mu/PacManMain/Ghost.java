package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {
	
	protected JLabel label;
    protected int x, y;
    private Maze maze;
    private static final int GHOST_SPEED = 38; 
    private int cell_size = 38; 


   
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

   

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
    
 
    public int getX() {
        return x; 
    }

    public int getY() {
        return y; 
    }

   
    public int getGhostSize() {
        return 30; 
    }

    public void move() {
    	int nextX = x;
        int nextY = y;

      
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

      
        if (!isValidMove(nextX, nextY)) {
            
            currentDirection = getRandomDirection();
        } else {
 
            x = nextX;
            y = nextY;
        }
    }

    private boolean isValidMove(int x, int y) {
    	int cellX = (int) (x / cell_size); 
        int cellY = (int) (y / cell_size);

      
        if (cellX < 0 || cellX >= maze.getMapGrid()[0].length || cellY < 0 || cellY >= maze.getMapGrid().length) {
            return false;
        }
        int cellValue = maze.getMapGrid()[cellY][cellX];
        boolean isWall = cellValue == 1 || cellValue == 10 || cellValue == 11 || cellValue == 12 ||
                         cellValue == 13 || cellValue == 14 || cellValue == 15 || cellValue == 16 || cellValue == 5
                         ;

        return !isWall; 
    }

    public void updatePosition() {
        label.setLocation(x, y);
    }

  
    private int getRandomDirection() {
    	return random.nextInt(4); 
    }
    
    public void setImage(String imagePath) {
        this.Icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(cell_size, cell_size, Image.SCALE_SMOOTH));
        this.label.setIcon(Icon);
    }

}
