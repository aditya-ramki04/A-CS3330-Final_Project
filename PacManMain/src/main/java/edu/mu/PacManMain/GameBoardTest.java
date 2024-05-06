package edu.mu.PacManMain;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class GameBoardTest
{
	private Ghost ghost;
	private PacMan pacMan;
    private Maze mockMaze;
    private JPanel mockPanel;
    private static final int CELL_SIZE = 38;
    private GameBoard gameBoard;
    private PowerUp powerUp;
    private Pellet pellet;
    private Food food;
    
    private int[][] mapGrid = {
    		{11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16},
		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
		    {13, 3, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 3, 14},
		    {13, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 2, 1, 5, 5, 5, 1, 2, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 1, 2, 2, 2, 1, 2, 1, 5, 5, 5, 1, 2, 1, 2, 2, 2, 1, 2, 14},
		    {13, 2, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1, 2, 1, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
		    {13, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 14},
		    {13, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 14},
		    {13, 3, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 3, 14},
		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
		    {10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 15}
		    };
		    

    
    @Before
    public void setUp() {
        mockMaze = new Maze(mapGrid);
        mockPanel = new JPanel();
        pacMan = new PacMan("images/pacman.png", mockMaze, mockPanel, CELL_SIZE);
        ghost = new Ghost("images/ghost.png", mockMaze, CELL_SIZE);
        gameBoard = new GameBoard();
        powerUp = new PowerUp(mockMaze, mockPanel, pacMan);
        pellet = new Pellet(mockMaze, mockPanel, pacMan);
        food = new Food(mockMaze, mockPanel, pacMan);
    }
    

    
    @After
    public void tearDown() {
        // Clean up resources if needed
    }
    
    
    
    @Test
    public void testInitialPosition() {
        assertEquals(0, pacMan.getX());
        assertEquals(0, pacMan.getY());
        assertEquals(0, ghost.getX());
        assertEquals(0, ghost.getY());
    }
        
    @Test
    public void testEatPowerUp() {
        int row = 1;
        int col = 1;
        mockMaze.getMapGrid()[row][col] = 3; 
        powerUp.eatPowerUp(row, col);
        assertEquals(0, mockMaze.getMapGrid()[row][col]); 
        assertTrue(pacMan.isPowerUpActive()); 
    }
    @Test
    public void testStartPowerUpTimer() {
        assertFalse(pacMan.isPowerUpActive()); 
        powerUp.startPowerUpTimer();
        assertTrue(pacMan.isPowerUpActive()); 
    }
    @Test
    public void testCancelPowerUpTimer() {
        powerUp.startPowerUpTimer(); 
        assertTrue(pacMan.isPowerUpActive()); 
        powerUp.cancelPowerUpTimer();
        assertFalse(pacMan.isPowerUpActive()); 
    }
    
    @Test
    public void testCyanGhostInitialPosition() {
        CyanGhost cyanGhost = new CyanGhost(mockMaze, CELL_SIZE);
        assertEquals(0, cyanGhost.getX());
        assertEquals(0, cyanGhost.getY());
    }

    @Test
    public void testRedGhostInitialPosition() {
        RedGhost redGhost = new RedGhost(mockMaze, CELL_SIZE);
        assertEquals(0, redGhost.getX());
        assertEquals(0, redGhost.getY());
    }

    @Test
    public void testOrangeGhostInitialPosition() {
        OrangeGhost orangeGhost = new OrangeGhost(mockMaze, CELL_SIZE);
        assertEquals(0, orangeGhost.getX());
        assertEquals(0, orangeGhost.getY());
    }

    @Test
    public void testPinkGhostInitialPosition() {
        PinkGhost pinkGhost = new PinkGhost(mockMaze, CELL_SIZE);
        assertEquals(0, pinkGhost.getX());
        assertEquals(0, pinkGhost.getY());
    }

    @Test
    public void testMoveDown() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        int initialY = pacMan.getY();
        pacMan.move(keyEvent);
        assertEquals(initialY + PacMan.PACMAN_SPEED, pacMan.getY());
    }
    
    @Test
    public void testMoveUp() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W');
        int initialY = pacMan.getY();
        pacMan.move(keyEvent);
        assertEquals(0, pacMan.getY());
    }

    @Test
    public void testMoveLeft() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        int initialX = pacMan.getX();
        pacMan.move(keyEvent);
        assertEquals(0, pacMan.getX());
    }
    
    @Test
    public void testMoveRight() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
        int initialX = pacMan.getX();
        pacMan.move(keyEvent);
        assertEquals(initialX + PacMan.PACMAN_SPEED, pacMan.getX());
    }

    
    @Test
    public void testIsValidMove_ValidMove() {
        assertTrue(mockMaze.isValidMove(38, 38)); 
    }

    @Test
    public void testIsValidMove_InvalidMove_Wall() {
        assertFalse(mockMaze.isValidMove(1, 1));
    }

    @Test
    public void testIsValidMove_InvalidMove_OutOfBounds() {
        assertFalse(mockMaze.isValidMove(100, 100));
    }
    
    @Test
    public void testEatPellet() {
        pellet.eatPellet(CELL_SIZE, CELL_SIZE);
        assertEquals(100, pacMan.getScore());
    }
    
    @Test
    public void testEatCherryBonus() {
        food.eatCherryBonus(CELL_SIZE, CELL_SIZE);
        assertEquals(1000, pacMan.getScore());
    }
}
	
