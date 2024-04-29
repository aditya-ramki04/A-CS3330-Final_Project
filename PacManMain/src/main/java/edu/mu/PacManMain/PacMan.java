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
    

    private int currentDirection;
    private int moveCount;
    private int pacmanSize = 30;

    public PacMan(String imagePath, Maze maze, int cellSize) {
        this.maze = maze;
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 0, 0);
    }
    
    public void toggleMovement() {
        allowMovement = !allowMovement;
    }

    public void move(KeyEvent evt) {
    	if(!allowMovement) {
    		return;
    	}
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
                         cellValue == 13 || cellValue == 14 || cellValue == 15 || cellValue == 16;

        return !isWall; // If not a wall, the move is valid
    }


    public void updatePosition() {
        label.setLocation(x, y);
    }
}

