import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    Label titleLabel = new Label("Hungry Elephant", 60);
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(612, 388, 1); 

        addObject(titleLabel, getWidth()/2, 100);
        prepare();
        
        GreenfootImage bg = new GreenfootImage("images/istockphoto-1140829787-612x612.jpg");
        setBackground(bg);
    }

    public void act() 
    {
        if(Greenfoot.isKeyDown("space")) 
        {
            MyWorld gameWorld = new MyWorld();
            Greenfoot.setWorld(gameWorld);
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Elephant elephant = new Elephant();
        addObject(elephant,485,72);
        elephant.setLocation(503,68);
        elephant.setLocation(516,86);
        Label label = new Label("Use <-- and --> to Move", 50);
        addObject(label,259,296);
        label.setLocation(272,229);
        label.setLocation(273,229);
        Label label2 = new Label("Press <space> to Start", 50);
        addObject(label2,241,340);
        label2.setLocation(284,336);
        label2.setLocation(342,325);
        label2.setLocation(263,319);
        label.setLocation(367,228);
        elephant.setLocation(528,81);
        label.setLocation(358,196);
        label.setLocation(301,184);
        label2.setLocation(275,294);
        label.setLocation(305,211);
        label.setLocation(274,220);
        elephant.setLocation(514,72);
        elephant.setLocation(550,81);
        elephant.setLocation(311,24);
        elephant.setLocation(268,169);
    }
}
