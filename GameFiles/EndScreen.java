import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * End Screen that the player gets switched to after they die 
 * 
 * @author Harishan Ganeshanathan, Marco Luong
 * @version January 12
 */
public class EndScreen extends World
{
    private int score;
    private UserInfo player;
    private Button b;
    private ScoreBoard lb;
    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen(int time, boolean win)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1300, 700, 1);
        
        if(win)
        {
            setBackground(new GreenfootImage("WinScreen.png"));
        }
        else
        {
            setBackground(new GreenfootImage("LoseScreen.png")); 
        }
        
        GreenfootImage background = new GreenfootImage("playAgainButton.png");
        background.scale(background.getWidth()/8, background.getHeight()/8);
        
        score = time;
        if (UserInfo.isStorageAvailable()) {
            player = UserInfo.getMyInfo();
            if (score > player.getScore()) {
                 player.setScore(score);
                 player.store();  // write back to server
            }
        }
        
        lb = new ScoreBoard(500, 325);
        addObject(lb, getWidth()/2, getHeight()/2 + 150);
        
        b = new Button(background, background);
        addObject(b,getWidth()/2,getHeight()/2 + 90);
        b.setLocation(650,300);
    }
    public void act(){
        if (b.getClick()){
            Greenfoot.setWorld(new StartScreen());
        }
    }
    
    
}
