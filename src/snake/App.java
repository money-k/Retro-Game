package snake;

import snakegame.SnakeGame;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        //this will be 600*600
        frame.setLocationRelativeTo(null); 
        //this will open up our window to the center of our screen 
        frame.setResizable(false);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //the program will terminate when the user clicks on x on window

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack(); //creating JPanel 600*600
        snakeGame.requestFocus();
    }
}
