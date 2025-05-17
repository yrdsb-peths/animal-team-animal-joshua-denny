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
    int landingFrameDelay = 5;
    int landingFrameTimer = 0;

    // Jump preparation state
    boolean preparingJump = false;
    int jumpPrepDelay = 100; // milliseconds
    SimpleTimer jumpPrepTimer = new SimpleTimer();

    // Audio
    GreenfootSound elephantSound = new GreenfootSound("elephant-scream.mp3");

    // Animation arrays
    GreenfootImage idleRight[]  = new GreenfootImage[8];
    GreenfootImage idleLeft[]   = new GreenfootImage[8];
    GreenfootImage runRight[]   = new GreenfootImage[8];
    GreenfootImage runLeft[]    = new GreenfootImage[8];
    GreenfootImage jumpRight[]  = new GreenfootImage[10];
    GreenfootImage jumpLeft[]   = new GreenfootImage[10];

    final int floorHeight = 0;
    int size = 75;
    String facing = "right";

    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer jumpCooldownTimer = new SimpleTimer();
    int jumpCooldownDuration = 1000;
    int imageIndex = 0;

    public Elephant() 
    {
        setRotation(0);
        jumpCooldownTimer.mark();

        // Load idle and mirrored
        for (int i = 0; i < idleRight.length; i++) 
        {
            idleRight[i] = new GreenfootImage("images/idleAnimation/idle" + i + ".png");
            idleRight[i].scale(size, size);
            idleLeft[i] = new GreenfootImage("images/idleAnimation/idle" + i + ".png");
            idleLeft[i].mirrorHorizontally();
            idleLeft[i].scale(size, size);
        }

        // Load run and mirrored
        for (int i = 0; i < runRight.length; i++) 
        {
            runRight[i] = new GreenfootImage("images/running/run" + i + ".png");
            runRight[i].scale(size, size);
            runLeft[i] = new GreenfootImage("images/running/run" + i + ".png");
            runLeft[i].mirrorHorizontally();
            runLeft[i].scale(size, size);
        }

        // Load jump and mirrored
        for (int i = 0; i < jumpRight.length; i++) 
        {
            jumpRight[i] = new GreenfootImage("images/jump/jump" + i + ".png");
            jumpRight[i].scale(size, size);
            jumpLeft[i] = new GreenfootImage("images/jump/jump" + i + ".png");
            jumpLeft[i].mirrorHorizontally();
            jumpLeft[i].scale(size, size);
        }

        animationTimer.mark();
        setImage(idleRight[0]);
    }

    public void act()
    {
        World currentWorld = getWorld();

        if (currentWorld instanceof MyWorld) {
            MyWorld world = (MyWorld) currentWorld;
            if (world.getGameOver()) return;
        }

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

        // Prepare for jump if not already in delay
        if (Greenfoot.isKeyDown("space") && onGround && !preparingJump && jumpCooldownTimer.millisElapsed() > jumpCooldownDuration) 
        {
            prepareJump();
        }

        // Finish jump after delay
        if (preparingJump && jumpPrepTimer.millisElapsed() >= jumpPrepDelay)
        {
            executeJump();
            jumpCooldownTimer.mark();
            preparingJump = false;
        }

        handleJumping();

        if (!jumping && !justLanded && !preparingJump) 
        {
            animateIdle();
        }

        if (currentWorld instanceof MyWorld)
        {
            MyWorld world = (MyWorld) currentWorld;

            eat();

            if (isTouching(Obstacle.class)) 
            {
                world.gameOver();
            }
        }
        drawJumpCooldownBar();

    }

    public void prepareJump()
    {
        preparingJump = true;
        jumpPrepTimer.mark();

        GreenfootImage[] jumpSet = (facing.equals("right")) ? jumpRight : jumpLeft;
        setImage(jumpSet[0]); // Crouch/charge frame
    }

    public void executeJump()
    {
        yVelocity = jumpStrength;
        onGround = false;
        jumping = true;

        GreenfootImage[] jumpSet = (facing.equals("right")) ? jumpRight : jumpLeft;
        setImage(jumpSet[1]); // Launch frame
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
            GreenfootImage[] jumpSet = (facing.equals("right")) ? jumpRight : jumpLeft;

            if (yVelocity < -10) {
                setImage(jumpSet[1]);
            } else if (yVelocity < -3) {
                setImage(jumpSet[2]);
            } else if (yVelocity <= 0) {
                setImage(jumpSet[3]);
            } else if (yVelocity <= 6) {
                setImage(jumpSet[4]);
            } else if (yVelocity <= 12) {
                setImage(jumpSet[5]);
            } else {
                setImage(jumpSet[6]);
            }
        }

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

        if (justLanded)
        {
            GreenfootImage[] jumpSet = (facing.equals("right")) ? jumpRight : jumpLeft;

            if (landingFrameCounter < 3)
            {
                if (landingFrameTimer == 0) 
                {
                    if (landingFrameCounter == 1) 
                    {
                        GreenfootImage baseImg = jumpSet[7 + landingFrameCounter];
                        GreenfootImage img = new GreenfootImage(baseImg);
                        img.scale((int)(baseImg.getWidth() * 1.1), (int)(baseImg.getHeight() * 0.9));
                        setImage(img);
                    }
                    else
                    {
                        GreenfootImage baseImg = jumpSet[7 + landingFrameCounter];
                        GreenfootImage img = new GreenfootImage(baseImg);
                        img.scale(size, size);
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
                justLanded = false;
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
    
    public void drawJumpCooldownBar()
    {
        int barWidth = 50;
        int barHeight = 6;
        int offsetY = 75;
    
        GreenfootImage baseImage = new GreenfootImage(getImage()); // Copy current image
        GreenfootImage bar = new GreenfootImage(barWidth, barHeight);
    
        int elapsed = jumpCooldownTimer.millisElapsed();
        int fillWidth = Math.min((int)((double)elapsed / jumpCooldownDuration * barWidth), barWidth);
    
        // Colors
        bar.setColor(Color.GRAY);   // Background
        bar.fill();
    
        bar.setColor(Color.GREEN);  // Fill
        bar.fillRect(0, 0, fillWidth, barHeight);
    
        baseImage.drawImage(bar, (baseImage.getWidth() - barWidth) / 2, baseImage.getHeight() - offsetY);
    
        setImage(baseImage);
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
