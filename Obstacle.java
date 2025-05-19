import greenfoot.*;

public class Obstacle extends Actor
{
    private int speed = 2;
    private boolean side = false; // left = false, right = true; 

    public Obstacle(boolean Side)
    {
        GreenfootImage img = new GreenfootImage("snake.png"); // Or any image you use
        img.scale(50, 50); // Resize to match your game
        side = Side;
        setImage(img);
    }

    public void act()
    {
        MyWorld world = (MyWorld) getWorld();
    
        if (world.getGameOver()) return;
        
        if (side)
        {
            
            setLocation(getX() - speed, getY());        
            //Right side movement
            if (getX() < -getImage().getWidth()) 
            {
                getWorld().removeObject(this);
            }
        }
        else 
        {
            setLocation(getX() + speed, getY());
            
            if (getX() > world.getWidth() + getImage().getWidth()) 
            {
                getWorld().removeObject(this);
            }
            
        }
        
        
    }


    public void setSpeed(int s)
    {
        speed = s;
    }
}
