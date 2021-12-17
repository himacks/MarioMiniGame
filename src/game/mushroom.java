package game;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Random;

/**
 * mushroom class for individual mushroom within the mario minigame,
 * consumable item and gives health to player
 * @author Max & Lucas
 *
 */
public class mushroom {
    
    private int x, dx, y, dy, width, height, moveDirection;
    private ImageIcon mushroom1 = new ImageIcon("images/mushroom.png");
    private Image mushroomImg;
    private Random rand;
    private boolean isAlive;
    
    /**
     * constructor class which sets the default mushroom image and sets
     * image width and height.
     */
    public mushroom()
    {
        rand = new Random();
        width = 50;
        height = 50;
        mushroomImg = mushroom1.getImage();
        
    }
    
    /**
     * move method which updates mushrooms X & Y position every time is called.
     */
    public void move()
    {
        if(y < 630)
        {
            y += dy;
        }
        else
        {
            x += dx;
        }
        
        if (x < -100 || x > 1100)
        {
            isAlive = false;
        }
    }
    
    /**
     * accessor method to return current mushroom image
     * @return current image
     */
    public Image getImage()
    {
        return mushroomImg;
    }
    
    /**
     * accessor method to return if mushroom is alive and in the game
     * @return alive status
     */
    public boolean isAlive()
    {
        return isAlive;
    }
    
    /**
     * accessor method to return x position
     * @return x value of mushroom
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * accessor method to return y position
     * @return y value of mushroom
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
     * sets the mushroom to not be alive when consumed
     */
    public void consume()
    {
        isAlive = false;
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
     * class to spawn individual mushroom into game, sets it's random coordinates
     * and determines the direction it's to move.
     */
    public void startMoving()
    {
        x = rand.nextInt(1000);
        y = -50;
        dy = 3;
        isAlive = true;
        
        if(x < 500)
        {
            moveDirection = 1;
        }
        else
        {
            moveDirection = -1;
        }
        
        dx = moveDirection;
    }
}
