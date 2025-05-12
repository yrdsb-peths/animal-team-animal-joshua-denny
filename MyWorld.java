import greenfoot.*;

public class MyWorld extends World {
    
    Elephant elephant;
    
    public MyWorld() {
        super(900, 600, 1);
        elephant = new Elephant();
        addObject(elephant, getWidth()/2, getHeight()/2);
    }
    
    public void createApple()
    {
        //Apple apple = new Apple();
        //apple.setSpeed(level);
        //addObject(apple, Greenfoot.getRandomNumber(getWidth()), 0);
    }
}
