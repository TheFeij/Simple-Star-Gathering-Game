package StarsGame;


public class Element {
    private int xPos;
    private int yPos;

    public Element(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getX() { // Location x return mishe
        return xPos;
    }

    public void setX(int xPos) { // voroodi migire va bara location x mizare
        this.xPos = xPos;
    }

    public int getY() { // Location y return mishe
        return yPos;
    }

    public void setY(int yPos) { // voroodi migire va bara location y mizare
        this.yPos = yPos;
    }

}
