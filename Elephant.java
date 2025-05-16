import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
//Changes to this class include adding a running animation, 
//adding gravity physics, adding a jumping and falling animation




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
    int landingFrameDelay = 5;  // frames to wait per landing animation frame
    int landingFrameTimer = 0;
    // Audio
    GreenfootSound elephantSound = new GreenfootSound("elephant-scream.mp3");

    // Animation arrays
    GreenfootImage idleRight[]  = new GreenfootImage[8];
    GreenfootImage idleLeft[]   = new GreenfootImage[8];
    GreenfootImage runRight[]   = new GreenfootImage[8];
    GreenfootImage runLeft[]    = new GreenfootImage[8];
    GreenfootImage jumpRight[]  = new GreenfootImage[10];  // jump0.png to jump9.png facing right
    GreenfootImage jumpLeft[]   = new GreenfootImage[10];  // mirrored jump images for left
    final int floorHeight = 0; // adjust this to your floor/platform height

    String facing = "right";
    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer jumpCooldownTimer = new SimpleTimer();
    int jumpCooldownDuration = 500;
    int imageIndex = 0;

    public Elephant() 
    {
        setRotation(0);
        jumpCooldownTimer.mark();  // start timer

        // Load idle right and left
        for (int i = 0; i < idleRight.length; i++) 
        {
            idleRight[i] = new GreenfootImage("images/idleAnimation/idle" + i + ".png");
            idleRight[i].scale(100, 100);

            idleLeft[i] = new GreenfootImage("images/idleAnimation/idle" + i + ".png");
            idleLeft[i].mirrorHorizontally();
            idleLeft[i].scale(100, 100);
        }

        // Load running right and left
        for (int i = 0; i < runRight.length; i++) 
        {
            runRight[i] = new GreenfootImage("images/running/run" + i + ".png");
            runRight[i].scale(100, 100);

            runLeft[i] = new GreenfootImage("images/running/run" + i + ".png");
            runLeft[i].mirrorHorizontally();
            runLeft[i].scale(100, 100);
        }

        // Load jump frames right and left (mirrored)
        for (int i = 0; i < jumpRight.length; i++) 
        {
            jumpRight[i] = new GreenfootImage("images/jump/jump" + i + ".png");
            jumpRight[i].scale(100, 100);

            jumpLeft[i] = new GreenfootImage("images/jump/jump" + i + ".png");
            jumpLeft[i].mirrorHorizontally();
            jumpLeft[i].scale(100, 100);
        }

        animationTimer.mark();
        setImage(idleRight[0]);
    }

    public void act()
    {
        World currentWorld = getWorld();
    
        // If in MyWorld and game is over, stop all actions
        if (currentWorld instanceof MyWorld) {
            MyWorld world = (MyWorld) currentWorld;
            if (world.getGameOver()) {
                return;  // Stop all movement and logic if game is over
            }
        }
    
        // Movement always allowed (unless game over above)
        if (Greenfoot.isKeyDown("left")) 
        {
            move(-6);
            facing = "left";
        } 
        else if (Greenfoot.isKeyDown("right")) 
        {
            move(6);
            facing = "right";
        }
    
        // Jump input
        // Jump input with cooldown
        if (Greenfoot.isKeyDown("space") && onGround && jumpCooldownTimer.millisElapsed() > jumpCooldownDuration) 
        {
            jump();
            jumpCooldownTimer.mark();  // reset cooldown timer
        }

    
        // Handle physics and animation
        handleJumping();
        if (!jumping && !justLanded) 
        {
            animateIdle();
        }
    
        // Gameplay logic (apples & obstacles) only in MyWorld
        if (currentWorld instanceof MyWorld)
        {
            MyWorld world = (MyWorld) currentWorld;
    
            eat();
    
            if (isTouching(Obstacle.class)) 
            {
                world.gameOver();
            }
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
        if (!onGround) 
        {
            yVelocity += gravity;
            setLocation(getX(), getY() + yVelocity);
        }

        if (!onGround)
        {
            justLanded = false;

            // Set jumping animation frames based on yVelocity
            GreenfootImage[] jumpSet = (facing.equals("right")) ? jumpRight : jumpLeft;

            if (yVelocity < -10) {
                setImage(jumpSet[1]);
            } 
            else if (yVelocity < -3) {
                setImage(jumpSet[2]);
            } 
            else if (yVelocity <= 0) {
                setImage(jumpSet[3]); // Apex
            } 
            else if (yVelocity <= 6) {
                setImage(jumpSet[4]); // Descending
            } 
            else if (yVelocity <= 12) {
                setImage(jumpSet[5]); // Faster descent
            } 
            else {
                setImage(jumpSet[6]); // Near landing
            }
        }

        // Detect landing
        int groundLevel = getWorld().getHeight() - floorHeight - getImage().getHeight() / 2;

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
                landingFrameTimer = 0;
            }
        }

        // Play landing animation only once after landing with slowdown and squash/stretch
        if (justLanded)
        {
            GreenfootImage[] jumpSet = (facing.equals("right")) ? jumpRight : jumpLeft;

            if (landingFrameCounter < 3)
            {
                // Slow down animation: update frame every landingFrameDelay acts
                if (landingFrameTimer == 0) 
                {
                    setImage(jumpSet[7 + landingFrameCounter]);

                    // Add squash/stretch on impact frame (landingFrameCounter == 1)
                    if (landingFrameCounter == 1) 
                    {
                        GreenfootImage img = getImage();
                        img.scale((int)(img.getWidth() * 1.1), (int)(img.getHeight() * 0.9)); // wider & shorter
                        setImage(img);
                    }
                    else
                    {
                        // Reset scale on other frames
                        GreenfootImage img = jumpSet[7 + landingFrameCounter];
                        img.scale(100, 100);
                        setImage(img);
                    }
                }

                landingFrameTimer++;
                if (landingFrameTimer >= landingFrameDelay)
                {
                    landingFrameTimer = 0;
                    landingFrameCounter++;
                }
            }
            else
            {
                justLanded = false; // Done landing animation

                // Reset to idle image facing correct direction after landing animation
                GreenfootImage[] idleSet = (facing.equals("right")) ? idleRight : idleLeft;
                setImage(idleSet[0]);
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
