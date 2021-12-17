package game;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Goomba class handles individual goomba that determines spawn location and
 * move direction.
 * @author Max & Lucas
 *
 */
public class goomba{
    
    private int x, dx, y, width, height;
    private int moveDirection;
    private boolean isAlive, inBounds, toKill;
    private Timer animationTimer;
    private TimerTask time;
    private int imgCounter;
    private Image goombaImg;
    private Random rand;
    private ImageIcon goomba1 = new ImageIcon("images/goomba1.png");
    private ImageIcon goomba2 = new ImageIcon("images/goomba2.png");
    private ImageIcon goomba3 = new ImageIcon("images/goomba3.png");

    
    /**
     * goomba constructor which sets the default image, and starts animation
     * timer.
     */
    public goomba()
    {
        goombaImg = goomba1.getImage();
        rand = new Random();
        width = 55;
        height = 55;
        
         
        y = 622;
        
        createNewTimer();
        
        
        
    }
    /**
     * animation timer which calls the goomba runAnimate to update its image
     * every time Timer goes off.
     * @return
     */
    private TimerTask createNewAnimationTask()
    {
        time = new TimerTask()
        {
            public void run()
            {                
                runAnimate();
            }
        };
        
        return time;
    }
    /**
     * animation class which handles what animation should be played whether
     * Goomba is dead or alive, if alive it switches between images every time
     * Timer is fired.
     */
    private void runAnimate() {
        
        if(toKill && imgCounter < 3)
        {
            goombaImg = goomba3.getImage();
            //System.out.println("stomped");
            imgCounter++;
            dx = 0;
            y = 628;
        }
        else if(toKill && imgCounter >= 3)
        {
            animationTimer.cancel();
            createNewTimer();
            isAlive = false;
            toKill = false;
        }
        else if (!toKill & imgCounter == 0)
        {
            goombaImg = goomba1.getImage();
            imgCounter++;
        }
        else if(!toKill)
        {
            goombaImg = goomba2.getImage();
            imgCounter = 0;
        }
    }



    /**
     * creates new Timer because new one is needed every time Goomba is reused 
     * in the Mario game. Helps control handling errors.
     * @return
     */
    private Timer createNewTimer()
    {
        animationTimer = new Timer();
        return animationTimer;
    }
    
    /**
     * accessor method to return X
     * @return x position
     */
    public int getX()
    {
        return x;
    }
    /**
     * accessor method to return Y
     * @return y position
     */
    public int getY()
    {
        return y;
    }
    /**
     * accessor method to return width
     * @return image height
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * accessor method to return height
     * @return image height
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * accessor method to return if Goomba is to be killed, helps determine
     * when to start the death animation.
     * @return toKill
     */
    public boolean isToKill()
    {
        return toKill;
    }
    
    /**
     * move method which updates Goomba's X position everytime is called.
     */
    public void move()
    {
        if(!inBounds && x > 0 && x < 930)
        {
            inBounds = true;
        }
        
        if(!inBounds || x+dx > 0 && x+dx < 930)
        {
            x += dx;
        }
        else if(x == 1)
        {
            dx = 1;
        }
        else if(x == 929)
        {
            dx = -1;
        }
        
    }
    
    /**
     * accessor method to return current Goomba image
     * @return goomba image
     */
    public Image getImage()
    {
        return goombaImg;
    }
    
    /**
     * class to spawn individual Goomba into game, sets it's coordinates and
     * determines the direction it's to move, also starts the animation.
     */
    public void startMoving()
    {
        if(rand.nextInt(2) == 1)
        {
            x = -80;
            moveDirection = 1;
        }
        else
        {
            x = 1080;
            moveDirection = -1;
        }
        
        dx = moveDirection;
        y = 622;
        isAlive = true;
        inBounds = false;
        animationTimer.scheduleAtFixedRate(createNewAnimationTask(), 0, 200);
    }
    
    /**
     * accessor method to return if Goomba is alive and in the game
     * @return isAlive
     */
    public boolean isAlive()
    {
        return isAlive;
    }
    
    /**
     * accessor method to if Goomba is dead, aka no longer in the game.
     * @return dead
     */
    public void dead()
    {
        toKill = true;        
    }
}
