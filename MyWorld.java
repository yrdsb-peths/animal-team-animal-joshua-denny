import greenfoot.*;

public class MyWorld extends World {
    public int score = 0;
    Label scoreLabel;
    int level = 1;
    
    private boolean isGameOver = false;
    
    public boolean getGameOver()
    {
        return isGameOver;
    }
    
    public void gameOver()
    {
        isGameOver = true;
        showText("Game Over!", getWidth() / 2, getHeight() / 2);
    }


    
    SimpleTimer obstacleTimer = new SimpleTimer(); // Use this for timing
    public MyWorld() {
        super(640, 359, 1, false);
        
        Elephant elephant  = new Elephant();
        addObject(elephant, 300, 300);
        
        scoreLabel = new Label(0, 20);
        addObject(scoreLabel, 20, 20);
        
        createApple();
        Greenfoot.setWorld(new TitleScreen());
        
        GreenfootImage bg = new GreenfootImage("images/download.jpg");
        setBackground(bg);


    }
    
    public void increaseScore() 
    {
        score++;
        scoreLabel.setValue(score);
        
        if(score % 5 == 0) {
            level += 1;
        }
    }
    
    public void createApple()
    {
        if (isGameOver) return; // Don't spawn apple if game is over
    
        Apple apple = new Apple();
        apple.setSpeed(level);
        int x = Greenfoot.getRandomNumber(600);
        int y = Greenfoot.getRandomNumber(50);
        addObject(apple, x, y);
    }

    public void act()
    {
        if (isGameOver) return; // Stop everything if game is over
    
        if (obstacleTimer.millisElapsed() > Greenfoot.getRandomNumber(20000) + 5000)  // random long delay
        {
            spawnObstacle();
            obstacleTimer.mark();
        }
    }

    
    public void spawnObstacle()
    {
        Obstacle snake = new Obstacle();
        
        // Get Y position of elephant's feet (ground level)
        int groundY = getHeight() - 50 - snake.getImage().getHeight() / 2;
            
        addObject(snake, getWidth(), groundY);
    }


}
