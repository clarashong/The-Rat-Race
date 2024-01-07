import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Story World is where the storyline slideshow for the game will play.
 * The backgrounds will flip and show the sequences of the game's story.
 *
 * @author Clara Hong
 * @version Jan 23
 */
public class Story extends World
{
    private GreenfootImage[] slides = new GreenfootImage[7];
    private int actCounter = 0;
    private int currentSlide = 0;
    private GreenfootSound story;
    private Button b;
    public Story()
    {    

        super(1300, 700, 1);
        initGraphics();  
        story = new GreenfootSound ("story.mp3");
        story.setVolume(30);
        started();
       
        GreenfootImage normal = new GreenfootImage("skipButton.png");
        GreenfootImage hover = new GreenfootImage("skipButtonHover.png");
        normal.scale(normal.getWidth()/6, normal.getHeight()/6);
        hover.scale(hover.getWidth()/6, hover.getHeight()/6);
       
        b = new Button(normal, hover);

        addObject(b,getWidth()/4,getHeight()/4 + 100);
        b.setLocation(1200,100);
    }
   
    public void started(){
        story.playLoop();
    }
   
    public void stopped(){
        story.stop();
    }
   
    //every 200 acts, the image will change
    public void act() {
        if (actCounter % 200 == 0 ){
            if (currentSlide == slides.length) {
                Greenfoot.setWorld(new Instructions());
                stopped();
            } else {
                setBackground(slides[currentSlide]);
                currentSlide++;
            }
        }
        if (b.getClick()){
            actCounter = 0;
            if (currentSlide == slides.length) {
                Greenfoot.setWorld(new Instructions());
                stopped();
            } else {
                setBackground(slides[currentSlide]);
                currentSlide++;
            }
        }
        actCounter++;
        /**
        if(Greenfoot.isKeyDown("SPACE")) // left
            {
                Greenfoot.setWorld(new Instructions());
                stopped();
            }
        **/
    }
   
    //fill an array with the pictures used as the slides
    public void initGraphics() {
        for (int i = 0; i < slides.length; i++) {
            slides[i] = new GreenfootImage("slide" + i + ".png");
        }
    }
}
