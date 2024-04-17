package edu.mu.PacManMain;

public class ScoreTracking 
{
	private int score;
	private static int highScore = 0;

    public ScoreTracking() 
    {
        this.score = 0;
    }

    public void eatDot() 
    {
        score += 10;
    }

    public void eatPowerPellet() 
    {
        score += 50;
    }

    public void eatGhost(int consecutiveGhostsEaten) 
    {
        switch (consecutiveGhostsEaten) 
        {
            case 1:
                score += 100;
                break;
            case 2:
                score += 200;
                break;
            case 3:
                score += 400;
                break;
            case 4:
                score += 800;
                break;
            default:
                break;
        }
    }

    public int getScore() 
    {
        return score;
    }
    
 // Method to handle game over
    public void gameOver() {
        if (score > highScore) {
            highScore = score;
        }
        System.out.println("Game Over");
        System.out.println("Your Score: " + score);
        System.out.println("High Score: " + highScore);
    }
}
