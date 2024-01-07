import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *  PopUps will decrease in transparency and move upwards 
 *  and are removed when transparency gets low enough. 
 *  Taken and slightly modified from Animal Restaurant simulation.
 *  
 * @author Joey Guan, Marco Luong
 * @version January 19, 2023
 */
public class PopUp extends UI
{
    private int num = 255, numChange;
    private boolean slow;
    
    /**
     * Constructor for String PopUps
     * 
     * @param str Text to draw
     * @param color Color of text font
     */
    public PopUp(String str, Color color)
    {
        slow = false;
        numChange = 5;
        Font font = new Font(false, false, 20);
        GreenfootImage image = new GreenfootImage(200,200);
        
        image.setFont(font);
        image.setColor(color);
        image.drawString(str, 100, 100);
        setImage(image);
    }
    
    /**
     * Constructor for custom image PopUps with a specified
     * String of text layered on it.
     * 
     * @param img        A specified GreenfootImage
     * @param scaleW     Width of img
     * @param scaleL     Length of img
     * @param fontSize   Size of text
     * @param c          Color of text
     * @param line       Text that you want to show
     * 
     */
    public PopUp(GreenfootImage img, int scaleW, int scaleL, int fontSize, Color c, String line)
    {
        slow = true;
        numChange = 1;
        Font font = new Font("Calibri", true, false, fontSize);
        
        img.scale(scaleW, scaleL);
        setImage(img);
        img.setFont(font);
        img.setColor(c);
        img.drawString(line, getImage().getWidth() / 2 - (line.length() * fontSize / 4), getImage().getHeight() / 2);
    }
    /**
     * Act Method for PopUp
     */
    public void act()
    {
        num -= numChange;
        setLocation(getX(), getY()-1);
        if(num > 0)
        {
            getImage().setTransparency(num);
        }
        else
        {
            getWorld().removeObject(this);
        }
    }
}