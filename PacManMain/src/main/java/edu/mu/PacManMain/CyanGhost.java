package edu.mu.PacManMain;
import java.util.*;
public class CyanGhost {

	private int x, y; // position
    private Movement direction; // current direction
    private Random random;

    public void Ghost(int startX, int startY) 
    {
    	x = startX;
    	y = startY;
	    direction = Movement.LEFT; // initial direction
	    random = new Random();
    }

    public void move() 
    {
        switch (random.nextInt(4)) 
        {
            case 0:
                direction = Movement.UP;
                break;
            case 1:
                direction = Movement.DOWN;
                break;
            case 2:
                direction = Movement.LEFT;
                break;
            case 3:
                direction = Movement.RIGHT;
                break;
            default:
                break;
        }

        // Update position based on direction
        switch (direction) 
        {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            default:
                break;
        }
        
    }

	    // Getter methods for position
	    public int getX() 
	    {
	        return x;
	    }

	    public int getY() 
	    {
	        return y;
	    }
	}

