import greenfoot.*;

public class MyWorld extends World {
    public int score = 0;
    Label scoreLabel;
    int level = 1;
    final int floorHeight = 0;

    private boolean isGameOver = false;

    private Elephant elephant;
    SimpleTimer obstacleTimer = new SimpleTimer(); // Timer for obstacle spawning
    private int frameCounter = 0;
    private int spawnInterval = Greenfoot.getRandomNumber(400) + 100; // for example, between 100 and 500 frames

    
    public MyWorld() {
        super(640, 359, 1, false);

        // Add elephant
        elephant = new Elephant();
        addObject(elephant, 300, getHeight() - floorHeight - elephant.getImage().getHeight() / 2);

        // Add score label
        scoreLabel = new Label(0, 20);
        addObject(scoreLabel, 20, 20);

        // Set background
        GreenfootImage bg = new GreenfootImage("images/download.jpg");
        setBackground(bg);

        createApple(); // Initial apple spawn
    }

    public void increaseScore() {
        score++;
        scoreLabel.setValue(score);

        if (score % 5 == 0) {
            level += 1;
        }
    }

    public void createApple() {
        if (isGameOver) return; // Don't spawn if game is over

        Apple apple = new Apple();
        apple.setSpeed(level);
        int x = Greenfoot.getRandomNumber(600);
        int y = Greenfoot.getRandomNumber(50);
        addObject(apple, x, y);
    }

    public void act() {
        if (isGameOver) return;

        frameCounter++;
        
        if (frameCounter > spawnInterval) {
            spawnObstacle();
            frameCounter = 0;
            spawnInterval = Greenfoot.getRandomNumber(400) + 100;  // reset interval for next spawn
        }
    }

    public void spawnObstacle() {
        if (isGameOver) return; // Don't spawn if game is over
        
        boolean snakeSpawnSide = (Greenfoot.getRandomNumber(2) != 0); // left = false; right = true;

        Obstacle snake = new Obstacle(snakeSpawnSide);

        int floorY = getHeight() - floorHeight;
        int snakeHalfHeight = snake.getImage().getHeight() / 2;
        int snakePosY = floorY - snakeHalfHeight; // Moves the snake sprite up from the ground.
        int snakePosX = (snakeSpawnSide ? getWidth() : 0); // Randomizes the spawn side for the snake

        addObject(snake, snakePosX, snakePosY);
    }

    public void gameOver() {
        isGameOver = true;
        showText("Game Over!", getWidth() / 2, getHeight() / 2);
    }

    public boolean getGameOver() {
        return isGameOver;
    }
}
