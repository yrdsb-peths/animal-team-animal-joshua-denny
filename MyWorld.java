import greenfoot.*;

public class MyWorld extends World {
    public int score = 0;
    Label scoreLabel;
    int level = 1;
    public MyWorld() {
        super(640, 359, 1, false);
        
        Elephant elephant  = new Elephant();
        addObject(elephant, 300, 300);
        
        scoreLabel = new Label(0, 20);
        addObject(scoreLabel, 20, 20);
        
        createApple();
    }
    
    public void gameOver() 
    {
        Label gameOverLabel = new Label("GameOver", 100);
        addObject(gameOverLabel, 300, 200);
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
        Apple apple = new Apple();
        apple.setSpeed(level);
        int x = Greenfoot.getRandomNumber(600);
        int y = Greenfoot.getRandomNumber(50);
        addObject(apple, x, y);
    }
}
