import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Parent class for ranged projectiles
 * 
 * @author Marco Luong, Harishan Ganeshanathan
 */
public class RangedProjectile extends Attack
{
    protected double speed = 10;
    protected int direction;
    
    /**
     * Main cosntructor
     * 
     * @param speed Speed of which the projectile travels at
     */
    public RangedProjectile(double speed){
        this.getImage().scale(25,25);
        this.speed = speed;
    }
}
