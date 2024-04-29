import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A class to hold all 6 of the MC's intro stories
 * Each story will be a background picture + some supporting text
 * 
 */
public class CharacterIntros extends AllWorld
{
    // Background images and corresponding text for MC Stories
    private GreenfootImage[] characterBackgrounds = new GreenfootImage[6];
    private SuperTextBox[] characterStories = new SuperTextBox[6];

    // Colors
    private Color bgColor = new Color(119, 136, 153, 240);
    private Color borderColor = new Color(192, 192, 192);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color textColor = new Color(250, 249, 246);
    // MCs
    //int MCs[];  // track which MCs were selected by the user
    //private int[] MCs = {1,2,3,6}; //temp

    // Button to control stories
    IntroButton leftButton;
    IntroButton rightButton;

    // Box to indicate when to go to next world (cutscene)
    SuperTextBox indicator;

    // Counter to keep track of current displayed story
    int displayCounter = 0;
    /**
     * Constructor for objects of class CharacterIntros.
     * 
     */
    public CharacterIntros()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 1);

        fillCharacterBackgroundAndStories();

        // Use multi-line textbox
        /*
        SuperTextBox s1 = new SuperTextBox(stuff, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), 600, false);
        addObject(s1, 600, 400);
         */
        // Initalize Font
        SimulationFont.initalizeFont();

        // Indicator
        indicator = new SuperTextBox("Click to move on to character selection", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 700, 5, borderColor);

        // By default first story will show up
        setBackground(characterBackgrounds[0]);
        addObject(characterStories[0], 600, 700);

        // Buttons
        rightButton = new IntroButton(true);
        addObject(rightButton, 1100, 425);
        leftButton = new IntroButton(false);
    }

    public void act(){
        addObject(leftButton, 100, 425);
        addObject(rightButton, 1100, 425);
        if(displayCounter == 0){
            removeObject(leftButton);
        }
        if(displayCounter == 5){
            removeObject(rightButton);
            // This indicator once added to world will be left there
            // So you can switch to the introWorld at anytime after you read all the stories
            addObject(indicator, 600, 100);
        }
        if(Greenfoot.mouseClicked(rightButton)){
            removeObject(characterStories[displayCounter]);
            displayCounter++;
            setBackground(characterBackgrounds[displayCounter]);
            addObject(characterStories[displayCounter], 600, 700);
        }
        if(Greenfoot.mouseClicked(leftButton)){
            removeObject(characterStories[displayCounter]);
            displayCounter--;
            setBackground(characterBackgrounds[displayCounter]);
            addObject(characterStories[displayCounter], 600, 700);
        }
        if(Greenfoot.mouseClicked(indicator)){
            SelectWorld selectWorld = new SelectWorld();
            Greenfoot.setWorld(selectWorld);
        }
    }

    // Fill String[] with text relating to MC stories
    // Change file paths to actual images later on, change font as needed
    private void fillCharacterBackgroundAndStories(){
        // Thief
        characterBackgrounds[0] = new GreenfootImage("images/inmate/thief-background.jpg");
        characterBackgrounds[0].scale(1200, 850);
        String[] story1 = {"[name] is a thief that was forced to steal to feed his family.", "He will do anything to escape and reunite with them,", "even if that means going back to his old ways of stealing."};
        characterStories[0] = new SuperTextBox(story1, bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 32), false, 1000, 8, borderColor); 
        // Brute
        //characterBackgrounds[i] = new GreenfootImage("butterfly.png");
        String[] story2 = {"[name] is a brute that ruthlessly blugeoned 10 men to death.", "He posseses superhuman strength and hates everyone.", "Its a miracle all the guards and inmates are still alive... for now."};
        characterStories[1] = new SuperTextBox(story2, bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 32), false, 1000, 8, borderColor); 
        // Scientist
        characterBackgrounds[2] = new GreenfootImage("inmate/scientist-background.jpg");
        String[] story3 = {"[name] is a scientist that's famous for his 'elixir of immortality'.", "However, people found out it was really just water.","He can brew up potions with fatal effects, drinker beware."};
        characterStories[2] = new SuperTextBox(story3, bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 32), false, 1000, 8, borderColor); 
        // Weapons Dealer
        //characterBackgrounds[i] = new GreenfootImage("hedgehog.png");
        String[] story4 = {"[name] is a weapons dealer who was in the military for 20 years.", "He is a pioneer that made posioned and expanding bullets.", "However, those were war crimes that sent him to jail..."};
        characterStories[3] = new SuperTextBox(story4, bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 32), false, 1000, 8, borderColor); 
        // Explosive Expert
        //characterBackgrounds[i] = new GreenfootImage("fish2.png");
        String[] story5 = {"[name] is an explosive expert who accidentally blew up a police station.", "He loves to cause chaos and play pranks on his fellow inmates.", "He is considered the 'joker' of the prison."};
        characterStories[4] = new SuperTextBox(story5, bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 32), false, 1000, 8, borderColor); 
        // Builder
        //characterBackgrounds[i] = new GreenfootImage("hippo.png");
        String[] story6 = {"[name] is a builder who cheaped out on a major construction project", "He used dirt instead of concrete, pocketing the difference.", "His greed caused the death of thousands."};
        characterStories[5] = new SuperTextBox(story6, bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 32), false, 1000, 8, borderColor); 
    }
}
