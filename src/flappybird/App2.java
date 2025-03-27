package flappybird;

import javax.swing.*;
public class App2 {
    public static void main(String[] args) throws Exception {
        int boardWidth= 360;  /* dimensions of windows: width=360 and height=640 */
        int boardHeight=640; 
        
        //FOR FRAME
        JFrame frame= new JFrame("Flappy Bird");
        //frame.setVisible(true); //frame is visible now
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null); //sets the frame in centre
        frame.setResizable(false); //user will not be able to resize the window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when user will click cross button, window will close
        // add JPanel in our frame
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird); //adding this will avoid JPanel taking space of title bar
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true); //frame is visible now

    }
}
