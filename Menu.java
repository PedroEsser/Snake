
package snake;

import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu implements KeyListener{

        JFrame frame;
        JPanel panel;
        Graphics g;
        JTextField[] data = new JTextField[3];
        JButton play = new JButton();
        
        public Menu() {
                frame = new JFrame();
                panel = new JPanel();
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setBounds(d.width/2  - 400, d.height/2 - 300, 800, 600);
                frame.setUndecorated(true);
                
                frame.add(panel);
                frame.setVisible(true);
                g = panel.getGraphics();
                
                for(int i = 0 ; i < 3 ; i++){
                        data[i] = new JTextField();
                        data[i].setBounds(410, 198 + i * 60, 50, 30);
                        panel.add(data[i]);
                }
                data[0].setText("800");
                data[1].setText("800");
                data[2].setText("40");
                
                play = new JButton();
                play.setBounds(300, 450, 200, 100);
                play.setBorderPainted(false);
                play.setFont(new Font("Arial",Font.BOLD,50));
                play.setBackground(Color.black);
                play.setForeground(Color.red);
                play.setText("PLAY!");
                play.setVisible(true);
                play.addActionListener((ActionEvent e) -> {
                        play();
                });
                panel.add(play);
                frame.addKeyListener(this);
                
                panel.setBackground(Color.BLACK);
                
                g.setColor(Color.red);
                g.setFont(new Font("",Font.ITALIC,100));
                g.drawString("SNAKE", 220, 100);
                g.setFont(new Font("Comic",Font.ITALIC,30));
                g.drawString("Width:", 315, 220);
                g.drawString("Height:", 305, 280);
                g.drawString("Square size:", 230, 340);
        }
        
        public static void main(String[] args){
                new Menu();
                //new GUI(800,800,40);
        }

        public void play(){
                try{
                        int width = Integer.parseInt(data[0].getText());
                        int height = Integer.parseInt(data[1].getText());
                        int squareSize = Integer.parseInt(data[2].getText());
                        
                        System.out.println(width + "  " + height + "  " + squareSize);
                        new GUI(width, height, squareSize);
                        frame.dispose();
                }catch(Exception e){
                        
                }
                
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
                
        }

        @Override
        public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                        case KeyEvent.VK_X: frame.dispose();
                        break;
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                
        }

}
