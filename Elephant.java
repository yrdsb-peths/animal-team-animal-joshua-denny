import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Elephant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Elephant extends Actor
{
    /**
     * Act - do whatever the Elephant wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    /*GreenfootSound elephantSound = new GreenfootSound("elephant-scream.mp3");
    GreenfootImage[] right = new GreenfootImage[2];
    GreenfootImage[] left = new GreenfootImage[2];
    public Elephant() 
    {
        for (int i = 0; i < right.length; i++) 
        {
            right[i] = new GreenfootImage("images/elephant-move/sprite" + i + ".png");
        }
        for (int i = 0; i < left.length; i++) 
        {
            left[i] = new GreenfootImage("images/elephant-move/sprite" + (i + 3) + ".png");
        }
        setImage(right[0]);
    }
    int imageIndex =0;
    public void animateElephantRight() 
    {
        setImage(right[imageIndex]);
        imageIndex = (imageIndex + 1) % right.length;

    }
    public void animateElephantLeft() 
    {
        setImage(left[imageIndex]);
        imageIndex = (imageIndex + 1) % left.length;

    }*/
    
    public void act()
    {
        // Add your action code here.
        if(Greenfoot.isKeyDown("left")) 
        {
            move(-5);
            //animateElephantLeft();
        }
        else if (Greenfoot.isKeyDown("right")) 
        {
            move(5);
            //animateElephantRight();
        }
        eat();
        
    }
    public void eat() 
    {
        if(isTouching(Apple.class)) 
        {
            removeTouching(Apple.class);
            MyWorld world = (MyWorld) getWorld();
            world.createApple();
            world.increaseScore();
            //elephantSound.play();
        }
    }
}
