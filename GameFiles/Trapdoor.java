import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Gateway between Floors that open up once a player defeats all the enemies in the room 
 * 
 * @author Joey Guan
 * @version January 11
 */
public class Trapdoor extends Structures
{
    
    private GreenfootImage img = new GreenfootImage("TrapDoor.png");
    public Trapdoor(){
        img.scale(75,75);
        setImage(img); 
    }
    /**
     * Act - do whatever the TrapDoor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        GameWorld w = (GameWorld)getWorld();
        if(!getIntersectingObjects(Player.class).isEmpty()){
            w.setGoingToNextFloor(true);
            w.setDoneSpawning(false); 
        }
    }
}
