package edu.mu.PacManMain;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class GameBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel startScreen;
    private JPanel gameScreen;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameBoard frame = new GameBoard();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GameBoard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Use absolute layout for simplicity
        
        // Create start screen panel
        startScreen = new JPanel();
        startScreen.setBounds(0, 0, 434, 261);
        contentPane.add(startScreen);
        
        JButton StartButton = new JButton("Start Game");
        StartButton.setForeground(new Color(0, 128, 255));
        StartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameScreen();
            }
        });
        startScreen.add(StartButton);
        
        // Create game screen panel
        gameScreen = new JPanel();
        gameScreen.setBounds(0, 0, 434, 261);
        gameScreen.setBackground(Color.WHITE); // Change color for visibility
        contentPane.add(gameScreen);
        
        // Initially show the start screen
        showStartScreen();
    }
    
    private void showGameScreen() {
        startScreen.setVisible(false);
        gameScreen.setVisible(true);
    }
    
    private void showStartScreen() {
        startScreen.setVisible(true);
        gameScreen.setVisible(false);
    }
}
