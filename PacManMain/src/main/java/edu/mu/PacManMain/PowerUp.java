package edu.mu.PacManMain;

//import java.awt.Color;
//import java.awt.Component;
//
//import javax.swing.JLabel;

public class PowerUp {

	public PowerUp() {
		// TODO Auto-generated constructor stub
	}
//	 public void eatPowerUp(int row, int col) {
//	        System.out.println("Checking pellet at row: " + row + ", col: " + col);
//	        if (maze.getMapGrid()[row][col] == 3) { // Check if Pacman's position corresponds to a pellet
//	            maze.getMapGrid()[row][col] = 0; // Remove the pellet from the maze grid
//	            System.out.println("Pellet eaten at row: " + row + ", col: " + col);
//	            score+= 100;
//	            // Update the appearance of the corresponding JLabel in the maze panel to represent an empty space (black square)
//	            Component[] components = mazePanel.getComponents();
//	            int cellIndex = row * maze.getMapGrid()[0].length + col;
//	            if (cellIndex >= 0 && cellIndex < components.length) {
//	                JLabel cellLabel = (JLabel) components[cellIndex];
//	                cellLabel.setBackground(Color.BLACK);
//	                cellLabel.setIcon(null); // Clear any existing icon
//	            } else {
//	                System.out.println("Invalid cell index: " + cellIndex);
//	            }
//	            System.out.println("Your current score " + score);
//
//	            // You can also increment a score counter or perform any other necessary actions here
//	        } else {
//	            System.out.println("No pellet found at row: " + row + ", col: " + col);
//	        }
//	    }
}
