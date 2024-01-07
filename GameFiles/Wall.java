import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Walls surround the room. Players cannot go into or past a wall. Obstacles are also
 * considered walls. 
 * 
 * @author Joey Guan, Clara Hong 
 * @version January 13
 */
public class Wall extends Structures
{
    private String obstacleType;
    //all the possible types: wall, statue, cart, couch, chair, fountain, bedFront, bedSide, wardrobe, smallTable, longTable
    //vase, vaseTable, nightTable, suitcase
    
    /**
    private GreenfootImage statue = new GreenfootImage("statue.png");
    private GreenfootImage cart = new GreenfootImage("cart.png");
    private GreenfootImage couch = new GreenfootImage("couch.png");
    private GreenfootImage chair = new GreenfootImage("chair.png");
    private GreenfootImage fountain = new GreenfootImage("fountain.png");
    private GreenfootImage bedFront = new GreenfootImage("statue.png");
    private GreenfootImage bedSide = new GreenfootImage("cart.png");
    */
    
    /**
     * Simple Constructor for Wall
     * 
     * @param type Type of obstacle, decides image and dimensions
     */
    public Wall(String type)
    {
        obstacleType = type;
        if(type.equals("wall"))
        {
            GreenfootImage image = getImage();
            image.setTransparency(0);
            image.scale(100, 100);
            setImage(image);
        } else {
            GreenfootImage image = new GreenfootImage(obstacleType + ".png"); 
            image.setTransparency(250); 
            setImage(image); 
        }
        
        
        /**
        if(type.equals("statue"))
        {
            GreenfootImage image = statue;
            image.setTransparency(250);
            image.scale(100, 100);
            setImage(image);
        }
        if(type.equals("cart"))
        {
            GreenfootImage image = cart;
            image.scale(100, 100);
            setImage(image);
        }
        if(type.equals("couch"))
        {
            GreenfootImage image = couch;
            image.scale(200, 100);
            setImage(image);
        }
        if(type.equals("chair"))
        {
            GreenfootImage image = chair;
            image.scale(100, 100);
            setImage(image);
        }
        if(type.equals("fountain"))
        {
            GreenfootImage image = fountain;
            image.scale(100, 100);
            setImage(image);
        }
        */
    }
    /**
     * Act - do whatever the Wall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
