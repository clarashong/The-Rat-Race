import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Cheeses are essentially powerups that the player can find. Instead of making a subclass for each different 
 * cheese powerup, we made it all in one class and the type as an integer parameter. 
 * <p>
 * There is armour cheese, which decreases the amount of damage a player takes, attack cheese, which increases damage, 
 * heal cheese, which heals health, and speed cheese, which increases speed. 
 * 
 * @author Clara Hong, Harishan Ganeshanathan 
 * @version January 20
 */
public class Cheese extends Actor
{
    private int cheeseType;
    /**
     * Constructor - Sets the type of cheese and its corresponding image
     * 
     * @param type The type of cheese passed in as an integer
     */
    public Cheese(int type)
    {
        cheeseType = type;
        if(type == 0) // armour
        {
            setImage("SilverCheese.png");
        }
        else if(type == 1) //attack
        {
            setImage("RedCheese.png"); 
        }
        else if(type == 2) //heal
        {
            setImage("GreenCheese.png");
        }
        else if(type == 3) //speed
        {
            setImage("PurpleCheese.png");
        }
        else if(type == 4)
        {
            setImage("RainbowCheese.png");
        }
        getImage().scale(50,50);
    }

    /**
     * Act Method - responsible for the actions of each type of cheese
     */
    public void act()
    {
        if(cheeseType == 0)
        {
            if(this.isTouching(Player.class)){
                GameWorld w = (GameWorld)getWorld(); 
                String[] v = w.getArrValues(); 
                v[7] = Double.toString(Double.parseDouble(v[7])+0.5);
                w.setArrValues(v); 

                w.markCheeseMap();
                Color color = new Color(220, 220, 220);
                w.addObject(new PopUp("+Armor", color), getX(), getY());
                getWorld().removeObject(this); 
            }
        }
        else if(cheeseType == 1)
        {
            if(this.isTouching(Player.class)){
                GameWorld w = (GameWorld)getWorld(); 
                String[] v = w.getArrValues(); 
                v[6] = Double.toString(Double.parseDouble(v[6])+2); 
                v[4] = Double.toString(Double.parseDouble(v[4])+2); 
                w.setArrValues(v); 

                w.markCheeseMap();

                Color color = new Color(255, 0, 0);
                w.addObject(new PopUp("+Attack", color), getX(), getY());
                getWorld().removeObject(this); 
            }
        }
        else if(cheeseType == 2)
        {
            if(this.isTouching(Player.class)){
                GameWorld w = (GameWorld)getWorld(); 
                String[] v = w.getArrValues(); 
                int maxHp = Integer.parseInt(v[10]); 
                if((Double.parseDouble(v[8])+10)<=maxHp){
                    double x = Double.parseDouble(v[8])+10; 
                    v[8] = Double.toString(x); 
                    w.setArrValues(v); 
                    Player player = w.getObjects(Player.class).get(0);
                    player.setHealth(x); 
                    player.updateHealthBar();
                }
                else
                {
                    v[8] = Double.toString(maxHp); 
                    w.setArrValues(v); 
                    Player player = w.getObjects(Player.class).get(0);
                    player.setHealth(maxHp); 
                    player.updateHealthBar();
                }
                w.markCheeseMap();

                Color color = new Color(0, 255, 0);
                w.addObject(new PopUp("+Health", color), getX(), getY());
                getWorld().removeObject(this); 
            }
        }
        else if(cheeseType == 3)
        {
            if(this.isTouching(Player.class)){
                GameWorld w = (GameWorld)getWorld(); 
                String[] v = w.getArrValues();
                if((Double.parseDouble(v[5])+0.5)<=8){
                    v[5] = Double.toString(Double.parseDouble(v[5])+0.5); 
                    w.setArrValues(v);
                }else if((Double.parseDouble(v[5])+0.5 == 8)){
                    v[5] = Double.toString(8);
                    w.setArrValues(v); 
                }

                w.markCheeseMap();

                Color color = new Color(120, 0, 200);
                w.addObject(new PopUp("+Speed", color), getX(), getY());
                getWorld().removeObject(this); 
            }
        }
        else if(cheeseType == 4)
        {
            GameWorld w = (GameWorld)getWorld();
            if(!getIntersectingObjects(Player.class).isEmpty()){
                w.setGoingToNextFloor(true);
                w.setDoneSpawning(false); 
            }
        }
    }
}
