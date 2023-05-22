package StarsGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    private GameState state;
    private JButton[][] buttons;
    private boolean redPlaced, bluePlaced;
    private boolean wallsPlaced;
    private boolean starsPlaced;
    private boolean speedLimitsPlaced;
    private boolean gameStart;
    private int width, height;

    public GameFrame(GameState state) {
        super();

        this.state = state;
        redPlaced = false;
        bluePlaced = false;
        gameStart = false;
        wallsPlaced = false;
        starsPlaced = false;
        speedLimitsPlaced = false;

        String maxWidth, maxHeight;
        maxHeight = JOptionPane.showInputDialog(null, "Enter the number of row buttons : ");
        maxWidth = JOptionPane.showInputDialog(null, "Enter the number of column buttons : ");

        width = Integer.parseInt(maxWidth);
        height = Integer.parseInt(maxHeight);

        buttons = new JButton[height][width];
        setLayout(new GridLayout(height, width));

        Actionhandler actionhandler = new Actionhandler();
        KeyHandler keyhandler = new KeyHandler();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(actionhandler);
                buttons[i][j].addKeyListener(keyhandler);
                add(buttons[i][j]);
            }
        }

        setSize(width * 80, height * 80);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JOptionPane.showMessageDialog(null, "Please Choose where to put redPlayer", null, JOptionPane.PLAIN_MESSAGE);

    }

    private class Actionhandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            JButton button = (JButton) event.getSource();
            int row = 0 , column = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (button == buttons[i][j]) {
                        row = i;
                        column = j;
                    }
                }
            }

            if (!(redPlaced && bluePlaced)) {

                Player player = new Player(row, column);
                if (!redPlaced) {
                    state.setRedPlayer(player);
                    redPlaced = true;
                    Icon icon = new ImageIcon("red.png");
                    button.setIcon(icon);
                    JOptionPane.showMessageDialog(null, "Please Choose where to put blue player", null,
                            JOptionPane.PLAIN_MESSAGE);
                }

                else if (!bluePlaced) {
                    if (state.checkLocation(row, column)) {
                        state.setBluePlayer(player);
                        bluePlaced = true;
                        Icon icon = new ImageIcon("blue.png");
                        button.setIcon(icon);
                        JOptionPane.showMessageDialog(null,
                                "Please Choose where to put walls and press any key to continue", null,
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }

            }
            
            else if (!wallsPlaced) {
                if (state.checkLocation(row, column)) {
                    state.getWalls().add(new Wall(row, column));
                    Icon icon = new ImageIcon("wall.png");
                    button.setIcon(icon);
                }
            }

            else if (!speedLimitsPlaced) {
                if (state.checkLocation(row, column)) {
                    int limitAmount;
                    do{
                        String amount = JOptionPane.showInputDialog(null, "Enter amount of SpeedLimit ");
                        limitAmount = Integer.parseInt(amount);
                        if(limitAmount <= 0)
                            JOptionPane.showMessageDialog(null, "SpeedLimit cannot be lower than 1");
                    }while (limitAmount <= 0);
                    state.getSpeedLimits().add(new SpeedLimit(row, column, limitAmount));
                    button.setBackground(Color.GRAY);
                    button.setText(String.valueOf(limitAmount));
                    button.setOpaque(true); 

                }
            } 
            
            else if (!starsPlaced) {
                if (state.checkLocation(row, column)) {
                    state.getStars().add(new Star(row, column));
                    button.setOpaque(true);
                    Icon icon = new ImageIcon("star.png");
                    button.setIcon(icon);

                }
            }

            ////// WHEN GAME STARTS:
            else if (gameStart) {
                if (state.checkMove(row, column)) {
                    state.moveEffects(row, column);
                    updateFrame();

                    if (state.isGameOver()) {
                        printTitle();
                        if (state.getBluePlayer().getStars() > state.getRedPlayer().getStars()) {
                            JOptionPane.showMessageDialog(null, "BLUE IS WINNER!");
                        } 
                        else if (state.getRedPlayer().getStars() > state.getBluePlayer().getStars()) {
                            JOptionPane.showMessageDialog(null, "RED IS WINNER!");
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "DRAW!");
                        }
                        System.exit(0);
                    }

                    if (state.getTurn().equals("blue"))
                        state.setTurn("red");
                    else
                        state.setTurn("blue");


                    printTitle();
                }

            }

        }

        private void updateFrame() {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    buttons[i][j].setIcon(null);
                    buttons[i][j].setBackground(Color.WHITE);
                    buttons[i][j].setText(null);
                }
            }
            //dobare element haro gharar mide
            for (Star star : state.getStars()) {
                buttons[star.getX()][star.getY()].setIcon(new ImageIcon("star.png")); 
            }
            for (SpeedLimit speedLimit : state.getSpeedLimits()) {
                buttons[speedLimit.getX()][speedLimit.getY()].setBackground(Color.GRAY);
                buttons[speedLimit.getX()][speedLimit.getY()].setOpaque(true);
                buttons[speedLimit.getX()][speedLimit.getY()].setText(String.valueOf(speedLimit.getLimitAmount()));
            }

            for (Wall wall : state.getWalls()) {
                buttons[wall.getX()][wall.getY()].setIcon(new ImageIcon("wall.png"));
            }
            buttons[state.getRedPlayer().getX()][state.getRedPlayer().getY()].setIcon(new ImageIcon("red.png"));
            buttons[state.getBluePlayer().getX()][state.getBluePlayer().getY()].setIcon(new ImageIcon("blue.png"));
        }
    }


    private class KeyHandler extends KeyAdapter {

        public void keyPressed(KeyEvent event) {
            if (!wallsPlaced) {
                wallsPlaced = true;
                JOptionPane.showMessageDialog(null, "Please place speed limits and press any key to continue");
            } 

            else if (!speedLimitsPlaced) {
                speedLimitsPlaced = true;
                JOptionPane.showMessageDialog(null, "Please place stars and press any key to continue");
            } 

            else if (!starsPlaced) {
                starsPlaced = true; 
                gameStart = true;
                JOptionPane.showMessageDialog(null, "Game Starts, Red's Turn!", null, JOptionPane.PLAIN_MESSAGE);
                printTitle();
            }

        }
    }



    public void printTitle(){
        StringBuilder redLimits = new StringBuilder();
        StringBuilder blueLimits = new StringBuilder();

        for(Integer limit : state.getRedPlayer().getLimits()){
            redLimits.append(limit).append(" ");
        }
        for(Integer limit : state.getBluePlayer().getLimits()){
            blueLimits.append(limit).append(" ");
        }
        System.out.println(blueLimits);
        setTitle("Red: " + state.getRedPlayer().getStars() +" (limits: " + redLimits +  ")"+ "    Blue: " + state.getBluePlayer().getStars()
                +" (limits: " + blueLimits +  ")" + "    Turn: " + state.getTurn());
    }

}
