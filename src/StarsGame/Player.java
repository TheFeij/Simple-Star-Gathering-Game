package StarsGame;

import java.util.ArrayList;

public class Player extends Element {
    private int stars;
    private final ArrayList<Integer> limits;

    public Player(int xPos, int yPos){
        super (xPos ,yPos);
        stars = 0;
        limits = new ArrayList<>();
    }

    public int getStars() {
        return stars;
    }

    public ArrayList<Integer> getLimits() {
        return limits;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void addLimitAmount(int limitAmount) {
        limits.add(limitAmount);
    }
}
