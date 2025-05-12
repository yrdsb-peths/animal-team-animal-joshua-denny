import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * title card + (intro music)
 * 
 * @author Denny Ung
 * @version May 12th 2025
 */
public class TitleScreen extends World
{
    Label titleLabel = new Label("THE. elepehanttt", 60);
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        addObject(titleLabel, getWidth()/2, 30);
        prepare();
    }
    
    public void act()
    {
        if(Greenfoot.isKeyDown("space"))
        {
            MyWorld myWorldgameworld = new MyWorld();
            Greenfoot.setWorld(myWorldgameworld);
        }

    }
    
    private void prepare()
    {
        Elephant elephant = new Elephant();
        addObject(elephant,271,144);
        Label label = new Label("Use \u2190 and \u2192 to move", 30);
        addObject(label,303,253);
        label.setLocation(302,252);
        label.setLocation(302,252);
        elephant.setLocation(314,159);
        label.setLocation(372,231);
        label.setLocation(336,231);
        label.setLocation(303,231);
        Label label2 = new Label("Use space to start", 30);
        addObject(label2,300,271);
        elephant.setLocation(280,127);
        label.setLocation(136,393);
        label2.setLocation(463,366);
        label2.setLocation(450,382);
        label2.setLocation(482,379);
        label2.setLocation(453,380);
        label2.setLocation(478,380);
        label.setLocation(259,378);
        label.setLocation(154,375);
        label.setLocation(147,380);
        elephant.setLocation(84,235);
    }
}
