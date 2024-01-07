import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Dagger thrown by the player's ranged attack
 * 
 * @author Harishan Ganeshanathan
 */
public class PlayerProjectile extends RangedProjectile
{
    private Player p; 
    private GreenfootImage[] clockwise = new GreenfootImage[12];
    private GreenfootImage[] counterClockwise = new GreenfootImage[12]; 
    SimpleTimer animationTimer = new SimpleTimer(); 
    private int imageIndex =0; 
    public PlayerProjectile(double speed, int facing, Player p){
        super(speed);
        this.getImage().scale(25,25);
        //1 is left, 2 is right, 3 is up, 4 is down
        this.direction = facing; 
        this.p = p; 
        
        animationTimer.mark(); 
        if(direction == 2 || direction == 3){//clockwise
            setImage(new GreenfootImage("images/ClockwiseDagger/daggerAnimation_0.png"));
            this.getImage().scale(75, 75); 
            for(int i =0;i<clockwise.length;i++){
                clockwise[i] = new GreenfootImage("images/ClockwiseDagger/daggerAnimation_"+i+".png");
                clockwise[i].scale(75, 75); 
            }
        }else if(direction == 1 || direction == 4){//counter clockwise
            setImage(new GreenfootImage("images/CounterClockwiseDagger/daggerAnimation_0.png"));
            this.getImage().scale(75, 75); 
            for(int i =0;i<counterClockwise.length;i++){
                counterClockwise[i] = new GreenfootImage("images/CounterClockwiseDagger/daggerAnimation_"+i+".png");
                counterClockwise[i].scale(75, 75); 
            }
        }
    }
    public void animate(){
        if(animationTimer.millisElapsed()<30){
            return;
        }
        if(direction == 2||direction == 3){
            setImage(clockwise[imageIndex]);
            imageIndex =  (imageIndex+1)%clockwise.length; 
        }
        else if(direction == 1||direction == 4){
            setImage(counterClockwise[imageIndex]);
            imageIndex =  (imageIndex+1)%clockwise.length; 
        }
    }
    public void act(){
        // Add your action code here.
        if(direction == 1){
            //if(Greenfoot.isKeyDown("A")){setLocation(this.getExactX()-(speed+(p.getSpeed()/1.5)), this.getExactY());}
            setLocation(this.getExactX()-speed, this.getExactY());
        }
        if(direction == 2){
            //if(Greenfoot.isKeyDown("D")){setLocation(this.getExactX()+(speed+(p.getSpeed()/1.5)), this.getExactY());}
            setLocation(this.getExactX()+speed, this.getExactY());
        }
        if(direction == 3){
           // if(Greenfoot.isKeyDown("W")){setLocation(this.getExactX(), this.getExactY()-(speed+(p.getSpeed()/1.5)));}
           setLocation(this.getExactX(), this.getExactY()-speed);
        }
        if(direction == 4){
           // if(Greenfoot.isKeyDown("S")){setLocation(this.getExactX(), this.getExactY()+(speed+(p.getSpeed()/1.5)));}
            setLocation(this.getExactX(),this.getExactY()+speed);
        }
        animate(); 
        //if the arrow hits the end of the world, it removes itself
        if(getOneIntersectingObject(Enemies.class)!=null){
            Enemies e = (Enemies)getOneIntersectingObject(Enemies.class);
            GameWorld w = (GameWorld)getWorld();
            String[] v = w.getArrValues(); 
            double dmg = Double.parseDouble(v[4]); 
            e.takeDamage(dmg); 
            if(e.getHp()<=0){
                w.removeObject(e); 
            }
            w.removeObject(this);
        }
        else if(this.isTouching(Door.class)||this.isTouching(Wall.class)){  
            getWorld().removeObject(this);
        }
    }
}
