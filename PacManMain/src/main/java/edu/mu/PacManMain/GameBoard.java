package edu.mu.PacManMain;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
    private CyanGhost cyanghost;
    private PinkGhost pinkghost;
    private OrangeGhost orangeghost;
    private RedGhost redghost;
    
    private JPanel startScreen;
    private JPanel pauseScreen;
    private Clip bgMusic;
    private JToggleButton audioToggleButton;

    
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
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 819, 850);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(new OverlayLayout(contentPanel)); // Using absolute positioning

        // Initialize Pacman
        createPauseScreen();
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
    		    {13, 2, 1, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 2, 1, 1, 1, 2, 1, 2, 14},
    		    {13, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {6, 2, 1, 2, 1, 1, 1, 2, 1, 5, 5, 5, 1, 2, 1, 1, 1, 1, 1, 2, 6},
    		    {13, 2, 1, 2, 2, 2, 1, 2, 1, 5, 5, 5, 1, 2, 2, 2, 2, 2, 2, 2, 14},
    		    {13, 2, 1, 2, 1, 2, 2, 2, 1, 1, 5, 1, 1, 2, 1, 1, 1, 2, 1, 2, 14},
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
                	cell.setBackground(Color.BLACK); //start area for ghost
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
                    cell.setBackground(Color.BLACK); // Empty space
            }
            mazePanel.add(cell);
        }
        }

        // Add the mazePanel to the contentPanel
       // Initialize Pacman
        pacman = new PacMan("images/pacmanrightopen.png", maze, 38);
        pacman.setPosition(350, 200);   
        
        cyanghost = new CyanGhost(maze, 30);
        cyanghost.setPosition(350,200);
        
        pinkghost = new PinkGhost(maze, 30);
        pinkghost.setPosition(350,200);
        
        orangeghost = new OrangeGhost(maze, 30);
        orangeghost.setPosition(350,200);

        redghost = new RedGhost(maze, 30);
        redghost.setPosition(350,200);
       
     // Add Pacman's label to the contentPane        
        contentPanel.add(cyanghost.getLabel());
        contentPanel.add(pinkghost.getLabel());
        contentPanel.add(orangeghost.getLabel());
        contentPanel.add(redghost.getLabel());
        contentPanel.add(pacman.getLabel());
        

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
        Timer timer = new Timer(20, new ActionListener() 
        {
        	private int ghostIndex = 0;
        	
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	if (!isPaused) 
            	{
                    // Check which ghost to move and update
                    switch (ghostIndex) {
                        case 0:
                            cyanghost.move();
                            cyanghost.updatePosition();
                            break;
                        case 1:
                            pinkghost.move();
                            pinkghost.updatePosition();
                            break;
                        case 2:
                            orangeghost.move();
                            orangeghost.updatePosition();
                            break;
                        case 3:
                            redghost.move();
                            redghost.updatePosition();
                            break;
                    }
                    // Increment the ghost index to move to the next ghost in the next iteration
                    ghostIndex = (ghostIndex + 1) % 10;
                    
                 // Update Pacman's position
                    pacman.updatePosition();
            }
            }
        });
        timer.setInitialDelay(3000);
        timer.start();
    }
    
    private void createPauseScreen() {
        pauseScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int rectWidth = 200; // Width of the rectangle
                int rectHeight = 400; // Height of the rectangle
                int x = (getWidth() - rectWidth) / 2; // X coordinate of the top-left corner of the rectangle
                int y = (getHeight() - rectHeight) / 2; // Y coordinate of the top-left corner of the rectangle
                g.setColor(new Color(0, 0, 0, 200)); // Darker semi-transparent black color
                g.fillRect(x, y, rectWidth, rectHeight); // Draw the rectangle
            }
        };
        pauseScreen.setLayout(null); // Use null layout to manually position components
        pauseScreen.setOpaque(false); // Make the panel transparent
        pauseScreen.setVisible(false); // Initially hide the pause screen

        // Create toggle button for audio
        audioToggleButton = new JToggleButton(isAudio ? "Audio: OFF" : "Audio: ON");
        audioToggleButton.setBounds(350, 315, 100, 30); // Set button position and size
        audioToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAudio();
            }
        });

        // Create resume button
        JButton resumeButton = new JButton("Resume");
        resumeButton.setBounds(350, 350, 100, 30); // Set button position and size
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });

        // Add toggle button and resume button to the pause screen panel
        pauseScreen.add(audioToggleButton);
        pauseScreen.add(resumeButton);

        contentPanel.add(pauseScreen); // Add the pause screen panel to the content panel
    }




    private boolean isAudio;
    
    private void toggleAudio() {
        isAudio = !isAudio;
        if (isAudio) {
            // Stop background music
            stopBackgroundMusic();
            audioToggleButton.setText("Audio: OFF");
        } else {
            // Play background music
            playBackgroundMusic();
            audioToggleButton.setText("Audio: ON");
        }
    }
    
    
    private boolean isPaused = false;
    
    private void togglePause() {
        isPaused = !isPaused;
        pauseScreen.setVisible(isPaused);
        pacman.toggleMovement();

        contentPanel.revalidate();
        contentPanel.repaint();
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
        startButton.setForeground(Color.BLACK); // Customize foreground (text) color
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Customize border
        startButton.setFocusPainted(false); // Remove focus border
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game by removing the start screen and showing the game board
                contentPanel.remove(startScreen);
                contentPanel.revalidate();
                contentPanel.repaint();
                
                playBackgroundMusic();
            }
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startScreen.add(startButton, BorderLayout.CENTER);

        contentPanel.add(startScreen);
    }
    
    private void playBackgroundMusic() {
        try {
            // Load audio file
            File audioFile = new File("./Audio/pacman_beginning.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Create clip and open audio stream
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioStream);

            // Loop the background music continuously
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop(); // Stop playing the music
            bgMusic.close(); // Close the clip to release system resources
        }
    }


}

