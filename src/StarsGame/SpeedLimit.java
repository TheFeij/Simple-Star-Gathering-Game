package StarsGame;

public class SpeedLimit extends Element {
    private int limitAmount;

    public SpeedLimit(int xPos, int yPos, int limitAmount) {
        super(xPos, yPos);
        setLimitAmount(limitAmount);
    }

    public void setLimitAmount(int limitAmount) {
        this.limitAmount = limitAmount;
    }

    public int getLimitAmount() {
        return limitAmount;
    }
}
