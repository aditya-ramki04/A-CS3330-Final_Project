package edu.mu.PacManMain;

public class PacMan {
	
	private int x;
	private int y;
	public PacMan(int startX, int startY) {
		// TODO Auto-generated constructor stub
		this.x = startX;
		this.y = startY;
	}
	
	public void movePac(Movement direction, GameBoard gboard) {
		int newX = x;
		int newY = y;
		if(direction == Movement.UP)
		{
			newY++;
		}
		if(direction == Movement.DOWN)
		{
			newY--;
		}
		if(direction == Movement.LEFT)
		{
			newX--;
		}
		if(direction == Movement.RIGHT)
		{
			newX++;
		}

	}

}
