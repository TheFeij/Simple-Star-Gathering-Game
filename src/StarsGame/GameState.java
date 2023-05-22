package StarsGame;

import java.util.ArrayList;
import java.util.Iterator;

public class GameState {

    private final ArrayList<Wall> walls;
    private ArrayList<Star> stars;
    private ArrayList<SpeedLimit> speedLimits;
    private String turn;
    private Player redPlayer, bluePlayer;

    public GameState() {
        walls = new ArrayList<>();
        stars = new ArrayList<>();
        speedLimits = new ArrayList<>();
        redPlayer = new Player(-1, -1);
        bluePlayer = new Player(-2, -2);
        turn = "red";
    }

    // Getters///////////////////////////
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Star> getStars() {
        return stars;
    }

    public ArrayList<SpeedLimit> getSpeedLimits() {
        return speedLimits;
    }

    public String getTurn() {
        return turn;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public boolean isGameOver() { // be ma mige aya bazi tamoom shode ya na? bar asas tedad star --> star=0 finish
        return stars.size() == 0;
    }
    //////////////////////////////////////////////

    // setters/////////////////////////////////////
    public void setTurn(String turn) {
        this.turn = turn;
    }

    public void setRedPlayer(Player redPlayer) {
        this.redPlayer = redPlayer;
    }

    public void setBluePlayer(Player bluePlayer) {
        this.bluePlayer = bluePlayer;
    }


    public boolean checkLocation(int i, int j) { // yek i , j migire va mige aya dar oon i , j element hast ya na?
        for (Star star : stars) {
            if (star.getX() == i && star.getY() == j) // agar dar oon khoone star bashe
                return false;
        }
        for (Wall wall : walls) {
            if (wall.getX() == i && wall.getY() == j)// agar dar oon khoone wall bashe
                return false;
        }
        for (SpeedLimit speedLimit : speedLimits) {
            if (speedLimit.getX() == i && speedLimit.getY() == j) // agar dar oon khoone speed limit bashe
                return false;
        }
        if (redPlayer.getX() == i && redPlayer.getY() == j) // agar dar oon khoone red player bashe
            return false;
        // agar dar oon khoone blue player bashe
        return bluePlayer.getX() != i || bluePlayer.getY() != j;// agar chizi nabashe
    }
    ////////////////////////////////////////////////////////

    public boolean checkMove(int row, int column) {

        Player player;
        if (turn.equals("red"))
            player = redPlayer;
        else
            player = bluePlayer;

        if (redPlayer.getX() == row && redPlayer.getY() == column) {
            return false;
        }
        if (bluePlayer.getX() == row && bluePlayer.getY() == column) {
            return false;
        }

        if (player.getX() == row) {
            for (Wall wall : walls) {
                if (wall.getX() == row)
                    if (isBetweenTwoPoints(column, player.getY(), wall.getY()))
                        return false;
            }

            if (player.getLimits().size() != 0) {
                return Math.abs(column - player.getY()) <= player.getLimits().get(0);
            } else
                return true;
        }

        else if (player.getY() == column) {
            for (Wall wall : walls) {
                if (wall.getY() == column)
                    if (isBetweenTwoPoints(row, player.getX(), wall.getX()))
                        return false;
            }
            if (player.getLimits().size() != 0) {
                return Math.abs(row - player.getX()) <= player.getLimits().get(0);
            } else
                return true;
        } else
            return false;
    }


    public boolean isBetweenTwoPoints(int firstPosition, int secondPosition, int wallPosition) {
        if (firstPosition > secondPosition) {
            int temp = firstPosition;
            firstPosition = secondPosition;
            secondPosition = temp;
        }
        return (firstPosition <= wallPosition) && (secondPosition >= wallPosition);
    }

    public void moveEffects(int row, int column) {
        Player player;
        if (turn.equals("red"))
            player = redPlayer;
        else
            player = bluePlayer;

        if (player.getY() == column) {
            Iterator<Star> starIterator = stars.iterator();
            while (starIterator.hasNext()) {
                Star star = starIterator.next();
                if (star.getY() == column) {
                    if (isBetweenTwoPoints(player.getX(), row, star.getX())) {
                        player.setStars(player.getStars() + 1);
                        starIterator.remove();
                    }
                }
            }

            Iterator<SpeedLimit> speedLimitIterator = speedLimits.iterator();
            while (speedLimitIterator.hasNext()) {
                SpeedLimit speedLimit = speedLimitIterator.next();
                if (speedLimit.getY() == column) {
                    if (isBetweenTwoPoints(player.getX(), row, speedLimit.getX())) {
                        if (redPlayer == player) {
                            bluePlayer.addLimitAmount(speedLimit.getLimitAmount());
                        } else
                            redPlayer.addLimitAmount(speedLimit.getLimitAmount());

                        speedLimitIterator.remove();
                    }
                }
            }
        }

        else {
            Iterator<Star> starIterator = stars.iterator();
            while (starIterator.hasNext()) {
                Star star = starIterator.next();
                if (star.getX() == row) {
                    if (isBetweenTwoPoints(player.getY(), column, star.getY())) {
                        player.setStars(player.getStars() + 1);
                        starIterator.remove();
                    }
                }
            }

            Iterator<SpeedLimit> speedLimitIterator = speedLimits.iterator();
            while (speedLimitIterator.hasNext()) {
                SpeedLimit speedLimit = speedLimitIterator.next();
                if (speedLimit.getX() == row) {
                    if (isBetweenTwoPoints(player.getY(), column, speedLimit.getY())) {
                        if (redPlayer == player) {
                            bluePlayer.addLimitAmount(speedLimit.getLimitAmount());
                        } else
                            redPlayer.addLimitAmount(speedLimit.getLimitAmount());

                        speedLimitIterator.remove();
                    }
                }
            }
        }

        player.setX(row);
        player.setY(column);

        if (player.getLimits().size() != 0)
            player.getLimits().remove(0);

    }

}
