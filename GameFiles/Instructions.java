import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Screen that displays the controls for the player
 * 
 * @author (Anthony Ung) 
 * @version (January 19)
 */
public class Instructions extends World
{
    private Button b;
    GreenfootSound Theme;
    /**
     * Constructor for objects of class Instructions.
     * 
     */
    public Instructions()
    {    
        super(1300, 700, 1); 
        GreenfootImage bg = new GreenfootImage("InstructionScreen.png");
        bg.scale(1300,700);
        
        setBackground(bg);
        
        
        Theme = new GreenfootSound ("instructions.mp3");
        Theme.setVolume(30);
        started();

        
        GreenfootImage normal = new GreenfootImage("nextButton.png");
        GreenfootImage hover = new GreenfootImage("nextButtonHover.png");
        normal.scale(normal.getWidth()/4, normal.getHeight()/4);
        hover.scale(hover.getWidth()/4, hover.getHeight()/4);
        
        b = new Button(normal, hover);
        addObject(b,getWidth()/2,getHeight()/2 + 100);
        b.setLocation(1125,625); 
    }
    
    public void started(){
        Theme.playLoop();
    }
   
    public void stopped(){
        Theme.stop();
    }
    
    public void act(){
        if (b.getClick()){
            Greenfoot.setWorld(new GameWorld());
            stopped();
        }
    }
}
