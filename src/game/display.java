package game;

import javax.swing.JFrame;

/**
 * Display class which creates window screen 
 * and boots up game within window.
 * @author max & lucas
 *
 */
public class display {
    
    /**
     * main class which boots game and makes window
     * @param args
     */
    public static void main(String[] args) {
        JFrame display = new JFrame();
        display.setTitle("MiniMario by Max and Lucas");
        display.add(new miniMario());
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setSize(1000, 800);
        display.setVisible(true);
        display.setResizable(false);
        display.setLocationRelativeTo(null);
    }
}
