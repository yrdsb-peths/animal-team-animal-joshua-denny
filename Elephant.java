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
    int yVelocity = 0;
    int gravity = 1;
    int jumpStrength = -15;
    boolean onGround = false;

    
    GreenfootSound elephantSound = new GreenfootSound("elephant-scream.mp3");
    GreenfootImage idleRight[]  = new GreenfootImage[8];
    GreenfootImage idleLeft[]  = new GreenfootImage[8];
    GreenfootImage runRight[] = new GreenfootImage[8];
    GreenfootImage runLeft[] = new GreenfootImage[8];
    String facing = "right";
    SimpleTimer animationTimer = new SimpleTimer();
    
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
        
        for (int i = 0; i < runRight.length; i++) 
        {
            runRight[i] = new GreenfootImage("images/running/run" + i + ".png");
            runRight[i].scale(100, 100);
        }
        
        for (int i = 0; i < runLeft.length; i++) 
        {
            runLeft[i] = new GreenfootImage("images/running/run" + i + ".png");
            runLeft[i].mirrorHorizontally();
            runLeft[i].scale(100, 100);
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
        if(facing.equals("right") && Greenfoot.isKeyDown("right") == false) 
        {
            setImage(idleRight[imageIndex]);
            imageIndex = (imageIndex + 1) %idleRight.length;
        }
        else if (facing.equals("left") && Greenfoot.isKeyDown("left") == false)
        {
            setImage(idleLeft[imageIndex]);
            imageIndex = (imageIndex + 1) %idleLeft.length;
        }
        
        if(facing.equals("right") && Greenfoot.isKeyDown("right") == true) 
        {
            setImage(runRight[imageIndex]);
            imageIndex = (imageIndex + 1) %runRight.length;
        }
        else if (facing.equals("left") && Greenfoot.isKeyDown("left") == true) 
        {
            setImage(runLeft[imageIndex]);
            imageIndex = (imageIndex + 1) %runLeft.length;
        }
    }
    
    public void act()
    {
        // Add your action code here.
        if(Greenfoot.isKeyDown("left")) 
        {
            move(-5);
            facing = "left";
            
        }
        else if (Greenfoot.isKeyDown("right")) 
        {
            move(5);
            
            facing = "right";
        }
        
        if (Greenfoot.isKeyDown("space") && onGround)
        {
            jump();
        }
        applyGravity();
        eat();
        animateIdle();
    }
    public void jump()
    {
        yVelocity = jumpStrength;
        onGround = false;
    }
    public void applyGravity()
    {
        yVelocity += gravity;
        setLocation(getX(), getY() + yVelocity);
    
        int groundLevel = getWorld().getHeight() - getImage().getHeight() / 2;
    
        if (getY() >= groundLevel)
        {
            setLocation(getX(), groundLevel);
            yVelocity = 0;
            onGround = true;
        }
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
