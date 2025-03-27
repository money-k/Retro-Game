package snakegame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //used for storing the segments of the snake body
import java.util.Random; //random x and y values for placing food on screen
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    /*creating a new class to keep track of all x and y positions for each tile
    and a private class so that only snake game can access this class*/
    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead; //variable
    //for the snake body we need arraylist to store all of it's segments(tile)
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop; //variable for timer
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    public SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5); //default starting space
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    //drawing function
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        /*/grid
        for(int i = 0; i <boardWidth/tileSize; i++){
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);//vertical lines
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); //horizontally
        }*/
        //Food
        g.setColor(Color.red);
        g.fill3DRect(food.x *tileSize, food.y * tileSize, tileSize, tileSize, true);
        //snakeHead
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //Snake Body
        for(int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //draw a rectangle for a snake part
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("GAME OVER: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize);//600/25 = 24, random num from 0 to 24
        food.y = random.nextInt(boardHeight/tileSize);
    }

    //function to detect collision between the snake's head and the food
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y; 
    }

    //move fnction will update x and y position of the snake
    public void move(){
        //eat food
        if(collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        //Snake Body
        for(int i = snakeBody.size()-1; i >=0; i--) {
            Tile snakePart = snakeBody.get(i);
            if(i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
            Tile prevSnakePart = snakeBody.get(i-1);
            snakePart.x = prevSnakePart.x;
            snakePart.y = prevSnakePart.y;
        }
        }
        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for(int i =0; i<snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //collide with the snake head
            if(collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight) {
            gameOver = true;
        }
    }

    //Overriding 1 ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); //this is going to be called every 100 millisecond
        repaint();
        if(gameOver) {
            gameLoop.stop();
        }
    }

    //Overriding 3 KeyListener methods 
    @Override
    public void keyPressed(KeyEvent e) {
        //VK_UP means for up key
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    //do not need 
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
