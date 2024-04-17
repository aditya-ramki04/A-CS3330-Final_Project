package edu.mu.PacManMain;

public class Pellet {
    private int x;
    private int y;
    private boolean isActive;

    public Pellet(int x, int y) {
        this.x = x;
        this.y = y;
        this.isActive = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return isActive;
    }

    public void consume() {
        this.isActive = false;
    }
}

