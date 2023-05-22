package StarsGame;

public class Main {
    public static void main(String[] args){
        GameState state = new GameState();
        GameFrame frame = new GameFrame(state);
        frame.setVisible(true);
    } 
}
