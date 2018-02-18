
package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Square {
            
        int i;
        int j;
        int x;
        int y;
        int l;
        boolean isSnake;
        Snake snake;
        boolean isApple;
        Graphics g;
        LinkedList <Square> freeSquares;
        
        public Square(int x, int y, Grid grid){
                this.i = x;
                this.j = y;
                this.x = x*grid.gui.squareSize;
                this.y = y*grid.gui.squareSize;
                this.g = grid.g;
                this.l = grid.gui.squareSize;
                this.freeSquares = grid.freeSquares;
        }
        
        public Square fillHead(Snake s){
                snake = s;
                g.setColor(snake.snakeColor.darker().darker());
                g.fillRect(x+1, y+1, l-2, l-2);
                g.setColor(Color.red);
                g.drawLine(x + l/2, y + l/2, x + l/2 + (l/2-2) * snake.direction.x, y + l/2 + (l/2-2) * snake.direction.y);
                freeSquares.remove(this);
                isSnake = true;
                return this;
        }
        public Square fillSnake(){
                g.setColor(snake.snakeColor);
                g.fillRect(x+1, y+1, l-2, l-2);
                return this;
        }
        
        public Square fillApple(){
                g.setColor(Color.red);
                g.fillRect(x+1, y+1, l-2, l-2);
                freeSquares.remove(this);
                isApple = true;
                return this;
        }
        
        public Square clear(){
                g.setColor(Color.black);
                g.fillRect(x, y, l, l);
                freeSquares.add(this);
                isSnake = false;
                snake = null;
                return this;
        }
}
