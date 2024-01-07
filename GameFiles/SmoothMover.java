import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.Queue; 
import java.util.LinkedList; 
/**
 * A variation of an actor that maintains a precise location (using doubles for the co-ordinates
 * instead of ints).  This allows small precise movements (e.g. movements of 1 pixel or less)
 * that do not lose precision.
 * 
 * There are also animation methods added for walk cycles and graphics. 
 * This allows for cool movement for the characters each with their own graphics 
 * that cycle through as they move or attack. 
 * 
 * @author Poul Henriksen
 * @author Michael Kolling
 * @author Neil Brown
 * @author Clara Hong
 * 
 * @version 3.0
 */
public abstract class SmoothMover extends Actor
{
    private double exactX;
    private double exactY;
    
    private int direction; //direction that its facing
    private int currentDirection; //tracking of its current direction
    private GreenfootImage[][] frames; //2D array of all its frames while walking
    private Queue<GreenfootImage> animation = new LinkedList<GreenfootImage> (); //queue of the frames that are to be played
    private String actorType; //ie. "Player", "Cat", "Snake" 
    private int framesPerDirection = 0; //how many frames that are available for any given direction
    private GreenfootImage[] attackFrames; //array of the frames where it's attacking 
    
    //inherited variables - variables that need to be present in subclasses 
    protected int actCounter = 0; 
    protected boolean moving;
    protected boolean attacking; 
    protected boolean hasAttackFrame; 
    
    public SmoothMover(String type) {
        //initialising variables
        actorType = type; 
        hasAttackFrame = false; 
        initGraphics(); 
        direction = 1; 
    }
    
    
    /**
     * Move forward by the specified distance.
     * (Overrides the method in Actor).
     */
    @Override
    public void move(int distance)
    {
        move((double)distance);
    }
    
    /**
     * Move forward by the specified exact distance.
     */
    public void move(double distance)
    {
        double radians = Math.toRadians(getRotation());
        double dx = Math.cos(radians) * distance;
        double dy = Math.sin(radians) * distance;
        setLocation(exactX + dx, exactY + dy);
    }
    
    /**
     * Set the location using exact coordinates.
     */
    public void setLocation(double x, double y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation((int) (x + 0.5), (int) (y + 0.5));
    }
    
    /**
     * Set the location using integer coordinates.
     * (Overrides the method in Actor.)
     */
    @Override
    public void setLocation(int x, int y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation(x, y);
    }

    /**
     * Return the exact x-coordinate (as a double).
     */
    public double getExactX() 
    {
        return exactX;
    }

    /**
     * Return the exact y-coordinate (as a double).
     */
    public double getExactY() 
    {
        return exactY;
    }
    
    //The start of the animation methods 
    /** 
     * The initGraphics method helps set all the images that the actor needs for 
     * its animation frames. More complex characters like the player, cat, and bird 
     * need to have their walk cycles as well as their attack frames initiated. 
     */
    public void initGraphics() {
        String[] directions = {"L", "R", "U", "D"}; //array used to easily find files 
        //only runs if there's a valid actor type 
        if (!actorType.equals("")) {
            //player, cat, and bird have more frames per direction, while the snake just has 1
            if (actorType.equals("Player") || actorType.equals("Cat") || actorType.equals("Bird")) {
                framesPerDirection = 4; 
                hasAttackFrame = true; 
            } else if (actorType.equals("Snake")) {
                framesPerDirection = 1; 
                hasAttackFrame = false; 
            }
            //2d array of frames, frames[# of directions][frames per direction]
            frames = new GreenfootImage[4][framesPerDirection]; 
            //filling in walk cycle
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < frames[i].length; j++) {
                    GreenfootImage image = new GreenfootImage(actorType + directions[i] + "Walk" + j + ".png"); 
                    image.scale(100,100); //scale it down to cell size
                    frames[i][j] = image; 
                }
            }
            //initial pose 
            setImage(frames[1][0]); 
            //filling in attack frames
            if (hasAttackFrame) {
                attackFrames = new GreenfootImage[4]; 
                for (int i = 0; i < 4; i++) {
                    GreenfootImage image = new GreenfootImage(actorType + directions[i] + "Attack.png"); 
                    image.scale(100,100); 
                    attackFrames[i] = image; 
                }
            }
        }
    }
    
    //adds frames to the queue
    public void addFrames() {
        if (moving) {
            for (int i = 0; i < framesPerDirection; i++) {
                animation.add(frames[direction][i]); 
            }
        } 
    }
    
    //players and enemies need to call on this in their respective act methods
    /**
     * The animate method decides when to add frames to the queue AKA the animation 
     * timeline. 
     * Takes the direction that the character is supposed to be facing as a parameter 
     * (0 - left, 1 - right, 2 - up, 3 - down).
     */
    public void animate(int d) {
        currentDirection = d; 
        if (attacking) {
            //if it's attacking, it'll clear the queue and put the attack frames
            animation.clear(); 
            animation.add(attackFrames[direction]); 
        } else if (currentDirection != direction) {
            //if it has changed directions, it'll clear the queue and put 
            //in the walk cycle for the new direction
            direction = currentDirection; 
            animation.clear(); 
            addFrames(); 
        } else if (moving && animation.peek() == null) {
            //if it's moving but there's nothing in the queue, add frames
            addFrames(); 
        }
        //change frames every 5 acts 
        if (actCounter % 5 == 0) {
            //if it's not doing anything
            if (!moving && !attacking) {
                animation.clear(); 
            }
            //if there's nothing in the queue: put a idle frame 
            if (animation.peek() == null) {
                setImage(frames[direction][0]); 
            } else{
                //else set the next image in the queue, then remove it 
                setImage(animation.peek()); 
                animation.remove(); 
            }
        }
    }
}
