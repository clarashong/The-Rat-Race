import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A ranged enemy that will shoot projectiles in the player's direction.
 * 
 * @author Marco Luong
 */
public class RangedEnemy extends Enemies
{
    private int shootCount = 0;
    /**
     * Constructor for Ranged Enemy
     * 
     * @param hp HP of enemy 
     * @param spd Speed of enemy 
     * @param atkDmg Attack Damage of enemy
     */
    public RangedEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Bird");
        range = 5;
        atkCD = 90;
        atkTimer = atkCD;
    }
    /**
     * Act Method for Ranged Enemy
     */
    public void act()
    {
        if(beenAttacked){
            if(direction == 1){
                setImage("BirdLDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 2){
                setImage("BirdRDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 3){
                setImage("BirdUDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 4){
                setImage("BirdDDamage.png"); 
                getImage().scale(100,100);
            }
            damagedTimer++; 
            if(damagedTimer == 30){
                damagedTimer = 0;
                beenAttacked = false;
            }
        }
        else{
            animate(direction-1); 
        }
        trackPlayer();
        super.act();
    }
    
    /**
     * Shoot a projectile after attack cooldown is over
     */
    public void attack(){
        attacking = true;
        moving = false;
        GameWorld gw = (GameWorld)getWorld(); 
        Player p = gw.getObjects(Player.class).get(0);
        
        if(atkTimer<=0){
            shootCount++;
            EnemyProjectile ep;
            // After 5 shots are fired, the fifth projectile size will increase slightly
            if(shootCount >= 5){
                ep = new EnemyProjectile(spd*2.0, true, p.getX(), p.getY(), this);
                shootCount = 0;
            }
            else{
                ep = new EnemyProjectile(spd*2.0, false, p.getX(), p.getY(), this);
            }
            
            gw.addObject(ep, this.getX(), this.getY()); 
            atkTimer = atkCD; 
        }
        atkTimer--;
    }
}
