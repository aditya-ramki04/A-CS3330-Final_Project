package edu.mu.PacManMain;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PowerUp {
    private JPanel mazePanel;
    private Maze maze;
    private PacMan pacMan;
    private Timer powerUpTimer;
    private int remainingTime = 0; 
   
    private ScorePanel sPanel;

    
    /**
     * Constructs a PowerUp object with the specified maze, maze panel, and Pac-Man character.
     *
     * @param maze      The maze grid containing information about the game layout.
     * @param mazePanel The panel representing the maze in the user interface.
     * @param pacMan    The Pac-Man character interacting with the power-ups.
     */
    public PowerUp(Maze maze, JPanel mazePanel, PacMan pacMan) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.pacMan = pacMan;
    }
    
    /**
     * Attempts to consume a power-up at the specified row and column.
     * If a power-up is present at the specified location, it is consumed,
     * Pac-Man's score is incremented, and the power-up effect is activated.
     * A timer is started to manage the duration of the power-up effect.
     *
     * @param row The row index of the power-up.
     * @param col The column index of the power-up.
     */
    public void eatPowerUp(int row, int col) {

        if (maze.getMapGrid()[row][col] == 3) { 
            maze.getMapGrid()[row][col] = 0; 
            pacMan.incrementScore(500); 
            pacMan.setPowerUpActive(true); 
            playPowerUpSound();
            
            
            if (powerUpTimer == null) { 
                startPowerUpTimer();
            } else { 
                powerUpTimer.cancel(); 
                startPowerUpTimer(); 
            }

            Component[] components = mazePanel.getComponents();
            int cellIndex = row * maze.getMapGrid()[0].length + col;
            if (cellIndex >= 0 && cellIndex < components.length) {
                JLabel cellLabel = (JLabel) components[cellIndex];
                cellLabel.setBackground(Color.BLACK);

                cellLabel.setIcon(null); 

                cellLabel.setIcon(null);
            }
        }
    }
    
    /**
     * Starts a timer to manage the duration of the power-up effect.
     * The power-up effect lasts for a fixed duration (5 seconds).
     * During this time, Pac-Man's movement speed is increased, and
     * he becomes invulnerable to ghosts.
     */
    private void startPowerUpTimer() {
        pacMan.setPowerUpActive(true);

        powerUpTimer = new Timer();
        powerUpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    System.out.println("Power-up countdown: " + remainingTime + " seconds");
                    remainingTime--;
                } else {
                    pacMan.setPowerUpActive(false); 
                    System.out.println("Power-up expired!");
                    powerUpTimer.cancel(); 
                    powerUpTimer = null; 
                }
            }
        }, 0, 1000); 

        remainingTime = 5; 
    }

    /**
     * Cancels the power-up timer if it is active.
     * This method is called to stop the timer when the game state changes,
     * such as when Pac-Man loses a life or completes a level.
     */
    public void cancelPowerUpTimer() {
        if (powerUpTimer != null) {
            powerUpTimer.cancel();
            powerUpTimer = null; 
        }
    }
    
    /**
     * Plays the sound effect associated with consuming a power-up.
     * The sound effect adds auditory feedback to the gameplay experience.
     */
    private void playPowerUpSound() {
        try {
            File soundFile = new File("./Audio/pacman_eatfruit.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
