package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PacMan {
    private JLabel label;
    private int x, y;
    private Maze maze;
    private static final int PACMAN_SPEED = 8;

    public PacMan(String imagePath, Maze maze, int cellSize) {
        this.maze = maze;
        this.label = new JLabel(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH)));
    }

    public JLabel getLabel() {
        return label;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 50, 50);
    }

    public void move(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        int nextX = x;
        int nextY = y;

        switch (keyCode) {
            case KeyEvent.VK_UP:
                nextY -= PACMAN_SPEED;
                break;
            case KeyEvent.VK_DOWN:
                nextY += PACMAN_SPEED;
                break;
            case KeyEvent.VK_LEFT:
                nextX -= PACMAN_SPEED;
                break;
            case KeyEvent.VK_RIGHT:
                nextX += PACMAN_SPEED;
                break;
            default:
                break;
        }

        // Check if the next position is valid (not hitting a wall)
        if (isValidMove(nextX, nextY)) {
            x = nextX;
            y = nextY;
        }
    }

    private boolean isValidMove(int x, int y) {
        // Check if the next position is within the maze bounds and not hitting a wall
        return maze.isValidMove(x, y);
    }

    public void updatePosition() {
        label.setLocation(x, y);
    }
}

//public class PacMan {
//    // Define image paths for different directions
//    private JLabel label;
//    private int x, y;
//    private Maze maze;
//    private static final int PACMAN_SPEED = 5;
//    private static final String PACMAN_RIGHT_IMAGE = "images/pacmanrightopen.png";
//    private static final String PACMAN_LEFT_IMAGE = "images/pacmanleftopen.png";
//    private static final String PACMAN_UP_IMAGE = "images/pacmanupopen.png";
//    private static final String PACMAN_DOWN_IMAGE = "images/pacmandownopen.png";
//    
//    // Define member variables to hold the ImageIcon for different directions
//    private ImageIcon rightIcon;
//    private ImageIcon leftIcon;
//    private ImageIcon upIcon;
//    private ImageIcon downIcon;
//    
//    // Add a variable to store the current direction
//    private int currentDirection;
//
//    public PacMan(String imagePath, Maze maze, int cellSize) {
//        // Load images for different directions
//        this.rightIcon = new ImageIcon(new ImageIcon(PACMAN_RIGHT_IMAGE).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
//        this.leftIcon = new ImageIcon(new ImageIcon(PACMAN_LEFT_IMAGE).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
//        this.upIcon = new ImageIcon(new ImageIcon(PACMAN_UP_IMAGE).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
//        this.downIcon = new ImageIcon(new ImageIcon(PACMAN_DOWN_IMAGE).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
//
//        // Initially, set the direction to right
//        this.currentDirection = KeyEvent.VK_RIGHT;
//
//        // Initialize the label with the right direction image
//        this.label = new JLabel(rightIcon);
//    }
//
//    // Modify the move method to change the direction and update the image accordingly
//    public void move(KeyEvent evt) {
//        int keyCode = evt.getKeyCode();
//        int nextX = x;
//        int nextY = y;
//
//        // Update the current direction
//        currentDirection = keyCode;
//
//        switch (keyCode) {
//            case KeyEvent.VK_UP:
//                nextY -= PACMAN_SPEED;
//                label.setIcon(upIcon); // Change image to facing up
//                break;
//            case KeyEvent.VK_DOWN:
//                nextY += PACMAN_SPEED;
//                label.setIcon(downIcon); // Change image to facing down
//                break;
//            case KeyEvent.VK_LEFT:
//                nextX -= PACMAN_SPEED;
//                label.setIcon(leftIcon); // Change image to facing left
//                break;
//            case KeyEvent.VK_RIGHT:
//                nextX += PACMAN_SPEED;
//                label.setIcon(rightIcon); // Change image to facing right
//                break;
//            default:
//                break;
//        }
//
//        // Check if the next position is valid (not hitting a wall)
//        if (maze.isValidMove(nextX, nextY)) {
//            x = nextX;
//            y = nextY;
//        }
//    }
//}
