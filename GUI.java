
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI implements KeyListener{

        Random rand = new Random();
        int squareSize;
        Grid grid;
        int width;
        int height;
        JFrame frame;
        JPanel panel;
        Graphics g;
        
        public GUI(int width, int height, int squareSize){
                
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                if(width > d.width - 200){
                        this.width = d.width - 200;
                }else{
                        this.width = width;
                }
                if(height > d.height){
                        this.height = d.height;
                }else{
                        this.height = height;
                }
                this.squareSize = squareSize;
                panel = new JPanel();
                frame = new JFrame();
                frame.setSize(width + 200, height);
                frame.setLocation(d.width/2 - (this.width + 200)/2, d.height/2 - this.height/2);
                
                frame.addKeyListener(this);
                frame.setUndecorated(true);
                panel.setBackground(Color.black);
                frame.add(panel);
                frame.setVisible(true);
                g = panel.getGraphics();
                
                /*try {
                        Thread.sleep(50);
                } catch (InterruptedException ex) {
                }*/
                grid = new Grid(this);
        }

        @Override
        public void keyTyped(KeyEvent e) {
                
        }

        @Override
        public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                        case KeyEvent.VK_X:frame.dispose();grid.t.cancel();
                        break;
                        case KeyEvent.VK_K:grid.spawnKeyControlledPlayer();
                        break;
                        case KeyEvent.VK_M:grid.spawnMouseControlledPlayer();
                        break;
                        case KeyEvent.VK_B:grid.spawnBot();
                        break;
                        case KeyEvent.VK_C:grid.clearSnakes();
                        break;
                        case KeyEvent.VK_ENTER:grid.spawnApple();
                        break;
                        case KeyEvent.VK_SPACE:
                                g.setColor(Color.white);
                                g.drawLine(width, 0, width, height);
                                g.setFont(new Font("Arial",Font.BOLD,25));
                                g.drawString("Scores:", width + 50, 50);
                                g.setFont(new Font("Arial",Font.BOLD,15));
                                
                        break;
                        
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                
        }
        
}
