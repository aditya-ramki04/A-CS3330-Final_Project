package edu.mu.PacManMain;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameBoard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel startScreen;
    private JPanel gameScreen;
    private Maze maze;
    private PacMan pacMan;
    private Clip bgMusic;
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
    private boolean isAudioMuted = false;
    
    private void toggleAudio() {
    	isAudioMuted = !isAudioMuted;
    	if(isAudioMuted) {
            stopBackgroundMusic();
    	}else {
    		playBackgroundMusic();
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
        setBounds(100, 100, 800, 800); //450,300 matthew said my limit was 800 :(
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));
        
        // Create start screen panel
        startScreen = new JPanel();
        startScreen.setBackground(Color.BLACK);
        contentPane.add(startScreen, "name_13981319384000");
        
        gameScreen = new JPanel();
        gameScreen.setBackground(Color.WHITE); // Change color for visibility
        

        
        maze = new Maze();
        pacMan = new PacMan(40, 40); // Set initial position (1, 1) or any other position you want
        gameScreen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int changeX = 0, changeY = 0;

                // Determine direction based on key pressed
                if (keyCode == KeyEvent.VK_LEFT) {
                    changeX = -1; // Move left
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    changeX = 1; // Move right
                } else if (keyCode == KeyEvent.VK_UP) {
                    changeY = -1; // Move up
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    changeY = 1; // Move down
                }

                // Move Pac-Man
                pacMan.movePac(changeX, changeY, maze);

                // Repaint game screen to reflect Pac-Man's new position
                gameScreen.repaint();
            }
        });

        
        gameScreen.setFocusable(true);
        gameScreen.requestFocusInWindow();
        
        // Request focus for the game screen to ensure key events are captured

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
//        pauseIcon = new ImageIcon("./images/pause.png");
//        playIcon = new ImageIcon("./images/play.png");  
        //nmc
        
        // Initially show the start screen
        showStartScreen();
    }
    
    private void showGameScreen() {
    	startScreen.setVisible(false);
        gameScreen.removeAll(); // Clear previous content
        gameScreen.setLayout(new BorderLayout());
         
        ImageIcon pacManIcon = new ImageIcon("images/pacmanrightopen.png");
        JLabel pacManLabel = new JLabel(pacManIcon);
        
        // Calculate Pac-Man's position relative to the maze grid
        int pacManGridX = pacMan.getX(); // Assuming Pac-Man's position is represented in grid coordinates
        int pacManGridY = pacMan.getY();
        int cellSize = 10; // Assuming each cell in the grid is 36x36 pixels

        // Calculate the actual position of Pac-Man within the grid layout
        int pacManX = pacManGridX * cellSize;
        int pacManY = pacManGridY * cellSize;

        // Set the position of Pac-Man
        pacManLabel.setBounds(pacManX, pacManY, 100, 50); 

        // Add Pac-Man to the game screen
        gameScreen.add(pacManLabel);

        //gameScreen.setLayout(new GridLayout(maze.getGrid().length, maze.getGrid()[0].length)); // Grid layout based on maze size
        
        //nmc
        //play/pause button
       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       JButton pauseButton = new JButton("Pause");
       //JButton pauseButton = new JButton();
       pauseButton.setText("Pause");   
       pauseButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		togglePause();//changes pause state when button is clicked
        		pauseButton.setText(isPaused ? "Play" : "Pause"); //update button text on state
//        		if(isPaused) {
//        			pauseButton.setIcon(playIcon);
//        		}else {
//        			pauseButton.setIcon(pauseIcon);
//        		}
        	}
        });
        
        //audio control button
        JButton audioButton = new JButton();
        audioButton.setText("Audio: ON");
        audioButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		toggleAudio();
        		audioButton.setText(isAudioMuted ? "Audio: OFF" : "Audio: ON");
        	}
        });
        buttonPanel.add(pauseButton);        
        buttonPanel.add(audioButton);
        gameScreen.add(buttonPanel, BorderLayout.NORTH);
//
        //images for the map
        BufferedImage pelletImg = null;
		try {
			pelletImg = ImageIO.read(new File("images/pellet.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon pelletIcon = new ImageIcon(pelletImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        
        BufferedImage topwallImg = null;
		try {
			topwallImg = ImageIO.read(new File("images/topwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon topwallIcon = new ImageIcon(topwallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
        BufferedImage toprightwallImg = null;
		try {
			toprightwallImg = ImageIO.read(new File("images/toprightwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon toprightwallIcon = new ImageIcon(toprightwallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
    
        BufferedImage rightsidewallImg = null;
		try {
			rightsidewallImg = ImageIO.read(new File("images/rightsidewall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon rightsidewallIcon = new ImageIcon(rightsidewallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));

        
        BufferedImage bottomrightwallImg = null;
		try {
			bottomrightwallImg = ImageIO.read(new File("images/bottomrightwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon bottomrightwallIcon = new ImageIcon(bottomrightwallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
        
        BufferedImage bottomwallImg = null;
		try {
			bottomwallImg = ImageIO.read(new File("images/bottomwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon bottomwallIcon = new ImageIcon(bottomwallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
        
        BufferedImage bottomleftwallImg = null;
		try {
			bottomleftwallImg = ImageIO.read(new File("images/bottomleftwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon bottomleftwallIcon = new ImageIcon(bottomleftwallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
        
        BufferedImage topleftwallImg = null;
		try {
			topleftwallImg = ImageIO.read(new File("images/topleftwall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon topleftwallIcon = new ImageIcon(topleftwallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
        
        BufferedImage leftsidewallImg = null;
		try {
			leftsidewallImg = ImageIO.read(new File("images/leftsidewall.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ImageIcon leftsidewallIcon = new ImageIcon(leftsidewallImg.getScaledInstance(36, 36, Image.SCALE_SMOOTH));
        
        
        JPanel gridPanel = new JPanel(new GridLayout(maze.getGrid().length, maze.getGrid()[0].length)); // Grid layout based on maze size
        for(int i = 0; i < maze.getGrid().length; i++) {
        	for(int j = 0; j < maze.getGrid()[0].length; j++) {
        		JLabel cell = new JLabel();
        		cell.setOpaque(true);
        		switch (maze.getGrid()[i][j]) {
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
                case 7:
                	cell.setIcon(toprightwallIcon); // Pellet
                	break;
                case 8:
                	cell.setIcon(rightsidewallIcon); // Pellet
                	break;
                case 9:
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
            gridPanel.add(cell);
        }
    }
        gameScreen.add(gridPanel, BorderLayout.CENTER);
        
        
        
      //  gameScreen.add(pacManLabel);
        
        gameScreen.validate();
        gameScreen.setVisible(true);
        
        
    }
    //nmc
        
        

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
                playBackgroundMusic();
            }
        });
        startButton.setBounds(140, 185, 150, 40);
        startScreen.add(startButton);

        // Set start screen panel visibility and hide game screen
        startScreen.setVisible(true);
        gameScreen.setVisible(false);
    }
    private void playBackgroundMusic() {
        try {
            File audioFile = new File("./Audio/pacman_beginning.wav"); // Change the path to your audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioStream);
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop();
            bgMusic.close();
        }
    }



}
