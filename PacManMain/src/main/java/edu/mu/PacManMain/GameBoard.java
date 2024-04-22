package edu.mu.PacManMain;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class GameBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel startScreen;
    private JPanel gameScreen;
    private Maze maze;
    //private ImageIcon pauseIcon;
    //private ImageIcon playIcon;
    
    //nmc
    private boolean isPaused = false;
    
    private void togglePause() {
    	isPaused = !isPaused;
    	if(isPaused) {
    		// need to add logic: stop game timer, freeze Pac-Man and ghosts
    	}else {
    		// when the game is resumed: resume game timer, un-freeze Pack-Man and ghosts
    	}
    }
    //nmc 
    
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
        contentPane.setLayout(new CardLayout(0, 0));
        
        // Create start screen panel
        startScreen = new JPanel();
        startScreen.setBackground(Color.BLACK);
        contentPane.add(startScreen, "name_13981319384000");
        

        maze = new Maze();
    //    ScoreTracking scoreTracker = new ScoreTracking();
        
        
        JButton StartButton = new JButton("Start Game");
        StartButton.setForeground(new Color(0, 128, 255));
        StartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	maze = new Maze();
                showGameScreen();
            }
        });
        startScreen.add(StartButton);
        
        // Create game screen panel
        gameScreen = new JPanel();
        gameScreen.setBackground(Color.WHITE); // Change color for visibility
        contentPane.add(gameScreen, "name_13981348637700");
        
        //nmc
        //Pause and Play images
        //pauseIcon = new ImageIcon("PauseLogo.png");
        //playIcon = new ImageIcon("PlayLogo.png");
        //nmc
        
        // Initially show the start screen
        showStartScreen();
    }
    
    private void showGameScreen() {
    	startScreen.setVisible(false);
        gameScreen.removeAll(); // Clear previous content
        gameScreen.setLayout(new BorderLayout());
        //gameScreen.setLayout(new GridLayout(maze.getGrid().length, maze.getGrid()[0].length)); // Grid layout based on maze size
        
        //nmc
       JPanel play_pausePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       JButton pauseButton = new JButton("Pause");
       //pauseButton.setBorderPainted(false);// removes border
       //pauseButton.setContentAreaFilled(false); //removes def bg
       // pauseButton.setFocusPainted(false); //removes focus border
        pauseButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		togglePause();//changes pause state when button is clicked
        		pauseButton.setText(isPaused ? "Play" : "Pause"); //update button text on state
        	}
        });
        play_pausePanel.add(pauseButton);
        gameScreen.add(play_pausePanel, BorderLayout.NORTH);
        
        JPanel gridPanel = new JPanel(new GridLayout(maze.getGrid().length, maze.getGrid()[0].length)); // Grid layout based on maze size
        for(int i = 0; i < maze.getGrid().length; i++) {
        	for(int j = 0; j < maze.getGrid()[0].length; j++) {
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
            gridPanel.add(cell);
        }
    }
        gameScreen.add(gridPanel, BorderLayout.CENTER);
        gameScreen.validate();
        gameScreen.setVisible(true);
    }
    //nmc
        

//        for (int i = 0; i < maze.getGrid().length; i++) {
//            for (int j = 0; j < maze.getGrid()[0].length; j++) {
//                JLabel cell = new JLabel();
//                cell.setOpaque(true);
//                switch (maze.getGrid()[i][j]) {
//                    case 1:
//                        cell.setBackground(Color.BLACK); // Wall
//                        break;
//                    case 2:
//                        cell.setBackground(Color.YELLOW); // Pellet
//                        break;
//                    case 3:
//                        cell.setBackground(Color.RED); // Power-up
//                        break;
//                    case 4:
//                    	cell.setBackground(Color.GREEN); //cherry image
//                    	break;
//                    case 5: 
//                    	cell.setBackground(Color.PINK); //start area for ghost
//                    	break;
//                    case 6:
//                    	cell.setBackground(Color.GRAY);
//                    	break;
//                    default:
//                        cell.setBackground(Color.WHITE); // Empty space
//                }
//                gameScreen.add(cell);
//            }
//        }
//        gameScreen.validate(); // Validate the layout
//        gameScreen.setVisible(true);
//    }
        
        
    
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
        pacManLabel.setBounds(170, 75, 100, 100);
        startScreen.add(pacManLabel);

     // Calculate x-coordinate to center the button horizontally

     // Add start button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font style and size
        startButton.setForeground(Color.WHITE); // Set text color
        startButton.setBackground(new Color(0, 128, 255)); // Set background color
        startButton.setFocusPainted(false); // Remove focus border
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maze = new Maze();
                showGameScreen();
            }
        });
        startButton.setBounds(140, 185, 150, 40);
        startScreen.add(startButton);

        // Set start screen panel visibility and hide game screen
        startScreen.setVisible(true);
        gameScreen.setVisible(false);
    }




}
