package edu.mu.PacManMain;

import static org.junit.Assert.*;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
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
    
    private FocusTraversalOnArray traversalPolicy;
    private JButton[] buttons;

    
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
		    

    /**
     * Sets up the test fixtures.
     */
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
        buttons = new JButton[5];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i + 1));
        }
        traversalPolicy = new FocusTraversalOnArray(buttons);
    }
     
    /**
     * Tests the initial position of PacMan and Ghost.
     */
    @Test
    public void testInitialPosition() {
        assertEquals(0, pacMan.getX());
        assertEquals(0, pacMan.getY());
        assertEquals(0, ghost.getX());
        assertEquals(0, ghost.getY());
    }
        
    /**
     * Tests the power-up eating functionality.
     */
    @Test
    public void testEatPowerUp() {
        int row = 1;
        int col = 1;
        mockMaze.getMapGrid()[row][col] = 3; 
        powerUp.eatPowerUp(row, col);
        assertEquals(0, mockMaze.getMapGrid()[row][col]); 
        assertTrue(pacMan.isPowerUpActive()); 
    }
    
    /**
     * Tests starting the power-up timer.
     */
    @Test
    public void testStartPowerUpTimer() {
        assertFalse(pacMan.isPowerUpActive()); 
        powerUp.startPowerUpTimer();
        assertTrue(pacMan.isPowerUpActive()); 
    }
    
    /**
     * Tests canceling the power-up timer.
     */
    @Test
    public void testCancelPowerUpTimer() {
        powerUp.startPowerUpTimer(); 
        assertTrue(pacMan.isPowerUpActive()); 
        powerUp.cancelPowerUpTimer();
    }
    
    /**
     * Tests the initial position of CyanGhost.
     */
    @Test
    public void testCyanGhostInitialPosition() {
        CyanGhost cyanGhost = new CyanGhost(mockMaze, CELL_SIZE);
        assertEquals(0, cyanGhost.getX());
        assertEquals(0, cyanGhost.getY());
    }

    /**
     * Tests the initial position of RedGhost.
     */
    @Test
    public void testRedGhostInitialPosition() {
        RedGhost redGhost = new RedGhost(mockMaze, CELL_SIZE);
        assertEquals(0, redGhost.getX());
        assertEquals(0, redGhost.getY());
    }

    /**
     * Tests the initial position of OrangeGhost.
     */
    @Test
    public void testOrangeGhostInitialPosition() {
        OrangeGhost orangeGhost = new OrangeGhost(mockMaze, CELL_SIZE);
        assertEquals(0, orangeGhost.getX());
        assertEquals(0, orangeGhost.getY());
    }
    

    /**
     * Tests the initial position of PinkGhost.
     */
    @Test
    public void testPinkGhostInitialPosition() {
        PinkGhost pinkGhost = new PinkGhost(mockMaze, CELL_SIZE);
        assertEquals(0, pinkGhost.getX());
        assertEquals(0, pinkGhost.getY());
    }

    
    /**
     * Tests PacMan movement downward.
     */
    @Test
    public void testMoveDown() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        int initialY = pacMan.getY();
        pacMan.move(keyEvent);
        assertEquals(initialY - PacMan.PACMAN_SPEED, -38);
    }
    
    /**
     * Tests PacMan movement upwards.
     */
    @Test
    public void testMoveUp() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W');
        int initialY = pacMan.getY();
        pacMan.move(keyEvent);
        assertEquals(initialY + PacMan.PACMAN_SPEED, 38);
    }

    /**
     * Tests PacMan movement left.
     */
    @Test
    public void testMoveLeft() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        int initialX = pacMan.getX();
        pacMan.move(keyEvent);
        assertEquals(initialX - PacMan.PACMAN_SPEED, -38);
    }
    
    /**
     * Tests PacMan movement right.
     */
    @Test
    public void testMoveRight() {
        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
        int initialX = pacMan.getX();
        pacMan.move(keyEvent);
        assertEquals(initialX + PacMan.PACMAN_SPEED, 38);
    }

    /**
     * Tests whether a move is valid when it's blocked by a wall.
     */
    @Test
    public void testIsValidMove_InvalidMove_Wall() {
        assertFalse(mockMaze.isValidMove(1, 1));
    }

    /**
     * Tests whether a move is valid when it's out of bounds.
     */
    @Test
    public void testIsValidMove_InvalidMove_OutOfBounds() {
        assertFalse(mockMaze.isValidMove(100, 100));
    }
    
    /**
     * Tests the functionality of eating a pellet.
     * When a pellet is eaten, it should increase the score by 100.
     */
    @Test
    public void testEatPellet() {
        pellet.eatPellet(1, 1);
        assertEquals(100, pacMan.getScore());
    }
    
    /**
     * Tests the functionality of eating a cherry bonus.
     * When a cherry bonus is eaten, it should increase the score by 1000.
     */
    @Test
    public void testEatCherryBonus() {
        food.eatCherryBonus(14, 10);
        assertEquals(1000, pacMan.getScore());
    }
    
    
    
    /**
     * Tests the behavior of getting the component after a specified component in the focus traversal policy.
     * It should return the component that comes after the specified component in the traversal order.
     */
    @Test
    public void testGetComponentAfter() {
        Component componentAfterFirst = traversalPolicy.getComponentAfter(new JPanel(), buttons[0]);
        assertEquals(buttons[1], componentAfterFirst);

        Component componentAfterLast = traversalPolicy.getComponentAfter(new JPanel(), buttons[buttons.length - 1]);
        assertEquals(buttons[0], componentAfterLast);
    }

    /**
     * Tests the behavior of getting the component before a specified component in the focus traversal policy.
     * It should return the component that comes before the specified component in the traversal order.
     */
    @Test
    public void testGetComponentBefore() {
        Component componentBeforeLast = traversalPolicy.getComponentBefore(new JPanel(), buttons[buttons.length - 1]);
        assertEquals(buttons[buttons.length - 2], componentBeforeLast);

        Component componentBeforeFirst = traversalPolicy.getComponentBefore(new JPanel(), buttons[0]);
        assertEquals(buttons[buttons.length - 1], componentBeforeFirst);
    }

    /**
     * Tests the behavior of getting the first component in the focus traversal policy.
     * It should return the first component in the traversal order.
     */
    @Test
    public void testGetFirstComponent() {
        Component firstComponent = traversalPolicy.getFirstComponent(new JPanel());
        assertEquals(buttons[0], firstComponent);
    }

    /**
     * Tests the behavior of getting the last component in the focus traversal policy.
     * It should return the last component in the traversal order.
     */
    @Test
    public void testGetLastComponent() {
        Component lastComponent = traversalPolicy.getLastComponent(new JPanel());
        assertEquals(buttons[buttons.length - 1], lastComponent);
    }

    /**
     * Tests the behavior of getting the default component in the focus traversal policy.
     * It should return the default component in the traversal order.
     */
    @Test
    public void testGetDefaultComponent() {
        Component defaultComponent = traversalPolicy.getDefaultComponent(new JPanel());
        assertEquals(buttons[0], defaultComponent);
    }
}
	
