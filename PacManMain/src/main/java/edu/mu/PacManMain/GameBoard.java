package edu.mu.PacManMain;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel;
    private PacMan pacman;
    private Maze maze;
    private CyanGhost cyanghost;
    private PinkGhost pinkghost;
    private OrangeGhost orangeghost;
    private RedGhost redghost;
    private int cellSize;
    private Timer timer;
    private Clip pacmanDeathSound;
 
    private ArrayList<Pellet> pellets = new ArrayList<>();
    
    private JPanel startScreen;
    private JPanel pauseScreen;
    private Clip bgMusic;
    private JToggleButton audioToggleButton;
    
    private JLabel timerLabel;
    private int remainingSeconds = 123;
    private int maxScore = 10000;
    
    private JFrame frame;
    private ScorePanel scorePanel;

    
    //maxScore is 22,400

    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new GameBoard());

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
        loadPacmanDeathSound();
        int pelletCount= 0;
       
        int[][] mapGrid = {
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
        maze = new Maze(mapGrid);
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(maze.getMapGrid().length, maze.getMapGrid()[0].length));
        
        // Add timer label to the contentPanel
        timerLabel = new JLabel(formatTime(remainingSeconds), SwingConstants.TRAILING);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        contentPanel.add(timerLabel, BorderLayout.EAST);
        timerLabel.setBounds(0, 315, 100, 30);
        timerLabel.setHorizontalAlignment(SwingConstants.TRAILING); 
 
        frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create score panel
        scorePanel = new ScorePanel();
        
        // Add score panel to frame
        frame.getContentPane().add(scorePanel, BorderLayout.NORTH);
        
        frame.pack();
        frame.setVisible(true);
        
        JLabel scoreLabel = new JLabel();
        
        //created separate method for creating the maze
        createMaze(maze, mazePanel, pelletCount);

        System.out.println("pellet count "+ pelletCount);

        // Add the mazePanel to the contentPanel
       // Initialize Pacman
        this.pacman = new PacMan("images/pacmanrightopen.png", maze, mazePanel, cellSize);

        pacman.setPosition(387, 200);   
        
        cyanghost = new CyanGhost(maze, 30);
        cyanghost.setPosition(52,40);
        
        pinkghost = new PinkGhost(maze, 30);
        pinkghost.setPosition(725,45);
        
        orangeghost = new OrangeGhost(maze, 30);
        orangeghost.setPosition(52,739);

        redghost = new RedGhost(maze, 30);
        redghost.setPosition(725,725);
       
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

     // Timer-based game loop with collision detection
         timer = new Timer(20, new ActionListener() {
            private int ghostIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    SwingUtilities.invokeLater(() -> { // Ensure thread safety
                        // Move and update ghosts
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

                        // Increment the ghost index
                        ghostIndex = (ghostIndex + 1) % 7;

                        // Update Pacman's position
                        pacman.updatePosition();

                        // Check for Pacman-ghost collisions
                        if (!pacman.isPowerUpActive() && checkPacmanGhostCollision(pacman, new Ghost[]{cyanghost, pinkghost, orangeghost, redghost}, 7)) {
                            handlePacmanGhostCollision(); // Handle the collision response only if power-up is not active
                        }
                        
                        if(maxScore == pacman.getScore())
                        {
                        	System.out.println("You win!!");
                        	 if (timer != null) {
                                 timer.stop(); // Stop the game timer
                             }

                             // Remove all components from the content panel
                             contentPanel.removeAll(); // Clear the current content

                             // Create and add the "Game Over" screen
                             JPanel gameOverScreen = createGameOverWinScreen(); // Create the "Game Over" panel
                             contentPanel.add(gameOverScreen); // Add it to the content panel
                             
                             // Revalidate and repaint to update the GUI
                             contentPanel.revalidate();
                             contentPanel.repaint();
                        	
                        }
                    });
                }
            }
        });

        
        
        timer.setInitialDelay(3000); // Adjust the delay as needed
        timer.start(); // Start the game loop
        
        startTimer();
        
     

    }
    
    public void createMaze(Maze maze, JPanel mazePanel, int pelletCount) {
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
        
        BufferedImage strawBerryImg = null;
		try {
			strawBerryImg = ImageIO.read(new File("images/strawberry.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon strawBerryIcon = new ImageIcon(strawBerryImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage cherryImg = null;
		try {
			cherryImg = ImageIO.read(new File("images/cherry.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon cherryIcon = new ImageIcon(cherryImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
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
                	pelletCount++;
                    break;
                case 4:
                    cell.setIcon(cherryIcon); // Power-up
                    break;
                case 3:
                	cell.setIcon(strawBerryIcon); //cherry image
                	break;
                case 5: 
                	cell.setBackground(Color.BLACK); //start area for ghost
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
    }
    
    
    public void handlePacmanGhostCollision() {
        System.out.println("Pacman collided with a ghost! Game over.");
        playPacmanDeathSound();
        stopBackgroundMusic();
        
        if (timer != null) {
            timer.stop(); // Stop the game timer
        }

        // Remove all components from the content panel
        contentPanel.removeAll(); // Clear the current content

        // Create and add the "Game Over" screen
        JPanel gameOverScreen = createGameOverScreen(); // Create the "Game Over" panel
        contentPanel.add(gameOverScreen); // Add it to the content panel
        
        // Revalidate and repaint to update the GUI
        contentPanel.revalidate();
        contentPanel.repaint();

        //System.out.println("Pacman collided with a ghost!");
        // Implement collision handling logic, like losing a life, resetting Pacman's position, or ending the game
    }

    
    public boolean checkPacmanGhostCollision(PacMan pacman, Ghost[] ghosts, int safetyMargin) 
    {
        // Define Pacman's bounds with a safety margin
        int pacmanX = pacman.getX() - safetyMargin;
        int pacmanY = pacman.getY() - safetyMargin;
        int pacmanSize = pacman.getPacmanSize() + (2 * safetyMargin); // Adjust for the margin
        Rectangle pacmanBounds = new Rectangle(pacmanX, pacmanY, pacmanSize, pacmanSize);

        // Loop through each ghost and check for collisions
        for (Ghost ghost : ghosts) 
        {
            int ghostX = ghost.getX() - safetyMargin;
            int ghostY = ghost.getY() - safetyMargin;
            int ghostSize = ghost.getGhostSize() + (2 * safetyMargin); // Adjust for the margin
            Rectangle ghostBounds = new Rectangle(ghostX, ghostY, ghostSize, ghostSize);

            // Check if Pacman's bounds intersect with the ghost's bounds
            if (pacmanBounds.intersects(ghostBounds)) 
            {
                return true; // Collision detected
            }
        }

        	return false; // No collision
    	
    }
    
    
    private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--; // Decrement remaining time
                timerLabel.setText(formatTime(remainingSeconds)); // Update timer label text

                handleEndTimer(e);
            }
        });
        timer.start();
    }
    
    private void handleEndTimer(ActionEvent e) {
    	if (remainingSeconds <= 0) {
        	// Remove all components from the content panel
            contentPanel.removeAll(); // Clear the current content

            // Create and add the "Game Over" screen
            JPanel gameOverScreen = createGameOverScreen(); // Create the "Game Over" panel
            contentPanel.add(gameOverScreen); // Add it to the content panel
            
            // Revalidate and repaint to update the GUI
            contentPanel.revalidate();
            contentPanel.repaint();
            ((Timer) e.getSource()).stop(); // Stop the timer
        }

    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%2d:%02d", minutes, secs);
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
        resumeButton.setBounds(350, 400, 100, 30); // Set button position and size
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
        
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // Volume range from 0 to 100, starting at 50
        volumeSlider.setBounds(325, 355, 150, 30); // Set slider position and size
        volumeSlider.setBackground(Color.WHITE);
        volumeSlider.setBackground(Color.BLACK);
        
        volumeSlider.setUI(new BasicSliderUI(volumeSlider));
        
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                adjustBackgroundMusicVolume(volumeSlider.getValue());
            }
        });

        // Add toggle button and resume button to the pause screen panel
        pauseScreen.add(audioToggleButton);
        pauseScreen.add(resumeButton);
        pauseScreen.add(volumeSlider);

        contentPanel.add(pauseScreen); // Add the pause screen panel to the content panel
    }
    
    private void adjustBackgroundMusicVolume(int volume) {
        if (bgMusic != null && bgMusic.isOpen()) {
            FloatControl gainControl = (FloatControl) bgMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
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
    
    private void loadPacmanDeathSound() {
        try {
            // Load audio file
            File audioFile = new File("./Audio/pacman_death.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Create clip and open audio stream
            pacmanDeathSound = AudioSystem.getClip();
            pacmanDeathSound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void playPacmanDeathSound() {
        if (pacmanDeathSound != null) {
            pacmanDeathSound.setFramePosition(0); // Rewind the sound to the beginning
            pacmanDeathSound.start(); // Start playing the sound
        }
    }
    
    
    public JPanel createGameOverScreen() {
        JPanel gameOverScreen = new JPanel();
        gameOverScreen.setLayout(new BorderLayout()); // Using BorderLayout for simple layout management

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Large bold font for emphasis
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        gameOverScreen.add(gameOverLabel, BorderLayout.CENTER); // Add the label to the center

        JPanel buttonPanel = new JPanel(); // Panel for buttons
        buttonPanel.setLayout(new FlowLayout()); // Simple flow layout

        JButton restartButton = new JButton("Restart");
    
        buttonPanel.add(restartButton); // Add the restart button to the panel

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0)); // Exit the game
        buttonPanel.add(quitButton); // Add the quit button to the panel

        gameOverScreen.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom

        return gameOverScreen; // Return the complete "Game Over" panel
    }
    
    public JPanel createGameOverWinScreen() {
        JPanel gameOverScreen = new JPanel();
        gameOverScreen.setLayout(new BorderLayout()); // Using BorderLayout for simple layout management

        JLabel gameOverLabel = new JLabel("Congrats, you won!");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Large bold font for emphasis
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        gameOverScreen.add(gameOverLabel, BorderLayout.CENTER); // Add the label to the center

        JPanel buttonPanel = new JPanel(); // Panel for buttons
        buttonPanel.setLayout(new FlowLayout()); // Simple flow layout

        JButton restartButton = new JButton("Restart");
    
        buttonPanel.add(restartButton); // Add the restart button to the panel

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0)); // Exit the game
        buttonPanel.add(quitButton); // Add the quit button to the panel

        gameOverScreen.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom

        return gameOverScreen; // Return the complete "Game Over" panel
    }
    
    



}

