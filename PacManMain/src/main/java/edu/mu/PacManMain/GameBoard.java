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
    private int remainingSeconds = 93;
    private int maxScore = 22400;
    
    private JFrame frame;
    

        
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
        contentPanel.setLayout(new OverlayLayout(contentPanel)); 
        createPauseScreen();
        showStartScreen();
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
        
       
   
        timerLabel = new JLabel(formatTime(remainingSeconds), SwingConstants.TRAILING);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        contentPanel.add(timerLabel, BorderLayout.EAST);
        timerLabel.setBounds(0, 315, 100, 30);
        timerLabel.setHorizontalAlignment(SwingConstants.TRAILING); 
 
        frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

                    
        createMaze(maze, mazePanel, pelletCount);


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
       
 
        contentPanel.add(cyanghost.getLabel());
        contentPanel.add(pinkghost.getLabel());
        contentPanel.add(orangeghost.getLabel());
        contentPanel.add(redghost.getLabel());
        contentPanel.add(pacman.getLabel());
        

        contentPanel.add(mazePanel);
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

 
         timer = new Timer(20, new ActionListener() {
            private int ghostIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    SwingUtilities.invokeLater(() -> { 
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

                        ghostIndex = (ghostIndex + 1) % 7;

                        pacman.updatePosition();

                        if (!pacman.isPowerUpActive() && checkPacmanGhostCollision(pacman, new Ghost[]{cyanghost, pinkghost, orangeghost, redghost}, 7)) {
                            handlePacmanGhostCollision(); 
                        }
                        
                        if(maxScore == pacman.getScore())
                        {
                        	System.out.println("You win!!");
                        	 if (timer != null) {
                                 timer.stop(); 
                             }

                             contentPanel.removeAll(); 
                             JPanel gameOverScreen = createGameOverWinScreen(); 
                             contentPanel.add(gameOverScreen); 
                             contentPanel.revalidate();
                             contentPanel.repaint();
                        	
                        }
                    });
                }
            }
        });

        
        
        timer.setInitialDelay(3000);
        timer.start(); 
        
        startTimer();
        
     

    }
    
    /**
     * Creates the maze grid and populates it with maze elements such as walls, pellets,
     * power-ups, and ghosts. Also sets up the timer label and initializes the game frame.
     *
     * @param maze         The Maze object representing the game's maze grid.
     * @param mazePanel    The JPanel where the maze grid will be displayed.
     * @param pelletCount  The initial count of pellets in the maze.
     */
    public void createMaze(Maze maze, JPanel mazePanel, int pelletCount) {
        BufferedImage pelletImg = null;
		try {
			pelletImg = ImageIO.read(new File("images/pellet.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        ImageIcon pelletIcon = new ImageIcon(pelletImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage topwallImg = null;
		try {
			topwallImg = ImageIO.read(new File("images/topwall.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        ImageIcon topwallIcon = new ImageIcon(topwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage toprightwallImg = null;
		try {
			toprightwallImg = ImageIO.read(new File("images/toprightwall.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        ImageIcon toprightwallIcon = new ImageIcon(toprightwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        BufferedImage rightsidewallImg = null;
		try {
			rightsidewallImg = ImageIO.read(new File("images/rightsidewall.png"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        ImageIcon rightsidewallIcon = new ImageIcon(rightsidewallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));

        
        BufferedImage bottomrightwallImg = null;
		try {
			bottomrightwallImg = ImageIO.read(new File("images/bottomrightwall.png"));
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
        ImageIcon bottomrightwallIcon = new ImageIcon(bottomrightwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage bottomwallImg = null;
		try {
			bottomwallImg = ImageIO.read(new File("images/bottomwall.png"));
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
        ImageIcon bottomwallIcon = new ImageIcon(bottomwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage bottomleftwallImg = null;
		try {
			bottomleftwallImg = ImageIO.read(new File("images/bottomleftwall.png"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        ImageIcon bottomleftwallIcon = new ImageIcon(bottomleftwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage topleftwallImg = null;
		try {
			topleftwallImg = ImageIO.read(new File("images/topleftwall.png"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        ImageIcon topleftwallIcon = new ImageIcon(topleftwallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        
        BufferedImage leftsidewallImg = null;
		try {
			leftsidewallImg = ImageIO.read(new File("images/leftsidewall.png"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        ImageIcon leftsidewallIcon = new ImageIcon(leftsidewallImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage strawBerryImg = null;
		try {
			strawBerryImg = ImageIO.read(new File("images/strawberry.png"));
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
        ImageIcon strawBerryIcon = new ImageIcon(strawBerryImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
        
        BufferedImage cherryImg = null;
		try {
			cherryImg = ImageIO.read(new File("images/cherry.png"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        ImageIcon cherryIcon = new ImageIcon(cherryImg.getScaledInstance(37, 39, Image.SCALE_SMOOTH));
  
        for(int i = 0; i < maze.getMapGrid().length; i++) {
        	for(int j = 0; j < maze.getMapGrid()[0].length; j++) {
        		JLabel cell = new JLabel();
        		cell.setOpaque(true);
        		switch (maze.getMapGrid()[i][j]) {
                case 1:
                	cell.setIcon(topwallIcon); 
                    break;
                case 2:
                	cell.setIcon(pelletIcon); 
                	pelletCount++;
                    break;
                case 4:
                    cell.setIcon(cherryIcon); 
                    break;
                case 3:
                	cell.setIcon(strawBerryIcon); 
                	break;
                case 5: 
                	cell.setBackground(Color.BLACK); 
                	break;
                case 16:
                	cell.setIcon(toprightwallIcon); 
                	break;
                case 14:
                	cell.setIcon(rightsidewallIcon); 
                	break;
                case 15:
                	cell.setIcon(bottomrightwallIcon); 
                	break;
                case 10:
                	cell.setIcon(bottomleftwallIcon); 
                	break;
                case 11:
                	cell.setIcon(topleftwallIcon); 
                	break;
                case 12:
                	cell.setIcon(bottomwallIcon); 
                	break;
                case 13:
                	cell.setIcon(leftsidewallIcon); 
                	break;

                default:
                    cell.setBackground(Color.BLACK); 
            }
            mazePanel.add(cell);
        }
        }
    }
    
    /**
     * Handles the collision between Pac-Man and ghosts.
     * Stops the game loop timer, plays the Pac-Man death sound, and displays the game over screen.
     */
    public void handlePacmanGhostCollision() {
        System.out.println("Pacman collided with a ghost! Game over.");
     
        playPacmanDeathSound();
        stopBackgroundMusic();
        
        if (timer != null) {
            timer.stop(); 
        }
        contentPanel.removeAll(); 
        JPanel gameOverScreen = createGameOverScreen(); 
        contentPanel.add(gameOverScreen); 
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Checks for collision between Pac-Man and ghosts.
     *
     * @param pacman       The Pac-Man object.
     * @param ghosts       An array of Ghost objects representing the ghosts in the game.
     * @param safetyMargin The safety margin to consider around the objects for collision detection.
     * @return True if there is a collision between Pac-Man and any of the ghosts, otherwise false.
     */
    public boolean checkPacmanGhostCollision(PacMan pacman, Ghost[] ghosts, int safetyMargin) 
    {
       
        int pacmanX = pacman.getX() - safetyMargin;
        int pacmanY = pacman.getY() - safetyMargin;
        int pacmanSize = pacman.getPacmanSize() + (2 * safetyMargin); 
        Rectangle pacmanBounds = new Rectangle(pacmanX, pacmanY, pacmanSize, pacmanSize);
        for (Ghost ghost : ghosts) 
        {
            int ghostX = ghost.getX() - safetyMargin;
            int ghostY = ghost.getY() - safetyMargin;
            int ghostSize = ghost.getGhostSize() + (2 * safetyMargin); 
            Rectangle ghostBounds = new Rectangle(ghostX, ghostY, ghostSize, ghostSize);
            if (pacmanBounds.intersects(ghostBounds)) 
            {
                return true; 
            }
        }

        	return false; 
    	
    }
    
    /**
     * Starts the countdown timer for the game.
     * Updates the remaining time label every second.
     * Displays the game over screen when the time runs out.
     */
    private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--; 
                timerLabel.setText(formatTime(remainingSeconds)); 

                if (remainingSeconds <= 0) {

                    contentPanel.removeAll(); 

                  
                    JPanel gameOverScreen = createGameOverScreen(); 
                    contentPanel.add(gameOverScreen); 
                   
                    contentPanel.revalidate();
                    contentPanel.repaint();
                    ((Timer) e.getSource()).stop(); 
                }
            }
        });
        timer.start();
    }

    /**
     * Formats the time in seconds to display in minutes and seconds format.
     *
     * @param seconds The time remaining in seconds.
     * @return The formatted time string (e.g., "2:30" for 2 minutes and 30 seconds).
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%2d:%02d", minutes, secs);
    }

    
    /**
     * Creates the pause screen with options to toggle audio, adjust volume, and resume the game.
     * Displays the pause screen when the game is paused.
     */
    private void createPauseScreen() {
        pauseScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int rectWidth = 200; 
                int rectHeight = 400; 
                int x = (getWidth() - rectWidth) / 2; 
                int y = (getHeight() - rectHeight) / 2; 
                g.setColor(new Color(0, 0, 0, 200)); 
                g.fillRect(x, y, rectWidth, rectHeight); 
            }
        };
        pauseScreen.setLayout(null); 
        pauseScreen.setOpaque(false); 
        pauseScreen.setVisible(false); 

      
        audioToggleButton = new JToggleButton(isAudio ? "Audio: OFF" : "Audio: ON");
        audioToggleButton.setBounds(350, 315, 100, 30); 
        audioToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAudio();
            }
        });

       
        JButton resumeButton = new JButton("Resume");
        resumeButton.setBounds(350, 400, 100, 30); 
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
        
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); 
        volumeSlider.setBounds(325, 355, 150, 30); 
        volumeSlider.setBackground(Color.WHITE);
        volumeSlider.setBackground(Color.BLACK);
        
        volumeSlider.setUI(new BasicSliderUI(volumeSlider));
        
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                adjustBackgroundMusicVolume(volumeSlider.getValue());
            }
        });

        pauseScreen.add(audioToggleButton);
        pauseScreen.add(resumeButton);
        pauseScreen.add(volumeSlider);

        contentPanel.add(pauseScreen); 
    }
    
    /**
     * Adjusts the volume of the background music based on the slider value.
     *
     * @param volume The volume level (0-100) set by the user.
     */
    private void adjustBackgroundMusicVolume(int volume) {
        if (bgMusic != null && bgMusic.isOpen()) {
            FloatControl gainControl = (FloatControl) bgMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    private boolean isAudio;
    /**
     * Toggles the audio on/off based on the current state.
     * Stops/starts the background music accordingly.
     */
    private void toggleAudio() {
        isAudio = !isAudio;
        if (isAudio) {
            
            stopBackgroundMusic();
            audioToggleButton.setText("Audio: OFF");
        } else {
           
            playBackgroundMusic();
            audioToggleButton.setText("Audio: ON");
        }
    }
    
    
    private boolean isPaused = false;
    /**
     * Toggles the game pause state.
     * Pauses/resumes the game and displays/hides the pause screen.
     */

    private void togglePause() {
        isPaused = !isPaused;
        pauseScreen.setVisible(isPaused);
        pacman.toggleMovement();

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    /**
     * Displays the start screen with the game title, Pac-Man image, and start button.
     * Starts the background music when the game starts.
     */
    private void showStartScreen() {

        startScreen = new JPanel();
        startScreen.setLayout(new GridBagLayout());
        startScreen.setBackground(new Color(0, 0, 0, 200));

       
        JLabel titleLabel = new JLabel("Welcome to Pac-Man!");
        titleLabel.setFont(new Font("Press Start 2P", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcTitleLabel = new GridBagConstraints();
        gbcTitleLabel.gridx = 0;
        gbcTitleLabel.gridy = 0;
        gbcTitleLabel.insets = new Insets(50, 0, 30, 0);
        startScreen.add(titleLabel, gbcTitleLabel);

       
        ImageIcon pacmanIcon = new ImageIcon("./images/pacman.png");
        JLabel pacmanLabel = new JLabel(pacmanIcon);
        GridBagConstraints gbcPacmanLabel = new GridBagConstraints();
        gbcPacmanLabel.gridx = 0;
        gbcPacmanLabel.gridy = 1;
        gbcPacmanLabel.insets = new Insets(0, 0, 50, 0);
        startScreen.add(pacmanLabel, gbcPacmanLabel);

      
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        startButton.setBackground(Color.YELLOW);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.remove(startScreen);
                contentPanel.revalidate();
                contentPanel.repaint();
                playBackgroundMusic();
                
            }
        });
        GridBagConstraints gbcStartButton = new GridBagConstraints();
        gbcStartButton.gridx = 0;
        gbcStartButton.gridy = 2;
        gbcStartButton.insets = new Insets(0, 0, 50, 0);
        startScreen.add(startButton, gbcStartButton);

        contentPanel.add(startScreen, BorderLayout.CENTER);
    }



    /**
     * Plays the background music for the game.
     */
    private void playBackgroundMusic() {
        try {
           
            File audioFile = new File("./Audio/pacman_beginning.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioStream);
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * Stops the background music playback.
     */
    private void stopBackgroundMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop(); 
            bgMusic.close(); 
        }
    }
    /**
     * Plays the Pac-Man death sound effect.
     */
    private void playPacmanDeathSound() {
        try {
            File audioFile = new File("./Audio/womp_womp.wav");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            Clip pacmanDeathSound = AudioSystem.getClip();
            pacmanDeathSound.open(audioStream);
            
            pacmanDeathSound.setFramePosition(0);
            pacmanDeathSound.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Plays the victory sound effect when the player wins the game.
     */
    private void playWinSound() {
        try {
         
            File audioFile = new File("./Audio/pacman_win.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

           
            Clip winSound = AudioSystem.getClip();
            winSound.open(audioStream);


            winSound.start();

           
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates the game over screen with options to quit the game.
     *
     * @return The JPanel representing the game over screen.
     */
    public JPanel createGameOverScreen() {
        JPanel gameOverScreen = new JPanel(new BorderLayout());
        gameOverScreen.setBackground(Color.BLACK);

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        gameOverScreen.add(gameOverLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

      

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.setBackground(Color.WHITE);
        quitButton.setForeground(Color.BLACK);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(quitButton);

        gameOverScreen.add(buttonPanel, BorderLayout.CENTER);

        return gameOverScreen;
    }

    /**
     * Creates the game over screen for the win condition with options to quit the game.
     *
     * @return The JPanel representing the win screen.
     */
    public JPanel createGameOverWinScreen() {
    	stopBackgroundMusic();
    	playWinSound();
        JPanel gameOverScreen = new JPanel(new BorderLayout());
        gameOverScreen.setBackground(Color.BLACK);

        JLabel gameOverLabel = new JLabel("Congratulations, You Won!");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.GREEN);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        gameOverScreen.add(gameOverLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

      

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.setBackground(Color.WHITE);
        quitButton.setForeground(Color.BLACK);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));


        gameOverScreen.add(buttonPanel, BorderLayout.CENTER);

        return gameOverScreen;
    }

   
}



