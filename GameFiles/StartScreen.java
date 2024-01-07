import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Starting Screen for the Game
 * 
 * @author Anthony Ung, Clara Hong 
 * @version January 18
 */
public class StartScreen extends World
{
    private Button b;
    private GreenfootSound Theme; 
    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 700, 1); 
        setBackground(new GreenfootImage("StartScreen.png"));
        Theme = new GreenfootSound ("Theme.mp3");
        Theme.setVolume(30);
        
        GreenfootImage background = new GreenfootImage("startButton.png");
        background.scale(background.getWidth()/8, background.getHeight()/8);
        
        b = new Button(background, background);
        addObject(b,getWidth()/2,getHeight()/2 + 100);
        b.setLocation(840,450);
    }
    public void started(){
        Theme.playLoop();
    }
   
    public void stopped(){
        Theme.stop();
    }
    public void act(){
        if (b.getClick()){
            Theme.stop(); 
            Greenfoot.setWorld(new Story());
        }
    }
    
    
}
