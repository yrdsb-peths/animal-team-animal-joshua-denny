import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Elephant extends Actor
{
    // Physics and state
    int yVelocity = 0;
    int gravity = 1;
    int jumpStrength = -15;
    boolean onGround = true;
    boolean jumping = false;
    boolean justLanded = false;
    int landingFrameCounter = 0;

    // Audio
    GreenfootSound elephantSound = new GreenfootSound("elephant-scream.mp3");

    // Animation arrays
    GreenfootImage idleRight[]  = new GreenfootImage[8];
    GreenfootImage idleLeft[]   = new GreenfootImage[8];
    GreenfootImage runRight[]   = new GreenfootImage[8];
    GreenfootImage runLeft[]    = new GreenfootImage[8];
    GreenfootImage jumpFrames[] = new GreenfootImage[10];  // jump0.png to jump9.png

    String facing = "right";
    SimpleTimer animationTimer = new SimpleTimer();
    int imageIndex = 0;

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

        for (int i = 0; i < jumpFrames.length; i++) 
        {
            jumpFrames[i] = new GreenfootImage("images/jump/jump" + i + ".png");
            jumpFrames[i].scale(100, 100);
        }

        animationTimer.mark();
        setImage(idleRight[0]);
    }

    public void act()
    {
        // Movement input
        if (Greenfoot.isKeyDown("left")) 
        {
            move(-5);
            facing = "left";
        } 
        else if (Greenfoot.isKeyDown("right")) 
        {
            move(5);
            facing = "right";
        }

        // Jump input
        if (Greenfoot.isKeyDown("space") && onGround) 
        {
            jump();
        }

        // Jumping physics and animation
        handleJumping();

        // Eat logic
        eat();

        // Idle/run animation only when not jumping or landing
        if (!jumping && !justLanded) 
        {
            animateIdle();
        }
    }

    public void jump()
    {
        yVelocity = jumpStrength;
        onGround = false;
        jumping = true;
    }

    public void handleJumping()
    {
        yVelocity += gravity;
        setLocation(getX(), getY() + yVelocity);

        int groundLevel = getWorld().getHeight() - getImage().getHeight() / 2;

        if (!onGround)
        {
            justLanded = false;

            if (yVelocity < -10) {
                setImage(jumpFrames[1]);
            } 
            else if (yVelocity < -3) {
                setImage(jumpFrames[2]);
            } 
            else if (yVelocity <= 0) {
                setImage(jumpFrames[3]); // Apex
            } 
            else if (yVelocity <= 6) {
                setImage(jumpFrames[4]); // Descending
            } 
            else if (yVelocity <= 12) {
                setImage(jumpFrames[5]); // Faster descent
            } 
            else {
                setImage(jumpFrames[6]); // Near landing
            }
        }

        // Detect landing
        if (getY() >= groundLevel)
        {
            if (!onGround)
            {
                setLocation(getX(), groundLevel);
                yVelocity = 0;
                onGround = true;
                jumping = false;
                justLanded = true;
                landingFrameCounter = 0;
            }
        }

        // Play landing animation only once after landing
        if (justLanded)
        {
            if (landingFrameCounter < 3)
            {
                setImage(jumpFrames[7 + landingFrameCounter]);
                landingFrameCounter++;
            }
            else
            {
                justLanded = false; // Done landing animation
            }
        }
    }

    public void animateIdle() 
    {
        if (animationTimer.millisElapsed() < 100) return;
        animationTimer.mark();

        if (facing.equals("right") && !Greenfoot.isKeyDown("right")) 
        {
            setImage(idleRight[imageIndex]);
            imageIndex = (imageIndex + 1) % idleRight.length;
        } 
        else if (facing.equals("left") && !Greenfoot.isKeyDown("left")) 
        {
            setImage(idleLeft[imageIndex]);
            imageIndex = (imageIndex + 1) % idleLeft.length;
        } 
        else if (facing.equals("right")) 
        {
            setImage(runRight[imageIndex]);
            imageIndex = (imageIndex + 1) % runRight.length;
        } 
        else if (facing.equals("left")) 
        {
            setImage(runLeft[imageIndex]);
            imageIndex = (imageIndex + 1) % runLeft.length;
        }
    }

    public void eat() 
    {
        if (isTouching(Apple.class)) 
        {
            removeTouching(Apple.class);
            MyWorld world = (MyWorld) getWorld();
            world.createApple();
            world.increaseScore();
            elephantSound.play();
        }
    }
}
