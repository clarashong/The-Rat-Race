import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Gateways between different rooms. A player cannot enter through a door until all the enemies in the room have been killed
 * 
 * @author Joey Guan
 * @version January 22, 2023
 */
public class Door extends Structures
{
    private boolean isOpen = true;
    /**
     * Simple Constructor for Door
     */
    public Door()
    {
        
    }

    /**
     * Simple Act Method for Door
     */
    public void act()
    {
        int sideLength = GameWorld.getBlockSize();
        
        GameWorld w = (GameWorld) getWorld();
        //Quirk of world gen. It's easier to just remove Walls, than to prevent them from spawning
        w.removeObject(getOneIntersectingObject(Wall.class));        
        
        //Sets image based on orientation
        if(getX() == 650 && getY() == 50)//up
        {
            GreenfootImage imageTop = new GreenfootImage("BWtopDoor.png");
            imageTop.scale(sideLength, sideLength);
            setImage(imageTop);
        }
        if(getX() == 650 && getY() == 650)//down
        {
            GreenfootImage imageBottom = new GreenfootImage("BWbottomDoor.png");
            imageBottom.scale(sideLength, sideLength);
            setImage(imageBottom);
        }
        if(getX() == 1250 && getY() == 350)//right
        {
            GreenfootImage imageRight = new GreenfootImage("BWrightDoor.png");
            imageRight.scale(sideLength, sideLength);
            setImage(imageRight);
        }
        if(getX() == 50 && getY() == 350)//left
        {
            GreenfootImage imageLeft = new GreenfootImage("BWleftDoor.png");
            imageLeft.scale(sideLength, sideLength);
            setImage(imageLeft);
        }

        //Chooses which way to move rooms in based on location of door
        if(isOpen && !getIntersectingObjects(Player.class).isEmpty())
        {
            if(getX() == 650 && getY() == 50)//up
            {
                w.moveRooms(0);
                w.setPlayerX(6);
                w.setPlayerY(5);
            }
            if(getX() == 650 && getY() == 650)//down
            {
                w.moveRooms(2);
                w.setPlayerX(6);
                w.setPlayerY(1);
            }
            if(getX() == 1250 && getY() == 350)//right
            {
                w.moveRooms(1);
                w.setPlayerX(1);
                w.setPlayerY(3);
            }
            if(getX() == 50 && getY() == 350)//left
            {
                w.moveRooms(3);
                w.setPlayerX(11);
                w.setPlayerY(3);
            }
            w.setDoneSpawning(false);
        }
    }

    /**
     * Checks if the door is open 
     * @return boolean Returns if the door is open or not 
     */
    public boolean getIsOpen()
    {
        return isOpen;
    }
    
    /**
     * Sets whether the door is open or not 
     * @param b Boolean that says whether the door is open or not
     */
    public void setIsOpen(boolean b)
    {
        isOpen = b;
    }
}
