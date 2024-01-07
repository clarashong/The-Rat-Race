import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A snake enemy that moves around the room randomly. 
 * Damage will be done on contact.
 * 
 * @author Marco Luong
 */
public class Snake extends Enemies
{
    // Critical variables
    private int movementCD, movementTimer, prevDirect;
    
    // Main constructor
    public Snake(int hp, int spd, double atkDmg){
        super(hp, spd, atkDmg, "Snake"); // super
        range = 0; // Will deal damage on contact
        atkCD = 40;
        atkTimer = atkCD;
        movementCD = 150; // Countdown for how often the snake changes direction
        movementTimer = movementCD; // Actual timer
    }
    /**
     * Act Method for Snake
     */
    public void act()
    {
        randomMovement();
        if(beenAttacked){
            if(direction == 1){
                setImage("SnakeLDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 2){
                setImage("SnakeRDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 3){
                setImage("SnakeUDamage.png"); 
                getImage().scale(100,100);
            }else if(direction == 4){
                setImage("SnakeDDamage.png"); 
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
        atkTimer--;
        movementTimer--;
        super.act();
    }
    
    /**
     * Changes the snake's direction of movement. 
     * Ensures that the snake doesn't keep moving L/R & U/D repeatedly
     */
    private void randomMovement(){
        enemyWorldSetup();
        if(movementTimer <= 0){
            prevDirect = direction;
            direction = Greenfoot.getRandomNumber(4) + 1; // Randomizer
            // Ensures that the snake doesnt keep turning in 1 dimension (L/R & U/D)
            if(prevDirect < 3 && direction < 3){
                direction = Greenfoot.getRandomNumber(2) + 3;
            }
            else if(prevDirect > 2 && direction > 2){
                direction = Greenfoot.getRandomNumber(2) + 1;
            }
            movementTimer = movementCD;
        }
        
        // To check if the snake is approaching the wall, resets timer if so
        try{
            if(direction == 1){ // left
                if(roomLayout[enemyY][enemyX - 1] == 1){
                    movementTimer = 0;
                }
                setRotation(179);
            }
            else if(direction == 2){ // right
                if(roomLayout[enemyY][enemyX + 1] == 1){
                    movementTimer = 0;
                }
                setRotation(0);
            }
            else if(direction == 3){ // up
                if(roomLayout[enemyY - 1][enemyX] == 1){
                    movementTimer = 0;
                }
                setRotation(269);
            }
            else { // down
                if(roomLayout[enemyY + 1][enemyX] == 1){
                    movementTimer = 0;
                }
                setRotation(89);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            movementTimer = 0;
        }
    }
    
    public void attack(){ // If touching player, deal damage
        if(isTouching(Player.class) && atkTimer<=0){
            Player p = (Player) getOneIntersectingObject(Player.class);
            p.takeDamage(atkDmg);
            atkTimer = atkCD; 
        }
    }
}
