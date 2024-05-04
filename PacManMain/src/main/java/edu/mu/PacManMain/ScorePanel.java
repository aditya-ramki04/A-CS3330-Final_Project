package edu.mu.PacManMain;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    
	private int score;

    public ScorePanel() {
        score = 0;
        setPreferredSize(new Dimension(200, 50));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    public void increaseScore(int points) {
        score += points;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String scoreStr = "Score: " + score;
        g.drawString(scoreStr, 20, 30);
    }
}

