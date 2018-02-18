
package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Grid {

        Random rand = new Random();
        Square[][] grid;
        int width;
        int height;
        Square apple;
        LinkedList <Square> freeSquares = new LinkedList<>();
        LinkedList <Snake> snakes = new LinkedList<>();
        GUI gui;
        Graphics g;
        Timer t = new Timer();
        int recordSize = 0;
        int players = 0;
        
        public Grid(GUI gui){
                width = gui.width/gui.squareSize;
                height = gui.height/gui.squareSize;
                grid = new Square[width][height];
                this.gui = gui;
                this.g = gui.g;
                gui.g.setColor(Color.white);
                gui.g.drawLine(gui.width, 0, gui.width, gui.height);
                gui.g.setFont(new Font("Arial",Font.BOLD,25));
                gui.g.drawString("Scores:", gui.width + 50, 50);
                gui.g.setFont(new Font("Arial",Font.BOLD,15));
                
                for(int i = 0 ; i < width ; i++){
                        for(int j = 0 ; j < height ; j++){
                                grid[i][j] = new Square(i,j,this);
                                freeSquares.add(grid[i][j]);
                        }
                }
                
                TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                                for(int i = 0;i < snakes.size();i++){
                                        snakes.get(i).moveSnake();
                                }
                        }
                };
                t.scheduleAtFixedRate(task, 500, 100);
        }
        
        public void spawnApple(){
                Square apple = freeSquares.get(rand.nextInt(freeSquares.size())).fillApple();
                for(int i = 0 ; i < snakes.size() ; i++){
                        if(snakes.get(i).computerControlled){
                                snakes.get(i).mouse = new Point(apple.x + apple.l/2, apple.y + apple.l/2);
                        }
                }
        }
        
        public void spawnKeyControlledPlayer(){
                
                switch (players) {
                        case 0:
                                {
                                        int[] keys = {KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_W};
                                        new Snake(this, Color.WHITE, keys);
                                        break;
                                }
                        case 1:
                                {
                                        int[] keys = {KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_T};
                                        new Snake(this, new Color(32,32,255), keys);
                                        break;
                                }
                        case 2:
                                {
                                        int[] keys = {KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_I};
                                        new Snake(this, new Color(64,255,32),  keys);
                                        break;
                                }
                        case 3:
                                {
                                        int[] keys = {KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_UP};
                                        new Snake(this, new Color(255,255,32), keys);
                                        break;
                                }
                        default:
                                break;
                }
                players++;
        }
        
        public void spawnMouseControlledPlayer(){
                new Snake(this, true);

        }
        
        public void spawnBot(){
                new Snake(this, false);
        }
        
        public void clearSnakes(){
                int xSnakes = snakes.size();
                for(int i = 0 ; i < xSnakes ; i++){
                        snakes.getFirst().terminate();
                }
                players = 0;
        }
}