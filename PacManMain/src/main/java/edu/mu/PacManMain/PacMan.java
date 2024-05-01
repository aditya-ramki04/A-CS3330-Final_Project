package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PacMan {
    private JLabel label;
    private int x, y;
    private Maze maze;
    private static final int PACMAN_SPEED = 38;
    private boolean allowMovement = true;
    private int score;
    
    private int cellSize = 38; // Rounded from 38.09 for simplicity

    private static final String PACMAN_RIGHT_IMAGE_OPEN = "images/pacmanrightopen.png";
    private static final String PACMAN_LEFT_IMAGE_OPEN = "images/pacmanleftopen.png";
    private static final String PACMAN_UP_IMAGE_OPEN = "images/pacmantopopen.png";
    private static final String PACMAN_DOWN_IMAGE_OPEN = "images/pacmanbottomaopen.png";
    private static final String PACMAN_RIGHT_IMAGE_CLOSED = "images/pacmanrightclosed.png";
    private static final String PACMAN_LEFT_IMAGE_CLOSED = "images/pacmanleftclosed.png";
    private static final String PACMAN_UP_IMAGE_CLOSED = "images/pacmantopclosed.png";
    private static final String PACMAN_DOWN_IMAGE_CLOSED = "images/pacmanbottomclosed.png";
    

    private ImageIcon rightOpenIcon;
    private ImageIcon leftOpenIcon;
    private ImageIcon upOpenIcon;
    private ImageIcon downOpenIcon;
    private ImageIcon rightClosedIcon;
    private ImageIcon leftClosedIcon;
    private ImageIcon upClosedIcon;
    private ImageIcon downClosedIcon;
    private JPanel mazePanel;
    

    private int currentDirection;
    private int moveCount;
    private int pacmanSize = 30;

    public PacMan(String imagePath, Maze maze, JPanel mazePanel, int cellSize) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.label = new JLabel(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH)));
        
        this.rightOpenIcon = new ImageIcon(new ImageIcon(PACMAN_RIGHT_IMAGE_OPEN).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.leftOpenIcon = new ImageIcon(new ImageIcon(PACMAN_LEFT_IMAGE_OPEN).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.upOpenIcon = new ImageIcon(new ImageIcon(PACMAN_UP_IMAGE_OPEN).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.downOpenIcon = new ImageIcon(new ImageIcon(PACMAN_DOWN_IMAGE_OPEN).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.rightClosedIcon = new ImageIcon(new ImageIcon(PACMAN_RIGHT_IMAGE_CLOSED).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.leftClosedIcon = new ImageIcon(new ImageIcon(PACMAN_LEFT_IMAGE_CLOSED).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.upClosedIcon = new ImageIcon(new ImageIcon(PACMAN_UP_IMAGE_CLOSED).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        this.downClosedIcon = new ImageIcon(new ImageIcon(PACMAN_DOWN_IMAGE_CLOSED).getImage().getScaledInstance(pacmanSize, pacmanSize, Image.SCALE_SMOOTH));
        
        this.currentDirection = KeyEvent.VK_RIGHT; 
    }

    public JLabel getLabel() {
        return label;
    }
    
    public int getCellX() {
        return (int) (x / cellSize);
    }

    public int getCellY() {
        return (int) (y / cellSize);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 0, 0);
    }
    
    public int getX() {
        return x; // Returns the current x-coordinate of Pacman
    }

    // Getter for y-coordinate
    public int getY() {
        return y; // Returns the current y-coordinate of Pacman
    }

    // Getter for Pacman's size
    public int getPacmanSize() {
        return pacmanSize; // Returns the size of Pacman
    }
    
    public void toggleMovement() {
        allowMovement = !allowMovement;
    }

    public void move(KeyEvent evt) {
        if(!allowMovement) {
            return;
        }
        System.out.println("Moving Pacman...");
        int keyCode = evt.getKeyCode();
        int nextX = x;
        int nextY = y;
        currentDirection = keyCode;

        switch (keyCode) {
            case KeyEvent.VK_UP:
                nextY -= PACMAN_SPEED;
                label.setIcon(upOpenIcon);
                label.setIcon(moveCount % 2 == 0 ? upOpenIcon : upClosedIcon);
                break;
            case KeyEvent.VK_DOWN:
                nextY += PACMAN_SPEED;
                label.setIcon(downOpenIcon);
                label.setIcon(moveCount % 2 == 0 ? downOpenIcon : downClosedIcon);
                break;
            case KeyEvent.VK_LEFT:
                nextX -= PACMAN_SPEED;
                label.setIcon(leftOpenIcon);        
                label.setIcon(moveCount % 2 == 0 ? leftOpenIcon : leftClosedIcon);
                break;
            case KeyEvent.VK_RIGHT:
                nextX += PACMAN_SPEED;
                label.setIcon(moveCount % 2 == 0 ? rightOpenIcon : rightClosedIcon);
                break;
            default:
                break;
        }

        // Check if the move is valid
        if (isValidMove(nextX, nextY)) {
            x = nextX;
            y = nextY;
            moveCount++;
            
            // Check if Pacman's new position overlaps with a pellet
            int cellX = (int) (x / cellSize);
            int cellY = (int) (y / cellSize);
            eatPellet(cellY, cellX); // Cell indices are swapped due to row-column convention

            eatCherryBonus(cellY, cellX);

            eatPowerUp(cellY, cellX);

        } else {
            System.out.println("Invalid move: Collision detected");
        }
    }

    private boolean isValidMove(int x, int y) {
        int cellX = (int) (x / cellSize); // Convert pixel position to grid cell
        int cellY = (int) (y / cellSize);

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
    
    public void eatPellet(int row, int col) {
        System.out.println("Checking pellet at row: " + row + ", col: " + col);
        if (maze.getMapGrid()[row][col] == 2) { // Check if Pacman's position corresponds to a pellet
            maze.getMapGrid()[row][col] = 0; // Remove the pellet from the maze grid
            System.out.println("Pellet eaten at row: " + row + ", col: " + col);
            score+= 100;
            // Update the appearance of the corresponding JLabel in the maze panel to represent an empty space (black square)
            Component[] components = mazePanel.getComponents();
            int cellIndex = row * maze.getMapGrid()[0].length + col;
            if (cellIndex >= 0 && cellIndex < components.length) {
                JLabel cellLabel = (JLabel) components[cellIndex];
                cellLabel.setBackground(Color.BLACK);
                cellLabel.setIcon(null); // Clear any existing icon
            } else {
                System.out.println("Invalid cell index: " + cellIndex);
            }
            System.out.println("Your current score " + score);

            // You can also increment a score counter or perform any other necessary actions here
        } else {
            System.out.println("No pellet found at row: " + row + ", col: " + col);
        }
    }
    

    public void eatCherryBonus(int row, int col) {
        System.out.println("Checking if cherry bonus at row: " + row + ", col: " + col);
        if (maze.getMapGrid()[row][col] == 4) { // Check if Pacman's position corresponds to a pellet
            maze.getMapGrid()[row][col] = 0; // Remove the pellet from the maze grid
            System.out.println("Cherry Bonus eaten at row: " + row + ", col: " + col);
            score+= 1000;
            // Update the appearance of the corresponding JLabel in the maze panel to represent an empty space (black square)
            Component[] components = mazePanel.getComponents();
            int cellIndex = row * maze.getMapGrid()[0].length + col;
            if (cellIndex >= 0 && cellIndex < components.length) {
                JLabel cellLabel = (JLabel) components[cellIndex];
                cellLabel.setBackground(Color.BLACK);
                cellLabel.setIcon(null); // Clear any existing icon
            } else {
                System.out.println("Invalid cell index: " + cellIndex);
            }
            System.out.println("Your current score " + score);

            // You can also increment a score counter or perform any other necessary actions here
        } else {
            System.out.println("No cherry bonus found at row: " + row + ", col: " + col);
        }
    }
  

	 public void eatPowerUp(int row, int col) {
	        System.out.println("Checking pellet at row: " + row + ", col: " + col);
	        if (maze.getMapGrid()[row][col] == 3) { // Check if Pacman's position corresponds to a pellet
	            maze.getMapGrid()[row][col] = 0; // Remove the pellet from the maze grid
	            System.out.println("Pellet eaten at row: " + row + ", col: " + col);
	            score+= 500;
	            // Update the appearance of the corresponding JLabel in the maze panel to represent an empty space (black square)
	            Component[] components = mazePanel.getComponents();
	            int cellIndex = row * maze.getMapGrid()[0].length + col;
	            if (cellIndex >= 0 && cellIndex < components.length) {
	                JLabel cellLabel = (JLabel) components[cellIndex];
	                cellLabel.setBackground(Color.BLACK);
	                cellLabel.setIcon(null); // Clear any existing icon
	            } else {
	                System.out.println("Invalid cell index: " + cellIndex);
	            }
	            System.out.println("Your current score " + score);

	            // You can also increment a score counter or perform any other necessary actions here
	        } else {
	            System.out.println("No pellet found at row: " + row + ", col: " + col);
	        }
	    }

    
    public void updatePosition() {
        label.setLocation(x, y);
    }
   
}