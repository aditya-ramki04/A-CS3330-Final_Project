package edu.mu.PacManMain;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;

public class GameBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel startScreen;
    private JPanel gameScreen;
    private Maze maze;


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
        startScreen.setBounds(0, 0, 434, 350);
        startScreen.setBackground(Color.BLACK);
        contentPane.add(startScreen);
        
        maze = new Maze();  
        
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
        gameScreen.removeAll(); // Clear previous content
        gameScreen.setLayout(new GridLayout(maze.getGrid().length, maze.getGrid()[0].length)); // Grid layout based on maze size

        for (int i = 0; i < maze.getGrid().length; i++) {
            for (int j = 0; j < maze.getGrid()[0].length; j++) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                switch (maze.getGrid()[i][j]) {
                    case 1:
                        cell.setBackground(Color.BLACK); // Wall
                        break;
                    case 2:
                        cell.setBackground(Color.YELLOW); // Pellet
                        break;
                    case 3:
                        cell.setBackground(Color.RED); // Power-up
                        break;
                    case 4:
                    	cell.setBackground(Color.GREEN); //cherry image
                    	break;
                    case 5: 
                    	cell.setBackground(Color.PINK); //start area for ghost
                    	break;
                    case 6:
                    	cell.setBackground(Color.GRAY);
                    	break;
                    default:
                        cell.setBackground(Color.WHITE); // Empty space
                }
                gameScreen.add(cell);
            }
        }
        gameScreen.validate(); // Validate the layout
        gameScreen.setVisible(true);
    }
    
    private void showStartScreen() {
        startScreen.setLayout(null); // Use absolute layout for precise positioning

        // Add Pac-Man text title
        JLabel titleLabel = new JLabel("PAC-MAN");
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(130, 10, 200, 50);
        startScreen.add(titleLabel);

        // Add Pac-Man image
        ImageIcon pacManIcon = new ImageIcon("images/pacman.png"); // Assuming there's an image file named "pacman.png"
        Image image = pacManIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        JLabel pacManLabel = new JLabel(resizedIcon);
        pacManLabel.setBounds(170, 100, 100, 100);
        startScreen.add(pacManLabel);

     // Calculate x-coordinate to center the button horizontally
        int buttonX = (startScreen.getWidth() - 150) / 2;

        // Add start button
        JButton startButton = new JButton("Start Game");
        startButton.setForeground(new Color(0, 128, 255));
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maze = new Maze();
                showGameScreen();
            }
        });
        startButton.setBounds(buttonX, 220, 150, 40);
        startScreen.add(startButton);

        // Set start screen panel visibility and hide game screen
        startScreen.setVisible(true);
        gameScreen.setVisible(false);
    }




}
