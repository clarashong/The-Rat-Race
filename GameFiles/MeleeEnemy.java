import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A short-ranged enemy that will track the player and swipe/scratch in front 
 * of itself to deal damage. After a short cooldown, the melee will increase its
 * range to prepare for a 'lunge'. Lunging will propel the enemy in the player's 
 * direction once in range of detection, dealing damage if colliding with them.
 * 
 * @author Marco Luong, Harishan Ganeshanathan
 */
public class MeleeEnemy extends Enemies
{
    // Radius of the attack
    private int meleeRadius = 50;
    
    // Lunge attack variables
    private int lungeCD, lungeTimer, lungeAngle;
    private boolean lunging = false, lunged = false;

    /**
     * Constructor for Melee Enemies
     * 
     * @param hp HP of enemy 
     * @param spd Speed of enemy 
     * @param atkDmg Attack Damage of enemy
     */
    public MeleeEnemy(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Cat");
        range = 0;
        atkCD = 90;
        atkTimer = atkCD;
        
        lungeCD = 240;
        lungeTimer = lungeCD;
    }
    
    // Tracks player and attack cooldown will reset without 
    // needing to be in range of the player.
    public void act()
    {
        if(beenAttacked){
            if(direction == 1){
                setImage("CatRDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 2){
                setImage("CatLDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 3){
                setImage("CatUDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 4){
                setImage("CatDDamage.png"); 
                getImage().scale(100,100);
            }
            damagedTimer++; 
            if(damagedTimer == 15){
                damagedTimer = 0;
                beenAttacked = false;
            }
        }
        else{
            animate(direction - 1);
        }
        
        if(!lunging){
            trackPlayer();
            lungeTimer--; 
        }
        else{
            lunge();
        }
        super.act();
        
        if(lungeTimer <= 0){
            range = 3;
        }
        atkTimer--;       
    }
    /**
     * Attack Method for Melee Enemies
     */
    public void attack(){
        GameWorld gw = (GameWorld)getWorld();        
        if(atkTimer<=0){ // Only attack if in normal range
            moving = false;
            EnemyMelee em = new EnemyMelee(meleeRadius, this); 
            gw.addObject(em, getX(), getY());
            atkTimer = atkCD;
        }
        
        if(range == 3 && !lunging){ // Checks if melee enemy is ready to lunge; Prepares variables
            Player p = gw.getObjects(Player.class).get(0);
            lunging = true;
            attacking = true;
            moving = true;
            spd *= 3;
            turnTowards(p.getX(), p.getY());
            lungeAngle = getRotation();;
        }
    }
    
    /**
     * Special attack for melee. Increases movement speed and lets the enemy charge 
     * in one direction until a wall is met. Reset to previous settings after action done.
     */
    private void lunge(){
        setRotation(lungeAngle);
        move(spd);
        
        if(isTouching(Player.class) && !lunged){
            Player p = (Player)getOneIntersectingObject(Player.class);
            lunged = true;
            p.takeDamage(atkDmg);
            atkTimer = atkCD;
        }
        else if(isTouching(Wall.class) || isTouching(Door.class)){ // If hitting a wall
            lunging = false;
            lunged = false;
            attacking = false;
            lungeTimer = lungeCD;
            spd /= 3;
            range = 0;
            trackPlayer();
        }
    }
}
