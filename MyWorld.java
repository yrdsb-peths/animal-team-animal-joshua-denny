import greenfoot.*;

public class MyWorld extends World {
    private int score = 0;
    private int gameOverValue = 5;
    
    private Label gameOverLabel;
    private Label scoreLabel;
    Elephant elephant;
    
    public MyWorld() {
        super(900, 600, 1);
        elephant = new Elephant();
        addObject(elephant, getWidth()/2, getHeight()/2);
        
        scoreLabel = new Label("Score: 0",30);
        
        addObject(scoreLabel,scoreLabel.getFontSize()/2 + 30, scoreLabel.getFontSize()/2);

        if (score >= gameOverValue) 
        {
            gameOver();
        }
    }
    
    public void gameOver()
    {
        gameOverLabel = new Label("Game Over", 50);
        addObject(gameOverLabel,getWidth()/2, getHeight()/2);
        
        removeObject(elephant);
    }
    
    public void createApple()
    {
        //Apple apple = new Apple();
        //apple.setSpeed(level);
        //addObject(apple, Greenfoot.getRandomNumber(getWidth()), 0);
    }
}
