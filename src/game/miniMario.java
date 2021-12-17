package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;

/**
 * Runs miniMario game, core component to draw and update
 * all images within game.
 * @author max & lucas
 *
 */
public class miniMario extends JPanel implements ActionListener{
    
    private player mario;
    private mushroom mushroom;
    private ArrayList<goomba> goombaList;
    private Image background, coinAmt, timeTitle, scoreTitle, heart, bush, clouds;
    private Timer time;
    private int spawnCounter, spawnRate, goombaKilled, finalTime;
    private double aliveTime, collideTimer;
    private Random rand;
    
    private ImageIcon backroundImg = new ImageIcon("images/background.png");
    private ImageIcon gameoverImg = new ImageIcon("images/gameover.png");
    private ImageIcon coinAmtImg = new ImageIcon("images/coinAmt.png");
    private ImageIcon timeTitleImg = new ImageIcon("images/timeTitle.png");
    private ImageIcon scoreTitleImg = new ImageIcon("images/scoreTitle.png");
    private ImageIcon heartImg = new ImageIcon("images/heart.png");
    private ImageIcon bushImg = new ImageIcon("images/bush.png");
    private ImageIcon cloudsImg = new ImageIcon("images/clouds.png");
    
    /**
     * constructor class, mostly defines images but also starts
     * the game.
     */
    public miniMario()
    {
        addKeyListener(new ActionListener());
        setFocusable(true);

        
        coinAmt = coinAmtImg.getImage();
        timeTitle = timeTitleImg.getImage();
        scoreTitle = scoreTitleImg.getImage();
        bush = bushImg.getImage();
        clouds = cloudsImg.getImage(); 
        heart = heartImg.getImage();  
        startGame();
    }
    /**
     * game class, boots up game creates player and goombas,
     * starts timer that updates animations
     */
    public void startGame()
    {
        aliveTime = 0;
        background = backroundImg.getImage();
        mario = new player();
        mushroom = new mushroom();
        goombaList = new ArrayList<goomba>();
        spawnCounter = 0;
        spawnRate = 1000;
        rand = new Random();
        collideTimer = 0;
        for(int i = 0; i < 20; i++)
        {
            goombaList.add(new goomba());
        }
        
        time = new Timer(5, this); 
        time.start();
    }
    /**
     * whole class implements ActionListener, and Timer sets it off
     * every 5 milliseconds which calls this class, this class is for
     * game mechanics. Constantly checks for object collision and handles
     * the outcome along with controlling goomba & mushroom spawning.
     */
    public void actionPerformed(ActionEvent e)
    {        
        aliveTime+=0.005;
        
        
        
        Rectangle pkillCheck = new Rectangle(mario.getX(),mario.getY()+mario.getHeight(),mario.getWidth(),2);
        Rectangle pHitBox = new Rectangle(mario.getX(),mario.getY(),mario.getWidth(),mario.getHeight()-2);
        goomba currGoomba = goombaList.get(spawnCounter);
        
        if(!mushroom.isAlive() && rand.nextInt(3000) == 1)
        {
            mushroom.startMoving();
        }
        else
        {
            mushroom.move();
            Rectangle mHitBox = new Rectangle(mushroom.getX(),mushroom.getY(),mushroom.getWidth(),mushroom.getHeight());
            
            if (mHitBox.intersects(pHitBox) && mushroom.isAlive())
            {
                mushroom.consume();
                mario.addLife();
            }
        }
        
        if(!currGoomba.isAlive())
        {
            currGoomba.startMoving();
            //System.out.println("spawning new goomba");
        }
        else if (rand.nextInt(spawnRate) == 1)
        {
            if (spawnCounter < 19)
            {
                spawnCounter++;
            }
            else
            {
                spawnCounter = 0;
            }
            
        }
        
        
        for(goomba goom : goombaList)
        {
            if (goom.isAlive())
            {
                goom.move();
                Rectangle gkillCheck = new Rectangle(goom.getX(),goom.getY(),goom.getWidth(), 2);
                Rectangle gHitBox = new Rectangle(goom.getX(),goom.getY(),goom.getWidth(), goom.getHeight()-2);
                //System.out.println(collideTimer);
                if(gkillCheck.intersects(pkillCheck))
                {
                    //System.out.println("goomba stomped");
                    if(!goom.isToKill())
                    {
                        mario.move("stomp");
                    }
                    
                    goom.dead();
                    goombaKilled++;
                    
                    if(spawnRate > 125)
                    {
                        spawnRate = (int) (spawnRate - (15.0/Math.pow(16,(goombaKilled/200.0))));
                    }
                    //System.out.println(spawnRate);
                }
                else if(!goom.isToKill() && gHitBox.intersects(pHitBox) && collideTimer == 0)
                {
                    //System.out.println("player dead");
                    if(!mario.toDie())
                    {
                        mario.move("dead");
                        collideTimer += 0.005;
                    }
                }
                else if (collideTimer > 2)
                {
                    collideTimer = 0;
                    
                }
                else if (collideTimer > 0)
                {
                    collideTimer += 0.01;
                }
            }
        }
            
        mario.move("");
        revalidate();
        repaint();
        
    }
    
    /**
     * class which paints images onto the window, checks for game state
     * to draw appropriate images.
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 38));
        
        if(mario.isDead())
        {
            background = gameoverImg.getImage();  
        }
        
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(scoreTitle, 10, 10, null);
        g2d.drawImage(timeTitle, 850, 10, null);
        g2d.drawString("" + mario.getScore(), 10, 65);
        g2d.drawString("" + (int) aliveTime, 850, 65);
        
        if(!mario.isDead())
        {
            g2d.drawImage(clouds, 0, 0, null);
            g2d.drawImage(bush, 340, 597, null);
            g2d.drawImage(bush, 740, 597, null);
            g2d.drawImage(bush, 40, 597, null);

            g2d.drawImage(mario.getImage(), mario.getX(), mario.getY(), null);
            g2d.drawImage(heart, 400, 10, null);
            g2d.drawString("" + mario.getLives(), 460, 40);
            
            if (collideTimer > 0 && !mario.toDie())
            {
                g2d.drawString("Ow!", mario.getX(), mario.getY() - 20);
            }
            
            for(goomba goom : goombaList)
            {
                if (goom.isAlive())
                {
                    g2d.drawImage(goom.getImage(), goom.getX(), goom.getY(), null);
                }
            }
            
            if (mushroom.isAlive())
            {
                g2d.drawImage(mushroom.getImage(), mushroom.getX(), mushroom.getY(), null);
            }
        }
        else
        {
            g2d.drawString("Press 'G' to Restart Game", 220, 750);
            time.stop();
        }
        
        
    }
    /**
     * subset class needed for all swing games that listen for keyboard inputs.
     * Listens for keyboard input and sends keys to mario class for handling.
     * 
     * @author Max & Lucas
     *
     */
    private class ActionListener extends KeyAdapter
    {
        /**
         * class that sends keys that are released
         */
        public void keyReleased(KeyEvent e)
        {
            mario.releasedKey(e);
        }
        /**
         * class that sends keys that are pressed
         */
        public void keyPressed(KeyEvent e)
        {
            if(mario.isDead() && e.getKeyCode() == KeyEvent.VK_G)
            {
                startGame();
            }
            
            mario.pressedKey(e);
        }
    }
}
