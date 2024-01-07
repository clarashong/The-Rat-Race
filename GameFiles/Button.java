import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A clickable Button (made in/borrowed from previous group simulation project)
 * 
 * @author Anthony Ung,  Gary Niu, Arsham Zare Moayedi
 * @version December 1
 */
public class Button extends Actor
{
    protected GreenfootImage background, nameImage, greyBackground; 
    private GreenfootSound Click;
    private boolean click;
    //protected width, height;
    private int timer = 0;
    /**
     * Constructor for Button
     * <p>
     * Creates a new Button object with a normal state and hover state
     * 
     * @param a The normal image of the button
     * @param b The hover image of the button
     */
    public Button(GreenfootImage a, GreenfootImage b)
    {
        Click = new GreenfootSound ("Click.mp3");
        /*
        background = new GreenfootImage(width, height); 
        background.setColor(Color.BLACK); 
        background.fill(); 

        nameImage = new GreenfootImage(name, 50, Color.WHITE, Color.BLACK);
        background.drawImage(nameImage, background.getWidth()/2-nameImage.getWidth()/2,background.getHeight()/2-nameImage.getHeight()/2);

        greyBackground = new GreenfootImage(width, height); 
        greyBackground.setColor(Color.GRAY); 
        greyBackground.fill(); 
        greyBackground.drawImage(nameImage, background.getWidth()/2-nameImage.getWidth()/2,background.getHeight()/2-nameImage.getHeight()/2);*/
        
        background = a;
        greyBackground = b;

        setImage(background); 
    }

    public void act() 
    {
        // Add your action code here.
        timer++;
        click = listenForClick();
    }    
    /**
     * Returns if the button is clicked
     * 
     * @return Button clicked
     */
    public boolean listenForClick()
    {
        if(Greenfoot.mousePressed(this))
        {
            
            
            
            greyBackground.scale(greyBackground.getWidth()+5, greyBackground.getHeight()+5);
            Greenfoot.delay(10);
            greyBackground.scale(greyBackground.getWidth()-5, greyBackground.getHeight()-5);
            Click.play();
            
            return true;
        } 
        if (Greenfoot.mouseMoved(this)){
            setImage(greyBackground);
        }
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)){
            setImage(background);
        }
        
        return false;

    }
    /**
     * Returns Click
     * @return boolean Click
     */
    public boolean getClick(){
        return click;
    }

}