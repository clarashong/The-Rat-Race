import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

/**
 * Melee parent class for organization
 * 
 * @author Marco Luong, Harishan Ganeshanathan
 */
public abstract class MeleeAttack extends Attack
{
    /**
     * Act - do whatever the MeleeAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected int attackRange;
    protected int animationOffset; 
    protected boolean animated;
    
    /**
     * Main cosntructor
     *
     * @param attackRange How far the melee checks for contact
     */
    public MeleeAttack(int attackRange){
        this.attackRange = attackRange; 
    }
}
