import greenfoot.*;

public class Obstacle extends Actor
{
    private int speed = 2;

    public Obstacle()
    {
        GreenfootImage img = new GreenfootImage("snake.png"); // Or any image you use
        img.scale(50, 50); // Resize to match your game
        setImage(img);
    }

    public void act()
    {
        MyWorld world = (MyWorld) getWorld();
    
        if (world.getGameOver()) return;
    
        setLocation(getX() - speed, getY());
    
        if (getX() < -getImage().getWidth()) {
            getWorld().removeObject(this);
        }
    }


    public void setSpeed(int s)
    {
        speed = s;
    }
}
