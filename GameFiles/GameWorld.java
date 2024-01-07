import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math.*;
import java.util.List;

/**
 * The Rat Race: The Chosen Cheese
 * is a rogue-like, dungeon-crawling game inspired by Binding of Isaac.
 * Playing as the rodential protagonist, skilled in the arts of swords 
 * and throwing daggers, you must fight through 5 floors of enemies in a
 * procedurally generated hotel in order to claim the legendary Chosen Cheese.
 * 
 * NOTE: To see the whole game easily, change max floor depth to a low number, eg. 0 or 1
 * 
 * At the end of your game, you will be scored based on the total amount of 
 * enemies you killed, and the amount of time it took for you to finish.
 * 
 * Controls:
 * Movement: WASD
 * Attack: SPACE
 * Weapon Switch: Q
 * Dash: N
 * Map: M
 * 
 * The controls are for your left hand to use WASD and Q, 
 * your right hand for M and N, and one of your thumbs for SPACE.
 * 
 * Key Features:
 * - Player Movement + Dashing Power
 * - Ranged + Melee Weapons (Sword and throwable dagger)
 * - Cheese Powerups (Instant Health, Speed Upgrade, Attack Upgrade, Armour)
 * - Ranged + Melee Enemies
 * - Obstacles
 * - UI (Attack Type Indicator in the top left which changes based on attack type, health bar, dash cooldown indicator)
 * - Story
 * - Procedural Generation
 * 
 * Image Credits: 
 * All characters (Player, Cat, Bird, Snake) and their appearances (walking, attacking, damaged) were drawn by Clara Hong. 
 * The Start Screen, End Screen, Story Slides, and all obstacles (vase, tableVase, bed, suitcase, cart, etc) were also drawn by Clara Hong.
 * DashIcon - https://commons.wikimedia.org/wiki/File:Running_icon_-_Noun_Project_17825.svg 
 * All Buttons / UI Elements were designed by Anthony Ung
 * ALL Weapons and their RESPECTIVE animation designed and animated by Anthony Ung
 * Title png designed by Anthony Ung
 * Instructions Screen designed By Anthony Ung
 * Game Backgrounds + Doors / Trapdoors designed by Anthony Ung
 * Wing Icon - https://tenor.com/view/angel-wings-pretty-8bit-cute-gif-18012222
 * 
 * 
 * Sound credits: 
 * Main Music (start screen and game world): https://www.youtube.com/watch?v=OWXuChSmpjw&t=304s&ab_channel=ElmostritoOO7 
 * Story Music: https://www.youtube.com/watch?v=FitRzyV2jw8&ab_channel=WaveJam
 * Instruction Music: https://www.youtube.com/watch?v=0gi5-mEVEc4
 * 
 * 
 * Sound Credits: 
 * Slash sound is from pixabay - "Knife slice" - https://pixabay.com/sound-effects/knife-slice-41231/
 * Munching sound is from pixabay by Maugusto sfx : https://pixabay.com/sound-effects/eatingsfxwav-14588/
 * 
 * @author Joey Guan, Harishan Ganeshanathan, Marco Luong, Anthony Ung, Clara Hong
 * @version January 24, 2023
 */
public class GameWorld extends World
{
    //Room grid variables
    private static int BLOCK_SIZE = 100;
    private static int X_OFFSET = 50;
    private static int Y_OFFSET = 50;

    private SimpleTimer timer = new SimpleTimer();

    //Game metadata
    private int floorDepth = 0;
    private int maxFloorDepth = 5;
    private int totalRoomAmount = 5 + (3 * floorDepth); //Room amount of a floor
    private int enemyNumber; //Enemy amount in a room

    //Maps of a floor
    private int[][] dungeonFloor;
    private int[][] dungeonMap = new int[7][7]; // keeps track of rooms that are clear. Cleared means no enemies will spawn again. 1 for not cleared, 2 for cleared
    private int[][] cheeseMap = new int[7][7]; // -1 for no cheese, other numbers means there's that number type of cheese, set to -1 by Cheese object when it gets picked up

    //Booleans to make sure certain events only happen once
    private boolean dungeonGenerated = false;
    private boolean doneSpawning = false;
    private boolean cheeseSpawned = false;
    private boolean trapdoorSpawned = false; 
    private boolean goingToNextFloor = false; 
    private boolean mapAdded = false;

    //The room player is currently in (starting location is dungeonFloor[3][3])
    private int currentRoomX = 3;
    private int currentRoomY = 3;

    //The cell numbers that player spawns at
    private int playerX = 6;
    private int playerY = 3;

    //Player stats; kept in world because the player would otherwise be reset each time you move rooms/floors
    private String[] values = {"false", "100", "30", "30", "5.5", "4", "8", "0", "50", "0", "50"};

    private GreenfootSound Theme; 
    /**
     * Constructor for objects of class GameWorld.
     */
    public GameWorld()
    {    
        super(1300, 700, 1); 
        GreenfootImage background = new GreenfootImage("backgroundnoDoor.png");
        timer.mark();
        background.scale(1300,700);
        setBackground(background);
        Theme = new GreenfootSound ("Theme.mp3");
        Theme.setVolume(20);
        started();
        setPaintOrder(UI.class, Player.class, Cheese.class, MeleeAttack.class, RangedProjectile.class, Structures.class);
    }

    public void started(){
        Theme.playLoop();
    }

    public void stopped(){
        Theme.stop();
    }

    public void act()
    {
        if(!dungeonGenerated) generateDungeonFloor();
        //goingToNextFloor set to true by Player touching a trapdoor
        if(goingToNextFloor)
        {
            if(floorDepth != maxFloorDepth) //If not on the final floor, go down a floor and generate it
            {
                floorDepth++;
                totalRoomAmount = 5 + (2 * floorDepth);

                if(floorDepth == 1)
                {
                    GreenfootImage background = new GreenfootImage("BLUEbackgroundnoDoor.png");
                    background.scale(1300,700);
                    setBackground(background);
                }
                else if(floorDepth == 2)
                {
                    GreenfootImage background = new GreenfootImage("GREENbackgroundnoDoor.png");
                    background.scale(1300,700);
                    setBackground(background);
                }
                else if(floorDepth == 3)
                {
                    GreenfootImage background = new GreenfootImage("GREYbackgroundnoDoor.png");
                    background.scale(1300,700);
                    setBackground(background);
                }
                else if(floorDepth == 4)
                {
                    GreenfootImage background = new GreenfootImage("MAGENTAbackgroundnoDoor.png");
                    background.scale(1300,700);
                    setBackground(background);
                }
                else if(floorDepth == 5)
                {
                    GreenfootImage background = new GreenfootImage("PURPLEbackgroundnoDoor.png");
                    background.scale(1300,700);
                    setBackground(background);
                }
                
                generateDungeonFloor();
                currentRoomX = 3;
                currentRoomY = 3;
                playerX = 6;
                playerY = 3;
            }
            else //If on the final floor, win the game
            {
                stopped(); 
                Greenfoot.setWorld(new EndScreen(stopTimer(), true));
            }
            goingToNextFloor = false;
        }
        if(dungeonGenerated && !doneSpawning) //If the floor has been generated, and room hasn't been spawned, then spawn room
        {
            spawnRoom(); //Clears the world of previous room's Actors, adds in all Actors of current room
        }
        roomStatusCheck(); //Adds cheese + trapdoor when there are no enemies in room, also locks/unlocks doors
        displayMap(); //Press M for map method
    }

    /**
     * Does all the room generation tasks that need to
     * check if there are still enemies in the room.
     */
    public void roomStatusCheck()
    {
        List<Door> doors = getObjects(Door.class);
        if(getObjects(Enemies.class).isEmpty()) 
        {
            dungeonMap[currentRoomY][currentRoomX] = 2; // set room to cleared
            for(Door d : doors) //opens when there are none
            {
                d.setIsOpen(true);
            }
            if(floorDepth == maxFloorDepth && dungeonFloor[currentRoomY][currentRoomX] == -2 && !trapdoorSpawned) // Spawns chosen cheese
            {
                addObject(new Cheese(4), getXCoordinate(6), getYCoordinate(3));
                trapdoorSpawned = true;
            }
            if(dungeonFloor[currentRoomY][currentRoomX] == -2 && !trapdoorSpawned) //spawn trapdoor at boss room if all enemies are dead
            {
                addObject(new Trapdoor(), getXCoordinate(6), getYCoordinate(3));
                trapdoorSpawned = true;
            }
            if(cheeseMap[currentRoomY][currentRoomX] >= 0 && !cheeseSpawned) //spawn a cheese of indicated type by the cheeseMap, -1 means no cheese
            {
                addObject(new Cheese(cheeseMap[currentRoomY][currentRoomX]), getXCoordinate(6), getYCoordinate(2));
                cheeseSpawned = true;
            }
        }
        else
        {
            for(Door d : doors) // locks doors if there are enemies
            {
                d.setIsOpen(false);
            }
        }
    }

    /**
     * Generates a floor of hotel consisting of a 7x7 grid
     * of integers, which represent different types of rooms.
     * -1 is the starting room; -2 is the room containing the trapdoor; 
     * 0 means there's no room present in the grid; all positive numbers are room layouts
     */
    public void generateDungeonFloor()
    {
        dungeonFloor = new int[][]{
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,-1,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0} 
        };
        int x = 3;
        int y = 3;
        int roomAmount = 1;
        while(roomAmount < totalRoomAmount) // generates floor in a randomly moving, snake-like way
        { 
            int direction = Greenfoot.getRandomNumber(4); //randomly picks a direction to move in
            switch (direction){
                case 0: //up
                    if(y != 0) y -= 1;
                    break;
                case 1: //right
                    if(x != 6) x += 1;
                    break;
                case 2: //down
                    if(y != 6) y += 1;
                    break;
                case 3: //left
                    if(x != 0) x -= 1;
                    break;
            }
            if(dungeonFloor[y][x] == 0) //If current spot is empty, add a room there
            {
                dungeonFloor[y][x] = 1;
                roomAmount++;
            }
        }

        //Room characteristics setting
        //Look for room farthest away to set as boss room to progress to next floor
        int farthestX = 3;
        int farthestY = 3;
        int farthestTotalDistance = Math.abs(3 - farthestX) + Math.abs(3 - farthestY);
        for(int i = 0; i < dungeonFloor.length; i++)
        {
            for(int j = 0; j < dungeonFloor[0].length; j++)
            {
                dungeonMap[i][j] = dungeonFloor[i][j]; // copies over the floor to a map that keeps track of cleared rooms
                cheeseMap[i][j] = Greenfoot.getRandomNumber(4); // generates the room's cheese type that will spawn
                if(dungeonFloor[i][j] > 0)
                {
                    dungeonFloor[i][j] = 1 + Greenfoot.getRandomNumber(7); // sets room layout type (where wall obstacles are placed in the room)
                    int totalDistance = Math.abs(3 - j) + Math.abs(3 - i);
                    if(totalDistance > farthestTotalDistance)
                    {
                        farthestTotalDistance = totalDistance;
                        farthestX = j;
                        farthestY = i;
                    }
                }
            }
        }
        dungeonFloor[farthestY][farthestX] = -2;

        dungeonGenerated = true;
    }

    /**
     * Simulates moving between rooms by clearing the screen, then 
     * adding all the Actors present in the new room into the World
     */
    public void spawnRoom()
    {
        //Reset variables
        trapdoorSpawned = false;
        cheeseSpawned = false;
        enemyNumber = 2 + Greenfoot.getRandomNumber(floorDepth+1);
        //Clear Screen
        removeObjects(getObjects(Actor.class));
        //Adding UI elements
        addObject(new AttackTypeIndicator(), 100, 100);
        //Adds room layout
        int roomType = dungeonFloor[currentRoomY][currentRoomX];
        switch (roomType)
        {
            case 1: 
                room1();
                break;
            case 2:
                room2();
                break;
            case 3:
                room3();
                break;
            case 4:
                room4();
                break;
            case 5:
                room5();
                break;
            case 6:
                room6();
                break;
            case 7:
                room7();
                break;
        }
        //Adding in Player
        addObject(new Player(values), getXCoordinate(playerX), getYCoordinate(playerY));
        //Adding in walls
        for(int i = 0; i <= 6; i++) //Left Wall
        {
            addObject(new Wall("wall"), getXCoordinate(0), getYCoordinate(i));
        }
        for(int i = 0; i <= 6; i++) //Right Wall
        {
            addObject(new Wall("wall"), getXCoordinate(12), getYCoordinate(i));
        }
        for(int i = 1; i <= 11; i++) //Top Wall
        {
            addObject(new Wall("wall"), getXCoordinate(i), getYCoordinate(0));
        }
        for(int i = 1; i <= 11; i++) //Bottom Wall
        {
            addObject(new Wall("wall"), getXCoordinate(i), getYCoordinate(6));
        }
        //Adding in doors, if there is a room neighboring this one in that direction
        if(currentRoomY > 0 && dungeonFloor[currentRoomY-1][currentRoomX] != 0) //Up
        {
            Door doorUp = new Door();
            addObject(doorUp, getXCoordinate(6), getYCoordinate(0));
        }
        if(currentRoomY < 6 && dungeonFloor[currentRoomY+1][currentRoomX] != 0) //Down
        {
            Door doorDown = new Door();
            addObject(doorDown, getXCoordinate(6), getYCoordinate(6));
        }
        if(currentRoomX < 6 && dungeonFloor[currentRoomY][currentRoomX+1] != 0) //Right
        {
            Door doorRight = new Door();
            addObject(doorRight, getXCoordinate(12), getYCoordinate(3));
        }
        if(currentRoomX > 0 && dungeonFloor[currentRoomY][currentRoomX-1] != 0) //Left
        {
            Door doorLeft = new Door();
            addObject(doorLeft, getXCoordinate(0), getYCoordinate(3));
        }
        //Spawn enemies randomly if room hasn't been cleared before
        if(dungeonMap[currentRoomY][currentRoomX] == 1)
        {
            spawnEnemies();
        }
        doneSpawning = true;
    }

    /**
     * Spawns enemies into the world 
     */
    public void spawnEnemies()
    {
        for(int i = 0; i < enemyNumber; i++) 
        {
            boolean coordinateGenerated = false;
            String enemyType;
            int x = 0;
            int y = 0;
            while(!coordinateGenerated) //Generates coordinates until it finds a valid empty grid spot
            {
                x = 1 + Greenfoot.getRandomNumber(11);
                y = 1 + Greenfoot.getRandomNumber(5);
                if(getObjectsAt(getXCoordinate(x), getYCoordinate(y), Actor.class).isEmpty())
                {
                    coordinateGenerated = true;
                }
            }
            int random = Greenfoot.getRandomNumber(3); //Randomly choose 1 of 3 enemy types
            if(random == 1)
            {
                enemyType = "melee";
            }
            else if(random == 2)
            {
                enemyType = "snake";
            }
            else
            {
                enemyType = "ranged";
            }
            if(enemyType.equals("melee"))
            {
                int hp = 15 + floorDepth*3;
                int attack = 2 + Greenfoot.getRandomNumber(floorDepth+1);
                int speed = 3 + Greenfoot.getRandomNumber(floorDepth+1);
                addObject(new MeleeEnemy(hp,speed,attack), getXCoordinate(x), getYCoordinate(y));
            }
            else if(enemyType.equals("ranged"))
            {
                int hp = 8 + floorDepth*3;
                double attack = 2 + Greenfoot.getRandomNumber(floorDepth+1);
                int speed = 4;
                addObject(new RangedEnemy(hp,speed,attack), getXCoordinate(x), getYCoordinate(y));
            }else if(enemyType.equals("snake"))
            {
                int hp = 8 + Greenfoot.getRandomNumber(floorDepth+1);
                double attack = 2 + Greenfoot.getRandomNumber(floorDepth+1);
                int speed = 4;
                addObject(new Snake(hp,speed,attack), getXCoordinate(x), getYCoordinate(y));
            }
        }
    }

    /**
     * Displays a map of the floor when "M" is pressed down
     */
    public void displayMap()
    {
        removeObjects(getObjects(Map.class));
        mapAdded = false;
        if(Greenfoot.isKeyDown("M") && mapAdded == false)
        {
            addObject(new Map(dungeonFloor), 650, 350);
            mapAdded = true;
        }
    }

    /**
     * Facilitates movement between rooms
     */
    public void moveRooms(int direction)
    {
        switch (direction){
            case 0: //up
                currentRoomY--;
                break;
            case 1: //right
                currentRoomX++;
                break;
            case 2: //down
                currentRoomY++;
                break;
            case 3: //left
                currentRoomX--;
                break;
        }
    }

    /**
     * Set doneSpawning variable
     * @param b Boolean for doneSpawning variables to be set to 
     */
    public void setDoneSpawning(boolean b)
    {
        doneSpawning = b;
    }

    /**
     * Set goingToNextFloor variable 
     * @param b Boolean to set goingToNextFloor variable to
     */
    public void setGoingToNextFloor(boolean b)
    {
        goingToNextFloor = b;
    }

    /**
     * Set player x coordinate
     * @param x New x coordinate for player 
     */
    public void setPlayerX(int x)
    {
        playerX = x;
    }

    /**
     * Set player y coordinate 
     * @param y New y coordinate for player 
     */
    public void setPlayerY(int y)
    {
        playerY = y;
    }

    /**
     * Marks if the cheese in the room is taken on the map 
     */
    public void markCheeseMap() // marks current room's cheese as taken
    {
        cheeseMap[currentRoomY][currentRoomX] = -1;
    }

    public static int getXCoordinate (int cellNumber){
        return (cellNumber * BLOCK_SIZE) + X_OFFSET;
    }

    public static int getXCell(int coordinate){
        return (coordinate - X_OFFSET) % BLOCK_SIZE;
    }

    public static int getYCoordinate (int cellNumber){
        return (cellNumber * BLOCK_SIZE) + Y_OFFSET;
    }

    public static int getYCell(int coordinate){
        return (coordinate - Y_OFFSET) % BLOCK_SIZE;
    }

    /**
     * Get the BLOCK_SIZE variable
     * return int Return the BLOCK_SIZE variable 
     */
    public static int getBlockSize()
    {
        return BLOCK_SIZE;
    }

    /**
     * Get the currentRoomY variable
     * return int Return the currentRoomY variable 
     */
    public int getCurrentRoomY() {
        return currentRoomY; 
    }

    /**
     * Get the currentRoomX variable
     * return int Return the currentRoomX variable 
     */
    public int getCurrentRoomX() {
        return currentRoomX; 
    }

    /**
     * Get the array holding the player values 
     * @return String[] Returns the string array holding player values
     */
    public String[] getArrValues(){
        return values;
    }

    /**
     * Set the array holding the player values 
     * @param v String array with the updated player values 
     */
    public void setArrValues(String[] v){
        for(int i = 0; i<v.length;i++){
            values[i] = v[i]; 
        }
    }

    /**
     * Methods for adding room layouts into the world
     */
    public void room1()
    {
        addObject(new Wall("statue"), getXCoordinate(1), getYCoordinate(1));
        addObject(new Wall("statue"), getXCoordinate(1), getYCoordinate(5));
        addObject(new Wall("statue"), getXCoordinate(11), getYCoordinate(1));
        addObject(new Wall("statue"), getXCoordinate(11), getYCoordinate(5));
        addObject(new Wall("fountain"), getXCoordinate(6), getYCoordinate(3));
    }

    public void room2()
    {

        addObject(new Wall("cart"), getXCoordinate(3), getYCoordinate(1));
        addObject(new Wall("cart"), getXCoordinate(8), getYCoordinate(5));
        addObject(new Wall("cart"), getXCoordinate(2), getYCoordinate(5));
        addObject(new Wall("suitcase"), getXCoordinate(5), getYCoordinate(2));
        addObject(new Wall("suitcase"), getXCoordinate(7), getYCoordinate(5));
        addObject(new Wall("suitcase"), getXCoordinate(3), getYCoordinate(4));
    }

    public void room3()
    {
        addObject(new Wall("smallTable"), getXCoordinate(1), getYCoordinate(1));
        addObject(new Wall("cart"), getXCoordinate(5), getYCoordinate(2));
        addObject(new Wall("chair"), getXCoordinate(6), getYCoordinate(3));
        addObject(new Wall("couch"), getXCoordinate(8), getYCoordinate(3));
        addObject(new Wall("fountain"), getXCoordinate(11), getYCoordinate(1));
        addObject(new Wall("vaseTable"), getXCoordinate(11), getYCoordinate(5));
    }

    public void room4()
    {
        addObject(new Wall("fountain"), getXCoordinate(6), getYCoordinate(3));
        addObject(new Wall("longTable"), getXCoordinate(2), getYCoordinate(1));
        addObject(new Wall("statue"), getXCoordinate(8), getYCoordinate(1));
        addObject(new Wall("statue"), getXCoordinate(10), getYCoordinate(4));
        addObject(new Wall("vaseTable"), getXCoordinate(9), getYCoordinate(2));
        addObject(new Wall("chair"), getXCoordinate(2), getYCoordinate(4));
    }

    public void room5()
    {
        addObject(new Wall("vaseTable"), getXCoordinate(1), getYCoordinate(1));
        addObject(new Wall("longTable"), getXCoordinate(6), getYCoordinate(3));
        addObject(new Wall("vase"), getXCoordinate(11), getYCoordinate(5));
        addObject(new Wall("cart"), getXCoordinate(10), getYCoordinate(2));
        addObject(new Wall("statue"), getXCoordinate(3), getYCoordinate(1));
    }

    public void room6()
    {
        addObject(new Wall("bedSide"), getXCoordinate(3), getYCoordinate(1));
        addObject(new Wall("nightTable"), getXCoordinate(1), getYCoordinate(1));
        addObject(new Wall("wardrobe"), getXCoordinate(10), getYCoordinate(4));
        addObject(new Wall("couch"), getXCoordinate(7), getYCoordinate(3));
        addObject(new Wall("chair"), getXCoordinate(1), getYCoordinate(5));
        addObject(new Wall("vase"), getXCoordinate(9), getYCoordinate(1));
    }

    public void room7()
    {
        addObject(new Wall("bedFront"), getXCoordinate(3), getYCoordinate(4));
        addObject(new Wall("nightTable"), getXCoordinate(1), getYCoordinate(1));
        addObject(new Wall("wardrobe"), getXCoordinate(10), getYCoordinate(4));
        addObject(new Wall("chair"), getXCoordinate(9), getYCoordinate(1));
    }

    public int stopTimer(){
        int sec = timer.millisElapsed() / 1000;
        return sec;
    }
}
