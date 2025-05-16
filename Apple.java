import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Apple extends Actor
{
    int speed = 1;

    public Apple() {
        GreenfootImage apple = new GreenfootImage("images/apple1.png");
        setImage(apple);
    }

    public void act()
    {
        MyWorld world = (MyWorld) getWorld();
    
        // Don't do anything if the game is over
        if (world.getGameOver()) return;
    
        setLocation(getX(), getY() + speed);
    
        if (getY() >= world.getHeight()) 
        {
            world.gameOver();
            world.removeObject(this);
        }
    }

    
    public void setSpeed(int spd) 
    {
        speed = spd;
    }
}
