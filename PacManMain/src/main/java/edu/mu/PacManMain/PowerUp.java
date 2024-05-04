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

    
    public PowerUp(Maze maze, JPanel mazePanel, PacMan pacMan) {
        this.maze = maze;
        this.mazePanel = mazePanel;
        this.pacMan = pacMan;
    }
    
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
            }

        }
    }
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

    public void cancelPowerUpTimer() {
        if (powerUpTimer != null) {
            powerUpTimer.cancel();
            powerUpTimer = null; 
        }
    }
    
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
