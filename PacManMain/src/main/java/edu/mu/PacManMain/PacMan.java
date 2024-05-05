package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PacMan {
    private JLabel label;
    private int x, y;
    private Maze maze;
    static final int PACMAN_SPEED = 38;
    private boolean allowMovement = true;
    private int score;
    private PowerUp powerUp;
    private Pellet pellet;
    private Food strawberry;
    private boolean powerUpActive = false;
    private int cellSize = 38;

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


    /**
     * Initializes the PacMan character.
     *
     * @param imagePath  The path to the image representing PacMan.
     * @param maze       The maze in which PacMan moves.
     * @param mazePanel  The panel containing the maze.
     * @param cellSize   The size of each cell in the maze.
     */
    public PacMan(String imagePath, Maze maze, JPanel mazePanel, int cellSize) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.powerUp = new PowerUp(maze, mazePanel, this);
        this.pellet = new Pellet(maze, mazePanel, this);
        this.strawberry = new Food(maze, mazePanel, this);
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
    
    /**
     * Checks if the PowerUp is active.
     *
     * @return True if the PowerUp is active, otherwise false.
     */
    public boolean isPowerUpActive() {
        return powerUpActive;
    }

    /**
     * Sets the status of the PowerUp.
     *
     * @param powerUpActive True to activate the PowerUp, false to deactivate.
     */
    public void setPowerUpActive(boolean powerUpActive) {
        this.powerUpActive = powerUpActive;
    }

    /**
     * Gets the label representing PacMan.
     *
     * @return The label representing PacMan.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Gets the X coordinate of PacMan in the maze.
     *
     * @return The X coordinate of PacMan.
     */
    public int getCellX() {
        return (int) (x / cellSize);
    }

    /**
     * Gets the Y coordinate of PacMan in the maze.
     *
     * @return The Y coordinate of PacMan.
     */
    public int getCellY() {
        return (int) (y / cellSize);
    }

    /**
     * Sets the position of PacMan in the maze.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        label.setBounds(x, y, 0, 0);
    }

    /**
     * Gets the X coordinate of PacMan.
     *
     * @return The X coordinate of PacMan.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate of PacMan.
     *
     * @return The Y coordinate of PacMan.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the size of PacMan.
     *
     * @return The size of PacMan.
     */
    public int getPacmanSize() {
        return pacmanSize;
    }

    /**
     * Toggles the movement of PacMan.
     */
    public void toggleMovement() {
        allowMovement = !allowMovement;
    }
 
    private void setPacmanIconUp() {
        label.setIcon(upOpenIcon);
        label.setIcon(moveCount % 2 == 0 ? upOpenIcon : upClosedIcon);
    }
 
    private void setPacmanIconDown() {
        label.setIcon(downOpenIcon);
        label.setIcon(moveCount % 2 == 0 ? downOpenIcon : downClosedIcon);
    }
 
    private void setPacmanIconLeft() {
        label.setIcon(leftOpenIcon);
        label.setIcon(moveCount % 2 == 0 ? leftOpenIcon : leftClosedIcon);
    }
    
    private void setPacmanIconRight() {
        label.setIcon(moveCount % 2 == 0 ? rightOpenIcon : rightClosedIcon);
    }

    /**
     * Moves PacMan based on the KeyEvent received.
     *
     * @param evt The KeyEvent representing the user input.
     */
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
                setPacmanIconUp();
                break;
            case KeyEvent.VK_DOWN:
                nextY += PACMAN_SPEED;
                label.setIcon(downOpenIcon);
                setPacmanIconDown();
                break;
            case KeyEvent.VK_LEFT:
                nextX -= PACMAN_SPEED;
                label.setIcon(leftOpenIcon);        
                setPacmanIconLeft();
                break;
            case KeyEvent.VK_RIGHT:
                nextX += PACMAN_SPEED;
                setPacmanIconRight();
                break;
            default:
                break;
        }

     
        if (isValidMove(nextX, nextY)) {
            x = nextX;
            y = nextY;
            moveCount++;
            int cellX = (int) (x / cellSize);
            int cellY = (int) (y / cellSize);
            pellet.eatPellet(cellY, cellX);

            strawberry.eatCherryBonus(cellY, cellX);

            powerUp.eatPowerUp(cellY, cellX);

        } else {
            System.out.println("Invalid move: Collision detected");
        }
    }
  
    /**
     * Checks if the given coordinates represent a valid move for PacMan.
     *
     * @param x The X coordinate of the move.
     * @param y The Y coordinate of the move.
     * @return True if the move is valid, false if the move is blocked by a wall or out of bounds.
     */
    private boolean isValidMove(int x, int y) {
        int cellX = (int) (x / cellSize); 
        int cellY = (int) (y / cellSize);
        if (cellX < 0 || cellX >= maze.getMapGrid()[0].length || cellY < 0 || cellY >= maze.getMapGrid().length) {
            return false; 
        }

        int cellValue = maze.getMapGrid()[cellY][cellX];
        boolean isWall = cellValue == 1 || cellValue == 10 || cellValue == 11 || cellValue == 12 ||
                         cellValue == 13 || cellValue == 14 || cellValue == 15 || cellValue == 16 || cellValue == 5
                         ;

        return !isWall; 
    }
    
    /**
     * Increments the score by the specified number of points.
     *
     * @param points The number of points to increment the score by.
     */
    public void incrementScore(int points) {
        score += points;
        System.out.println("Your current score: " + score);
    }

    /**
     * Updates the position of PacMan on the screen.
     */
    public void updatePosition() {
        label.setLocation(x, y);
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the score to zero.
     */
    public void resetScore() {
        score = 0;
    }
   
}