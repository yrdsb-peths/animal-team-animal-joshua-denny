import greenfoot.*;

public class MyWorld extends World {
    private int score = 0;
    
    private Label scoreLabel;
    Elephant elephant;
    
    public MyWorld() {
        super(900, 600, 1);
        elephant = new Elephant();
        addObject(elephant, getWidth()/2, getHeight()/2);
        
        scoreLabel = new Label("Score: 0",30);
        
        addObject(scoreLabel,scoreLabel.getFontSize()/2 + 30, scoreLabel.getFontSize()/2);

    }
    
    public void createApple()
    {
        //Apple apple = new Apple();
        //apple.setSpeed(level);
        //addObject(apple, Greenfoot.getRandomNumber(getWidth()), 0);
    }
}
