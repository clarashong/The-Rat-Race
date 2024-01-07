import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;

 

/**
 * Melee Attack - This is a sword swing, that damages enemies within the range of the swing.
 *
 * @author Harishan Ganeshanathan & Anthony Ung
 * @version January 17
 */
public class PlayerMelee extends MeleeAttack
{
    private Player p;
    GreenfootImage[] up = new GreenfootImage[12];
    GreenfootImage[] left = new GreenfootImage[12];
    GreenfootImage[] right = new GreenfootImage[12];
    GreenfootImage[] down = new GreenfootImage[12];
   
    SimpleTimer animationTimer = new SimpleTimer();

    private int horiOffset =0;
    private int vertiOffset =0;
    private int imageIndex = 0;
    /**
     * Simple Constructor
     * @param attackRange The range of the sword swing
     * @param p The player that the sword swing belongs to
     */
    public PlayerMelee(int attackRange, Player p){
        super(attackRange); 
        this.p = p;
        animationOffset = 0;
       
        animated = false;
       
       
        animationTimer.mark();
        if(p.getDirection() == 1){
            setImage(new GreenfootImage("images/swordSwingLeft/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <left.length; i++){
            left[i] = new GreenfootImage ("images/swordSwingLeft/swordSwing_"+ i +".png");
            left[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
            }
           
        }else if(p.getDirection() == 2){
            setImage(new GreenfootImage("images/swordSwingRight/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <right.length; i++){
            right[i] = new GreenfootImage ("images/swordSwingRight/swordSwing_"+ i +".png");
            right[i].scale(200,100);
            //swingAnimation[i].offsetX(100);
            }
           
        }else if(p.getDirection() == 3){
            setImage(new GreenfootImage("images/swordSwingUp/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <up.length; i++){
            up[i] = new GreenfootImage ("images/swordSwingUp/swordSwing_"+ i +".png");
            up[i].scale(100,200);
            //swingAnimation[i].offsetX(100);
            }
        }else if(p.getDirection() == 4){
            setImage(new GreenfootImage("images/swordSwingDown/swordSwing_0.png"));
            this.getImage().scale(attackRange, attackRange);
            for(int i = 0; i <down.length; i++){
            down[i] = new GreenfootImage ("images/swordSwingDown/swordSwing_"+ i +".png");
            down[i].scale(100,200);
            //swingAnimation[i].offsetX(100);
            }
        }
    }
    /**
     * Simple Act Method - Offsets attack to be in front of the player and also damage enemies
     */
   
    public void animateSwing(){
        if(animationTimer.millisElapsed() < 10){
            return;
        }
        if(p.getDirection() == 1){
            setImage(left[imageIndex]);
            imageIndex = (imageIndex+1)%left.length;
        }else if(p.getDirection() ==2){
            setImage(right[imageIndex]);
            imageIndex = (imageIndex+1)%right.length;
        }else if(p.getDirection() == 3){
            setImage(up[imageIndex]);
            imageIndex = (imageIndex+1)%up.length;
        }else{
            setImage(down[imageIndex]);
            imageIndex = (imageIndex+1)%down.length;
        }
        animationTimer.mark();
    }
    public void act()
    {
        animateSwing();
        if(p.getDirection() == 4){
            vertiOffset = 20;
        }
        setLocation(p.getX(), p.getY()+vertiOffset);
        animationOffset++;
        for(EnemyProjectile ep : getIntersectingObjects(EnemyProjectile.class)){
            getWorld().removeObject(ep); 
        }
        if(animationOffset>=10){
            for(Enemies e : getObjectsInRange(attackRange, Enemies.class)){
                GameWorld w = (GameWorld)getWorld();
                String[] v = w.getArrValues();
                double dmg = Double.parseDouble(v[6]);
                e.takeDamage(dmg);
                if(p.getDirection() ==1){
                    e.setLocation(e.getX()-75, e.getY()); 
                }else if(p.getDirection() == 2){
                    e.setLocation(e.getX()+75, e.getY()); 
                }else if(p.getDirection() == 3){
                    e.setLocation(e.getX(), e.getY()-75); 
                }else if(p.getDirection() ==4){
                    e.setLocation(e.getX(), e.getY()+75); 
                }
                if(e.getHp() <= 0){
                    w.removeObject(e); 
                }
            }
            p.setAttackStatus(false);
            getWorld().removeObject(this);
        }
    }
}
