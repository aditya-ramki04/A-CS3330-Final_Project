package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PacMan {
    private JLabel label;
    private int x, y;
    private Maze maze;
    private static final int PACMAN_SPEED = 5;

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
