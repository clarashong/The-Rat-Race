import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The main player can move around, dash, attack and switch their attack type
 * 
 * @author Harishan Ganeshanathan, Anthony Ung, Joey Guan
 * @version January 22th, 2023
 */
public class Player extends SmoothMover
{
    private int direction = 2; //1 = left, 2 = right, 3 = up, 4 = down
    //flag variables
    private boolean ranged;
    private boolean attacked;
    private boolean isAttacking; 
    private boolean attackSwitched = false;
    private boolean healthBarAdded = false;
    private int attackSwitchTimer; 
    private int rangeTimer; 
    private int meleeTimer; 
    
    //upgradable stats
    private double speed;
    private double projectilePower;
    private double attackPower; 
    private double armour; //damage reduction variable
    private double health;

    //extra values
    private double maxHealth;
    private double projectileSpeed = 10; 
    private int meleeRadius; 
    private int meleeReset; //attack resets every .5 seconds
    private int rangeReset; 
    private int level; 
    
    //dash variables 
    private boolean isDashing = false; 
    private boolean dashReady = false;
    private boolean dashed = false;
    private int dashTimer = 0; 
    private int dashCooldown = 0; 
    
    private SuperStatBar cooldown; 
    private DashIcon di; 
    private HpIcon hi; 
    private SuperStatBar healthBar;
    
    private GreenfootSound slash = new GreenfootSound("slash.mp3"); 
    
    //public Player(boolean ranged, int meleeRadius, int meleeSpeed, int rangeSpeed, double projectileSpeed, double speed,  double attackPower, double armour, double health)
    /**
     * Simple Constructor for Player to set values through parsing a String into integers, booleans and/or doubles
     * @param values A string array that holds the values for the players attack type, attack radius, attack timer, attack speed, player speed, attack power, armour and health
     */
    public Player(String[] values)//updated player constructor using an array of strings to manage parameters 
    {
        super("Player");
        //player stats
        this.ranged = Boolean.parseBoolean(values[0]);  
        this.meleeRadius = Integer.parseInt(values[1]); //100
        this.meleeReset = Integer.parseInt(values[2]); //30
        this.rangeReset = Integer.parseInt(values[3]); //30
        this.projectilePower = Double.parseDouble(values[4]); //5
        this.speed = Double.parseDouble(values[5]); //3
        this.attackPower = Double.parseDouble(values[6]); //8 
        this.armour = Double.parseDouble(values[7]); //0
        this.health = Double.parseDouble(values[8]); //25
        this.dashCooldown = Integer.parseInt(values[9]); //0
        this.maxHealth = Double.parseDouble(values[10]); 

        slash.setVolume(30); 
        
        
        attacked = false;
        isAttacking = false;
        rangeTimer = 0;
        meleeTimer = 0; 
        attackSwitchTimer = 0;
        
        di = new DashIcon(); 
        hi = new HpIcon(); 
        cooldown = new SuperStatBar(120, 0, null, 200, 20, 0, Color.WHITE, Color.YELLOW, false, Color.BLACK, 0);
    }

    public void addedToWorld()
    {
        healthBarAdded = false;
    }
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        GameWorld gw = (GameWorld)getWorld();
        gw.addObject(hi, 175, 30); 
        gw.addObject(di, 175, 65); 
        gw.addObject(cooldown, 300, 60); 
        moving = false; 
        if(!healthBarAdded)
        {
            updateHealthBar();
            healthBarAdded = true;
        }
        if(dashReady){
            if(Greenfoot.isKeyDown("N")){
                isDashing = true;
            }
        }
        if(!isDashing){
            movement(); 
            switchAttack();
        }
        if(!dashReady){
            dashCooldown++;
            String[] v = gw.getArrValues(); 
            v[9] = Integer.toString(dashCooldown);
            gw.setArrValues(v); 
        }
        if(isDashing){
            if(direction == 1){
                Wall wall = (Wall) getOneObjectAtOffset(-25, getImage().getHeight()/-2, Wall.class);
                Door door = (Door) getOneObjectAtOffset(-25, getImage().getHeight()/-2, Door.class);
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX()-25, getY());
                }
            }else if(direction == 2){
                Wall wall = (Wall) getOneObjectAtOffset(25, getImage().getHeight()/-2, Wall.class);
                Door door = (Door) getOneObjectAtOffset(25, getImage().getHeight()/-2, Door.class);
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX()+25, getY());
                }
            }else if(direction == 3){
                Wall wall = (Wall) getOneObjectAtOffset(getImage().getWidth()/-2, -25, Wall.class);
                Door door = (Door) getOneObjectAtOffset(getImage().getWidth()/-2, -25, Door.class);
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX(), getY()-25);
                }
            }else if(direction == 4){
                Wall wall = (Wall) getOneObjectAtOffset(getImage().getWidth()/-2, 25, Wall.class);
                Door door = (Door) getOneObjectAtOffset(getImage().getWidth()/-2, 25, Door.class);
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX(), getY()+25);
                }
            }
            dashTimer++;
            if(dashTimer == 8){
                dashTimer = 0;
                isDashing = false;
                dashReady = false;
                dashed = false; 
            }
        }
        if(dashCooldown == 120){
            dashReady = true;
            dashCooldown = 0; 
            String[] v = gw.getArrValues(); 
            v[9] = Integer.toString(dashCooldown);
            gw.setArrValues(v);
        }
        attack(); 
        //timer for attacks
        if(attacked){
            if(ranged){
                rangeTimer++;
            }
            else{
                meleeTimer++;
            }
        }
        if(ranged){
            if(rangeTimer>=rangeReset){ 
                attacking = false; 
                attacked = false; 
                rangeTimer = 0; 
            }
        }
        if(!ranged){
            if(meleeTimer>=meleeReset){
                attacking = false; 
                attacked = false;
                meleeTimer = 0;
            }
        }
        //switch/flag for attack type switching
        if(attackSwitched){
            attackSwitchTimer++;
        }
        if(attackSwitchTimer>=60){
            attackSwitched = false;
            attackSwitchTimer = 0;
        }
        animate (direction - 1); 
        String[] v = gw.getArrValues(); 
        cooldown.update(Integer.parseInt(v[9]));  
        actCounter++; 
    }
    
    /**
     * Method for player movement - Note that players cannot move when melee attacking
     */
    public void movement()
    {
        if(!isAttacking){
            if(Greenfoot.isKeyDown("W")) // up
            {
                Wall wall = (Wall) getOneObjectAtOffset(0, getImage().getHeight()/-2, Wall.class);
                Door door = (Door) getOneObjectAtOffset(0, getImage().getHeight()/-2, Door.class);
    
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX(), getY() - speed);
                }  
                direction = 3; 
                moving = true; 
            }
            if(Greenfoot.isKeyDown("A")) // left
            {
                Wall wall = (Wall) getOneObjectAtOffset(getImage().getWidth()/-2, 0, Wall.class);
                Door door = (Door) getOneObjectAtOffset(getImage().getWidth()/-2, 0, Door.class);
    
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX() - speed, getY());
                } 
                direction = 1;
                moving = true; 
            } 
            if(Greenfoot.isKeyDown("S")) // down
            {
                Wall wall = (Wall) getOneObjectAtOffset(0, getImage().getHeight()/2, Wall.class);
                Door door = (Door) getOneObjectAtOffset(0, getImage().getHeight()/2, Door.class);
    
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX(), getY() + speed);
                } 
                direction = 4;
                moving = true; 
            }
            if(Greenfoot.isKeyDown("D")) // right
            {
                Wall wall = (Wall) getOneObjectAtOffset(getImage().getWidth()/2, 0, Wall.class);
                Door door = (Door) getOneObjectAtOffset(getImage().getWidth()/2, 0, Door.class);
    
                if(wall == null && (door == null || door.getIsOpen())){
                    setLocation(getX() + speed, getY());
                } 
                direction =2;
                moving = true; 
            }
        }
    }
    /**
     * Attack method for Player
     */
    public void attack(){
        if(Greenfoot.isKeyDown("SPACE"))//attack
        {
            attacking = true; 
            GameWorld gw = (GameWorld)getWorld();
            if(!attacked){
                slash.play(); 
                if(ranged){
                    PlayerProjectile rp = new PlayerProjectile(projectileSpeed, direction, this);
                    gw.addObject(rp, this.getX(), this.getY()); 
                }
                else{
                    PlayerMelee ma = new PlayerMelee(meleeRadius, this); 
                    if(direction == 1){
                        gw.addObject(ma, this.getX()-10, this.getY()); 
                    } else if(direction == 2){
                        gw.addObject(ma, this.getX()+10, this.getY()); 
                    } else if(direction == 3){
                        gw.addObject(ma, this.getX(), this.getY()-10); 
                    }else if(direction == 4){
                        gw.addObject(ma, this.getX(), this.getY()+10); 
                    }  
                    isAttacking = true;
                }
                attacked = true; 
                attacking = false; //for the smooth mover animation
            }
        }
    }
    /**
     * Method for switching between ranged attack and melee attack
     */
    public void switchAttack(){
        if(!attackSwitched){
            if(Greenfoot.isKeyDown("Q")){
                GameWorld w = (GameWorld)getWorld(); 
                if(ranged){
                    ranged = false;
                    String[] v = w.getArrValues(); 
                    v[0] = Boolean.toString(ranged);
                    w.setArrValues(v);                
                }
                else if(!ranged){
                    ranged = true;
                    String[] v = w.getArrValues(); 
                    v[0] = Boolean.toString(ranged);
                    w.setArrValues(v);
                }
                attackSwitched = true;
            }
        }
    }
    /**
     * Method for taking damage
     * @param atkDmg Damage dealt to player
     */
    public void takeDamage(double atkDmg){
        if(this.health - (atkDmg-armour)>0){
            if(armour<atkDmg){
                GameWorld w = (GameWorld)getWorld();
                String[] v = w.getArrValues(); 
                this.health -= (atkDmg-armour); 
                v[8] = Double.toString(this.health); 
                w.setArrValues(v); 
            }
        }
        else{
            GameWorld gw = (GameWorld) getWorld();
            gw.stopped(); 
            Greenfoot.setWorld(new EndScreen(gw.stopTimer(), false)); 
        }
        updateHealthBar();
    }
    /**
     * Method for updating HealthBar
     */
    public void updateHealthBar()
    {
        GameWorld world = (GameWorld) getWorld();
        healthBar = new SuperStatBar((int)maxHealth, (int)health, null, 200, 20, 0);
        world.removeObject(healthBar);
        world.addObject(healthBar, 300, 30);
    }
    /**
     * Gets the direction that the player is facing 
     * @return int Returns the direction as an int value: 1 = left, 2 = right, 3 = up, 4 = down
     */
    public int getDirection(){
        return direction; 
    }
    /**
     * Sets the melee attack status of the player
     * @param b Boolean that determines if the player is attacking or not 
     */
    public void setAttackStatus(boolean b){
        isAttacking = b; 
    }
    /**
     * Sets the health of the player - useful for updating health after picking up heal cheeses
     * @param health New health of player. 
     */
    public void setHealth(double health){
        this.health = health; 
    }
}
