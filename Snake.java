/*
 * 
 */

package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snake implements KeyListener, MouseMotionListener{

        Random rand = new Random();
        LinkedList <Square> snake = new LinkedList<>();
        Square head;
        Square tail;
        Point direction = new Point(0,1);
        Point newDirection;
        int size = 1;
        Grid grid;
        Graphics g;
        boolean directionChanged;
        Color snakeColor;
        int[] keys;
        Point mouse = new Point(0,0);
        boolean mouseControlled = false;
        boolean computerControlled = false;
        Point scorePos;
        
        public Snake(Grid g, Color c,int[] keys){
                this.grid = g;
                this.g = g.g;
                this.snakeColor = c;
                this.scorePos = new Point(grid.gui.width + 90,100 + grid.snakes.size() * 30);
                head = tail = grid.freeSquares.get(rand.nextInt(grid.freeSquares.size())).fillHead(this);
                this.g.setColor(c);
                
                g.gui.frame.addKeyListener(this);
                this.keys = keys;
                this.g.drawString("Key", scorePos.x - 70, scorePos.y);
                
                updateScore();
                grid.snakes.add(this);
        }
        
        public Snake(Grid g,boolean mouseControlled){
                this.grid = g;
                this.g = g.g;
                this.snakeColor = new Color(rand.nextInt());
                this.scorePos = new Point(grid.gui.width + 90,100 + grid.snakes.size() * 30);
                head = tail = grid.freeSquares.get(rand.nextInt(grid.freeSquares.size())).fillHead(this);
                this.g.setColor(snakeColor);
                
                if(mouseControlled){
                        g.gui.frame.addMouseMotionListener(this);
                        this.mouseControlled = true;
                        this.g.drawString("Mouse", scorePos.x - 70, scorePos.y);
                }else{
                        this.computerControlled = true;
                        this.g.drawString("Bot", scorePos.x - 70, scorePos.y);
                }
                
                updateScore();
                grid.snakes.add(this);
        }
        
        public void moveSnake(){
                if(mouseControlled || computerControlled){
                        checkDirection();
                }
                if(directionChanged){
                        changeDirection();
                        directionChanged = false;
                }
                Square nextSquare = nextSquare(direction);
                
                if(!nextSquare.isSnake || nextSquare.snake == this){
                        if(nextSquare.snake == this){
                                cutTail(nextSquare);
                        }
                        head.fillSnake();
                        head = nextSquare.fillHead(this);
                        snake.add(head);
                        
                        if(nextSquare.isApple){
                                size++;
                                head.isApple = false;
                                grid.spawnApple();
                                updateScore();
                        }else{
                                snake.remove(tail.clear());
                                tail = snake.getFirst();
                        }
                }
                //System.out.println(size);
        }
        
        public void cutTail(Square s){
                int h = snake.indexOf(s) + 1;
                for (int i = 0; i < h; i++){
                        snake.removeFirst().clear();
                        size--;
                }
                tail = snake.getFirst();
                updateScore();
        }
        
        public void changeDirection(){
                if(validDirection(newDirection)){
                        Square nextSquare = nextSquare(newDirection);
                        if(!nextSquare.isSnake || nextSquare.snake == this){
                                direction = newDirection;
                        }
                }
        }
        
        public boolean validDirection(Point p){
                return !p.equals(oppositeDirection(direction));
        }

        
        public void checkDirection(){
                Point p = new Point(this.head.x + this.head.l/2,this.head.y + this.head.l/2);
                int difY = p.y - mouse.y;
                int difX = p.x - mouse.x;
                directionChanged = true;
                
                Point secondBestDirection;
                if(Math.abs(difY) < Math.abs(difX)){
                        if(difX < 0){
                                newDirection = new Point(1,0);
                        }else{
                                newDirection = new Point(-1,0);
                        }
                        if(difY < 0){
                                secondBestDirection = new Point(0,1);
                        }else{
                                secondBestDirection = new Point(0,-1);
                        }
                }else{
                        if(difY < 0){
                                newDirection = new Point(0,1);
                        }else{
                                newDirection = new Point(0,-1);
                        }
                        if(difX < 0){
                                secondBestDirection = new Point(1,0);
                        }else{
                                secondBestDirection = new Point(-1,0);
                        }
                }                                                 

                /*if(mouseControlled){
                        if(!validDirection(newDirection) && !grid.grid[head.i + secondBestDirection.x][head.j + secondBestDirection.y].isSnake){
                                newDirection = secondBestDirection;
                        }
                }else{  */                                //computer controlled
                        try{
                                Square nextSquare = nextSquare(newDirection);
                                if(nextSquare.isSnake){
                                        nextSquare = nextSquare(secondBestDirection);
                                        if(validDirection(secondBestDirection) && !nextSquare.isSnake){
                                                newDirection = secondBestDirection;
                                        }else if (!nextSquare(oppositeDirection(secondBestDirection)).isSnake && validDirection(oppositeDirection(secondBestDirection))){
                                                newDirection = oppositeDirection(secondBestDirection);
                                        }else{
                                                newDirection = oppositeDirection(newDirection);
                                        }

                                }
                        }catch(Exception e){

                        }
                //}
                
                        
        }
        
        public Square nextSquare(Point p){
                return grid.grid[(head.i + p.x + grid.width) % grid.width][(head.j + p.y + grid.height) % grid.height];
        }
        
        public Point oppositeDirection(Point p){
                return new Point(p.x * -1, p.y * -1);
        }
        
        public void updateScore(){
                this.g.setColor(Color.black);
                this.g.fillRect(scorePos.x, scorePos.y - 20, 100, 20);
                this.g.setColor(snakeColor);
                this.g.drawString("" + size, scorePos.x, scorePos.y);
        }

        public void terminate(){
                grid.snakes.remove(this);
                for(int i = 0 ; i < snake.size() ; i++){
                        snake.get(i).clear();
                }
                this.g.setColor(Color.black);
                this.g.fillRect(scorePos.x - 80, scorePos.y - 20, 180, 25);
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
                
        }

        @Override
        public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == keys[0]){
                        directionChanged = true;
                        newDirection = new Point(-1,0);
                }else if(e.getKeyCode() == keys[1]){
                        directionChanged = true;
                        newDirection = new Point(0,1);
                }else if(e.getKeyCode() == keys[2]){
                        directionChanged = true;
                        newDirection = new Point(1,0);
                }else if(e.getKeyCode() == keys[3]){
                        directionChanged = true;
                        newDirection = new Point(0,-1);
                        }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                
        }

        @Override
        public void mouseDragged(MouseEvent e) {
                
        }

        @Override
        public void mouseMoved(MouseEvent e) {
                mouse = new Point(e.getX(),e.getY());
        }
}