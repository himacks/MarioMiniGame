package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;

/**
 * player class which controls player movement and behavior within game
 * @author Max & Lucas
 *
 */
public class player {
    
    private int x, dx, y, dy, width, height, jumpCap, floorHeight, imgCounter, score, lives;
    private Image playerImg;
    private TimerTask time;
    private Timer animationTimer;
    private boolean animateStart, jump, falling, isDead, hit, toDie;
    
    private ImageIcon marioLeft = new ImageIcon("images/marioLeft.png");
    private ImageIcon marioRight = new ImageIcon("images/marioRight.png");
    private ImageIcon marioRunR0 = new ImageIcon("images/marioRunR0.png");
    private ImageIcon marioRunR1 = new ImageIcon("images/marioRunR1.png");
    private ImageIcon marioRunR2 = new ImageIcon("images/marioRunR2.png");
    private ImageIcon marioRunL0 = new ImageIcon("images/marioRunL0.png");
    private ImageIcon marioRunL1 = new ImageIcon("images/marioRunL1.png");
    private ImageIcon marioRunL2 = new ImageIcon("images/marioRunL2.png");
    private ImageIcon marioDead = new ImageIcon("images/marioDead.png");
    
    /**
     * player constructor which sets all default values along with default
     * image, sets coordinates for where to spawn.
     */
    public player()
    {
        animateStart = false;
        jump = false;
        falling = false;
        isDead = false;
        toDie = false;
        hit = false;
        jumpCap = 490;
        floorHeight = 614;
        score = 0;
        lives = 2;
        
        playerImg = marioRight.getImage();
        x = 400;
        y = 613;
        
        width = 65;
        height = 64;
        
        createNewTimer();  
    }
    
    /**
     * creates new Timer because new one is needed every time player moves different direction in
     * the Mario game. Helps control handling errors.
     * @return the new Timer
     */
    private Timer createNewTimer()
    {
        animationTimer = new Timer();
        return animationTimer;
    }
    
    /**
     * animation timer which calls the player's runAnimate to update its image
     * every time a or d key goes off.
     * @return the timerTask created
     */
    private TimerTask createNewAnimationTask()
    {
        time = new TimerTask()
        {
            public void run()
            {
                animateStart = true;

                if (dx < 0)
                {
                    runLeftAnimate();
                }
                else if (dx > 0)
                {
                    runRightAnimate();
                }
            }
        };
        
        return time;
    }
    
    /**
     * run animation for mario running right.
     */
    private void runRightAnimate() {
        
        if (imgCounter == 0)
        {
            playerImg = marioRunR0.getImage();
        }
        else if (imgCounter == 3)
        {
            playerImg = marioRunR1.getImage();
        }
        else if (imgCounter == 6)
        {
            playerImg = marioRunR2.getImage();
            imgCounter = 0;
        }
        
        imgCounter++;
        
    }
    /**
     * run animation for mario running left.
     */
    protected void runLeftAnimate() {
        
        if (imgCounter == 0)
        {
            playerImg = marioRunL0.getImage();
        }
        else if (imgCounter == 3)
        {
            playerImg = marioRunL1.getImage();
        }
        else if (imgCounter == 6)
        {
            playerImg = marioRunL2.getImage();
            imgCounter = 0;
        }
        
        imgCounter++;
        
    }
    
    /**
     * move method which updates Mario's position based on game events and
     * keyboard inputs. Handles jumping, moving left & right, and player death.
     * @param action, the movement action to handle
     */
    public void move(String action)
    {   

        if (action.equals("stomp"))
        {
            //System.out.println("jumping on goomba");
            jumpCap = 460;
            jump = true;
            falling = false;
            score+=10;
        }
        
        if (action.equals("dead"))
        {
            hit = true;
            lives--;

            if(lives <= 0)
            {
                playerImg = marioDead.getImage();
                dy = 2;
                dx = 0;
                jumpCap = 580;
                floorHeight = 900;
                toDie = true;
                jump = true;
                falling = false;
            }

        }
        
        if (y > 800)
        {
            isDead = true;
        }
        
        if (x+dx > 0 && x+dx < 930)
        {
            x += dx;
        }
      
        if (y-dy > jumpCap && !falling && jump)
        {
            y -= dy;
        }
        else if (y+dy < floorHeight)
        {
            falling = true;
            
            y += dy;
        }
        else if (falling && y == floorHeight-1)
        {
            falling = false;
            jump = false;
            jumpCap = 490;
        }
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
     * accessor method to return image width
     * @return image width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * accessor method to return image height
     * @return image height
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * accessor method to return current image
     * @return current mario image
     */
    public Image getImage()
    {
        return playerImg;
    }
    
    /**
     * accessor method to determine if player is dead
     * @return player alive status
     */
    public boolean isDead()
    {
        return isDead;
    }
    
    /**
     * accessor method to return if player is currently colliding with Goomba
     * @return collide status of player
     */
    public boolean gotHit()
    {
        return hit;
    }
    
    /**
     * method to set the player hit status
     * @param toSet status of player if being hit or not
     */
    public void setHit(boolean toSet)
    {
        hit = toSet;
    }
    
    /**
     * accessor method to return if player is to die, helpful for starting the
     * death animation
     * @return toDie status
     */
    public boolean toDie()
    {
        return toDie;
    }
    
    /**
     * accessor method to return player score
     * @return current score
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * adds a life to the player
     */
    public void addLife()
    {
        lives++;
    }
    
    /**
     * accessor method to return the player lives
     * @return current lives
     */
    public int getLives()
    {
        return lives;
    }
    

    /**
     * handles incoming pressed key Events, if right keys it sets the players 
     * to move coordinates accordingly, starts animation for running if a or d
     * pressed.
     * @param e incoming pressed key
     */
    public void pressedKey(KeyEvent e)
    {
        if(toDie())
        {
            return;
        }
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_A)
        {
            dx = -3;
            
            if (!animateStart)
            {
                animateStart = true;
                animationTimer.scheduleAtFixedRate(createNewAnimationTask(), 0, 50);
            }
        }
        
        if (key == KeyEvent.VK_D)
        {
            dx = 3;
            if (!animateStart)
            {
                animateStart = true;
                animationTimer.scheduleAtFixedRate(createNewAnimationTask(), 0, 50);
            }

        }
        
        if (key == KeyEvent.VK_W)
        {   
            dy = 4;
            jump = true;
        }
    }
    
    /**
     * handles incoming released key Events, stops player movement as key
     * released should stop equal stopping of player movement. Also sets image
     * back to default
     * @param e incoming pressed key
     */
    public void releasedKey(KeyEvent e)
    {
        if (toDie())
        {
            return;
        }
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_A)
        {
            playerImg = marioLeft.getImage();
            dx = 0;
            imgCounter = 0;
            animationTimer.cancel();
            createNewTimer();
            animateStart = false;
        }
        
        if(key == KeyEvent.VK_D)
        {
            playerImg = marioRight.getImage();
            dx = 0;
            imgCounter = 0;
            animationTimer.cancel();
            createNewTimer();
            animateStart = false;
        }
    }
    
}
