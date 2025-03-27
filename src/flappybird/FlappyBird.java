package flappybird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random; // to place pipes at random places
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth=360;
    int boardHeight=640;
    
    //Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2; 
    int birdWidth= 34;
    int birdHeight= 24;

    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
          public Bird(Image img ){
                this.img= img;
            }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0; //the pipe will start from top of  the screen right side
    int pipeWidth = 64; //actual image is 384px; 384/6 here
    int pipeHeight = 512; 
 
    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; //to check whether flappy bird passed the pipe
        //constructor
       public Pipe(Image img){
            this.img = img;
        }
    }
    
    //game logic
    Bird bird; 
    int velocityX = -4; //move pipes to the left
    int velocityY = 0; //velocity will push the bird up
    int gravity = 1; //gravity will push the bird down

    ArrayList<Pipe> pipes; //storing all the pipes in use (list of all arrays)
    Random random = new Random();
    
    Timer gameLoop; //a variable to keep the game going endless
    Timer placePipesTimer;  //to set pipes per frame
    boolean gameOver = false;
    double score = 0 ;

    //constructor
   public FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true); //makes sure it is the Flappy Bird class that takes up our key events
        addKeyListener(this); //checks all three Key Listner functions
       
        //load images
        backgroundImg = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
       
        
        //bird
        bird=new Bird(birdImg);

        //array list
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() { //1.5 sec, a new pipe added
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this); //1000 milliseconds divided by 60(frames)=16.66 and this->FlappyBird class
        gameLoop.start();
    }
    
    //function to create pipes and add them to array list
    public void placePipes() {
        //randomPipeY will be a random integer between -384 to -128 i.e) 3/4 to 1/4 of pipeHeight (resp.)
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4; //space b/w pipes
        
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y= randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe =  new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    //draw
    public void paintComponent(Graphics g){
        super.paintComponent(g); //invoke a function from JPanel
        draw(g); //calling draw function

    }
    
    //define draw function
    public void draw(Graphics g){
        //background draw
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        //bird draw
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null );
        //pipes draw
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver){
            g.drawString("Game Over: "+ String.valueOf((int) score), 10, 35);
        }
        else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }
    
    //updating positions as per velocity
    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0); //to stop the bird(object) from moving out of screen
       
        //pipes
        for(int i = 0; i < pipes.size(); i++){
        Pipe pipe=pipes.get(i);
        pipe.x += velocityX; //each pipe will move by -4 to left

         if(!pipe.passed && bird.x > pipe.x + pipe.width){ 
            //logic: if the bird passes the right side of the pipe; return true
            pipe.passed = true;
            score += 0.5; //0.5 for top, 0.5 for bottom; therefore 1 for one set of pipes
         }   

        if (collision(bird, pipe)){
            gameOver = true;
        }
    }
        if(bird.y > boardHeight){
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&  //a's top left corner unreachable by b's top right corner
        a.x + a.width > b.x && //a's top right corner passes b's top left corner
        a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
        a.y + a.height > b.y; //a's bottom left corner passes b's top left corner
    } 

    //auto implemented method for ActionListner
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    //auto implemented method for KeyListner
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY= -9;
            if (gameOver){
                //restart the game
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {} 
    
    @Override
    public void keyReleased(KeyEvent e) {}
}
