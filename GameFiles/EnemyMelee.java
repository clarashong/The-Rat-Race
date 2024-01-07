import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Melee attack for the cat
 * 
 * @author Marco Luong
 */
public class EnemyMelee extends MeleeAttack
{
    private int spaceMoved = 0;
    private Enemies e;
    private GreenfootImage scratch;
    /**
     * Main constructor
     * 
     * @param e Enemy that spawns this attack
     */
    public EnemyMelee(int attackRange, Enemies e){
        super(attackRange);
        animationOffset = 0; 
        animated = false;
        this.e = e; 
        
        scratch = new GreenfootImage("scratchPH.png");
        scratch.scale(30,90);
        setImage(scratch);
    }
    
    public void addedToWorld(World gw){
        Player p = gw.getObjects(Player.class).get(0);
        turnTowards(p.getX(), p.getY());
    }
    
    public void act()
    {
       if(!animated){
           //animation
           animated = true; 
       }
       
       if(spaceMoved >= attackRange){
           Player p = (Player) getOneIntersectingObject(Player.class);
           if(p != null){
               p.takeDamage(e.getAttackDamage());
           }
           getWorld().removeObject(this);
       }
       else{
           move(5);
           spaceMoved += 5;
       }
    }
}
