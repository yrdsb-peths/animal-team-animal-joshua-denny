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
        // Move the apple downwards
        setLocation(getX(), getY() + speed);

        MyWorld world = (MyWorld) getWorld();
        if(getY() >= world.getHeight()) 
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
