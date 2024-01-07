import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * AttackTypeIndicator is in the top left corner of the screen, and shows if the player is using Melee or Ranged attacks
 * 
 * @author Joey Guan, Harishan Ganeshanathan, Anthony Ung
 * @version January 21
 */
public class AttackTypeIndicator extends UI
{
    private GreenfootImage sword = new GreenfootImage("SWORDUI.png");
    private GreenfootImage dagger = new GreenfootImage("daggerUI.png");
    /**
     * Constructor for AttackTypeIndicator
     */
    public AttackTypeIndicator()
    {
        dagger.scale(100,100);
        sword.scale(100,100);
    }
    /**
     * Simple Act Method
     */
    public void act()
    {
        GameWorld world = (GameWorld) getWorld();
        String[] playerInfo = world.getArrValues();
        boolean ranged = Boolean.parseBoolean(playerInfo[0]); 
        if(ranged)
        {
            setImage(dagger);
        }
        else
        {
            setImage(sword);
        }
    }
}
