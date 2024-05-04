package edu.mu.PacManMain;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class GameBoardTest
{
	private PacMan pacMan;
    private Maze mockMaze;
    private JPanel mockPanel;
    private static final int CELL_SIZE = 38;
    
    @Before
    public void setUp() {
        mockMaze = new Maze(null);
        mockPanel = new JPanel();
        pacMan = new PacMan("images/pacman.png", mockMaze, mockPanel, CELL_SIZE);
    }
    
    @After
    public void tearDown() {
        // Clean up resources if needed
    }
    
    @Test
    public void testInitialPosition() {
        assertEquals(0, pacMan.getX());
        assertEquals(0, pacMan.getY());
    }
    
//    @Test
//    public void testMoveUp() {
//        
//		KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W');
//        int initialY = pacMan.getY();
//        pacMan.move(keyEvent);
//        assertEquals(initialY - PacMan.PACMAN_SPEED, pacMan.getY());
//    }
//    
//    @Test
//    public void testValidMove() {
//       
//
//        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
//        int initialX = pacMan.getX();
//        pacMan.move(keyEvent);
//        assertEquals(initialX + PacMan.PACMAN_SPEED, pacMan.getX());
//    }
//    
//    @Test
//    public void testInvalidMove() {
//        
//    	
//        KeyEvent keyEvent = new KeyEvent(mockPanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
//        int initialX = pacMan.getX();
//        pacMan.move(keyEvent);
//        assertEquals(initialX, pacMan.getX());
//    }
//	
	
}