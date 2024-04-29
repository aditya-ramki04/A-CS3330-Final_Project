package edu.mu.PacManMain;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel;
    private PacMan pacman;
    private Maze maze;
    private JPanel startScreen;
    private JPanel pauseScreen;
    
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

    public GameBoard() {
    	
    	//DO NOT TOUCH NITIN
    	
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 819, 850);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(new OverlayLayout(contentPanel)); // Using absolute positioning


        // Initialize Pacman
        
        showStartScreen();
        
        int[][] mapGrid = {
        		{11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 16},
    		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {13, 3, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 3, 14},
    		    {13, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 14},
    		    {13, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {13, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 1, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 2, 2, 14},
    		    {13, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {6, 2, 1, 2, 1, 1, 1, 2, 1, 5, 5, 5, 1, 2, 1, 1, 1, 1, 1, 2, 6},
    		    {13, 2, 1, 2, 2, 2, 1, 2, 1, 5, 5, 5, 1, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {13, 2, 1, 2, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 1, 2, 1, 1, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 1, 2, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {13, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 2, 14},
    		    {13, 3, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 3, 14},
    		    {13, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 15}
    		    };
        maze = new Maze(mapGrid);
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(maze.getMapGrid().length, maze.getMapGrid()[0].length));

        
        BufferedImage pelletImg = null;
		try {
			pelletImg = ImageIO.read(new File("images/pellet.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon pelletIcon = new ImageIcon(pelletImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage topwallImg = null;
		try {
			topwallImg = ImageIO.read(new File("images/topwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon topwallIcon = new ImageIcon(topwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage toprightwallImg = null;
		try {
			toprightwallImg = ImageIO.read(new File("images/toprightwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon toprightwallIcon = new ImageIcon(toprightwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        BufferedImage rightsidewallImg = null;
		try {
			rightsidewallImg = ImageIO.read(new File("images/rightsidewall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon rightsidewallIcon = new ImageIcon(rightsidewallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));

        
        BufferedImage bottomrightwallImg = null;
		try {
			bottomrightwallImg = ImageIO.read(new File("images/bottomrightwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon bottomrightwallIcon = new ImageIcon(bottomrightwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage bottomwallImg = null;
		try {
			bottomwallImg = ImageIO.read(new File("images/bottomwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon bottomwallIcon = new ImageIcon(bottomwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage bottomleftwallImg = null;
		try {
			bottomleftwallImg = ImageIO.read(new File("images/bottomleftwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon bottomleftwallIcon = new ImageIcon(bottomleftwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage topleftwallImg = null;
		try {
			topleftwallImg = ImageIO.read(new File("images/topleftwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon topleftwallIcon = new ImageIcon(topleftwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage leftsidewallImg = null;
		try {
			leftsidewallImg = ImageIO.read(new File("images/leftsidewall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon leftsidewallIcon = new ImageIcon(leftsidewallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        // Add colored cells to the maze panel
        for(int i = 0; i < maze.getMapGrid().length; i++) {
        	for(int j = 0; j < maze.getMapGrid()[0].length; j++) {
        		JLabel cell = new JLabel();
        		cell.setOpaque(true);
        		switch (maze.getMapGrid()[i][j]) {
                case 1:
                	cell.setIcon(topwallIcon); // Wall
                    break;
                case 2:
                	cell.setIcon(pelletIcon); // Pellet
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
                case 16:
                	cell.setIcon(toprightwallIcon); // Pellet
                	break;
                case 14:
                	cell.setIcon(rightsidewallIcon); // Pellet
                	break;
                case 15:
                	cell.setIcon(bottomrightwallIcon); // Pellet
                	break;
                case 10:
                	cell.setIcon(bottomleftwallIcon); // Pellet
                	break;
                case 11:
                	cell.setIcon(topleftwallIcon); // Pellet
                	break;
                case 12:
                	cell.setIcon(bottomwallIcon); // Pellet
                	break;
                case 13:
                	cell.setIcon(leftsidewallIcon); // Pellet
                	break;
                default:
                    cell.setBackground(Color.WHITE); // Empty space
            }
            mazePanel.add(cell);
        }
        }

        // Add the mazePanel to the contentPanel
       // Initialize Pacman
        pacman = new PacMan("images/pacmanrightopen.png", maze, 38);
        pacman.setPosition(350, 200);     
        
       

        contentPanel.add(pacman.getLabel());
        // Add Pacman's label to the contentPane
        //contentPanel.add(pacman.getLabel());
        
        contentPanel.add(mazePanel);

        // Add key listener to move Pacman
        contentPanel.setFocusable(true);
        contentPanel.requestFocus();
        contentPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
            	if(evt.getKeyCode() == KeyEvent.VK_P) {
            		togglePause();
            	} else {
            		pacman.move(evt);
            	}
            }
        });

        // Game loop using Timer
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!isPaused) {
            		// Update Pacman's position
                    pacman.updatePosition();
            	}
            }
        });
        timer.start();
    }
    
    private void createPauseScreen() {
        pauseScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 128)); // Semi-transparent black color
                g.fillRect(0, 0, getWidth(), getHeight()); // Fill the entire panel
            }
        };
        pauseScreen.setOpaque(false); // Make the panel transparent
        pauseScreen.setVisible(false); // Initially hide the pause screen
        contentPanel.add(pauseScreen); // Add the pause screen panel to the content panel
    }

    private boolean isPaused = false;
    
    private void togglePause() {
        isPaused = !isPaused;
        pauseScreen.setVisible(isPaused);
        pacman.toggleMovement();
        
    }
 
    private void showStartScreen() {
        startScreen = new JPanel();
        startScreen.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Pac-Man!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startScreen.add(titleLabel, BorderLayout.NORTH);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(Color.GREEN); // Customize background color
        startButton.setForeground(Color.WHITE); // Customize foreground (text) color
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Customize border
        startButton.setFocusPainted(false); // Remove focus border
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game by removing the start screen and showing the game board
                contentPanel.remove(startScreen);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startScreen.add(startButton, BorderLayout.CENTER);

        contentPanel.add(startScreen);
    }
}

