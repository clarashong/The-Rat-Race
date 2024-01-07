import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Projectile for ranged enemy.
 * 
 * @author Marco Luong
 */
public class EnemyProjectile extends RangedProjectile
{
    private Enemies e; 
    private int pX, pY, projectileSize = 25;
    /**
     * Main constructor.
     * 
     * @param buffed Projectile with an enlarged size
     * @param pX Player x coordinate
     * @param pY Player Y coordinate
     * @param e The enemy that shot the projectile
     */
    public EnemyProjectile(double speed, boolean buffed, int pX, int pY, Enemies e){
        super(speed);
        setImage("BirdProjectile.png");
        
        if(buffed){ // Enlarged projectile
            projectileSize *= 2;
            getImage().scale(projectileSize, projectileSize);
            projectileSize /= 2;
        }
        else{
            getImage().scale(projectileSize, projectileSize);
        }
        
        this.e = e;
        this.pX = pX;
        this.pY = pY;
    }
    
    public void addedToWorld(World w){
        turnTowards(pX, pY);        
    }
    
    // Moves in direction of the player
    public void act()
    {
        move(speed);
        //if the projectily hits a wall or door, it removes itself
        if(getOneIntersectingObject(Player.class)!=null){
            Player p = (Player)getOneIntersectingObject(Player.class);
            p.takeDamage(e.getAttackDamage()); 
            getWorld().removeObject(this);
        }
        else if(this.isTouching(Door.class)||this.isTouching(Wall.class)){ 
            getWorld().removeObject(this);
        }
    }
}
