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
    GreenfootSound elephantSound = new GreenfootSound("elephant-scream.mp3");
    GreenfootImage idleRight[]  = new GreenfootImage[8];
    GreenfootImage idleLeft[]  = new GreenfootImage[8];
    
    String facing = "right";
    SimpleTimer animationTimer = new SimpleTimer();
    //"images/idleAnimation/Idle1.png"
    public Elephant() 
    {
        for (int i = 0; i < idleRight.length; i++) 
        {
            idleRight[i] = new GreenfootImage("images/idleAnimation/idle" + i + ".png");
            idleRight[i].scale(100, 100);
        }
        
        for (int i = 0; i < idleLeft.length; i++) 
        {
            idleLeft[i] = new GreenfootImage("images/idleAnimation/idle" + i + ".png");
            idleLeft[i].mirrorHorizontally();
            idleLeft[i].scale(100, 100);
        }
        animationTimer.mark();
        
        setImage(idleRight[0]);
    }
    
    int imageIndex = 0; 
    public void animateIdle() 
    {
        if(animationTimer.millisElapsed() < 100) 
        {
            return;
        }        
        animationTimer.mark();
        if(facing.equals("right")) 
        {
            setImage(idleRight[imageIndex]);
            imageIndex = (imageIndex + 1) %idleRight.length;
        }
        else 
        {
            setImage(idleLeft[imageIndex]);
            imageIndex = (imageIndex + 1) %idleLeft.length;
        }
    }
    /*GreenfootImage[] right = new GreenfootImage[2];
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
            facing = "left";
            //animateElephantLeft();
        }
        else if (Greenfoot.isKeyDown("right")) 
        {
            move(5);
            //animateElephantRight();
            facing = "right";
        }
        eat();
        animateIdle();
    }
    public void eat() 
    {
        if(isTouching(Apple.class)) 
        {
            removeTouching(Apple.class);
            MyWorld world = (MyWorld) getWorld();
            world.createApple();
            world.increaseScore();
            elephantSound.play();
        }
    }
}
