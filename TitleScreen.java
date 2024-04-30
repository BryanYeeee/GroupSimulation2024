import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends AllWorld
{
    GreenfootImage titleBackground = new GreenfootImage("title-background.png");
    // Colors
    private Color bgColor = new Color(119, 136, 153, 240);
    private Color borderColor = new Color(192, 192, 192);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color textColor = new Color(250, 249, 246);
    // Textboxes
    SuperTextBox title = new SuperTextBox("THE ESCAPERS", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 54), true, 500, 5, borderColor);
    SuperTextBox startIntro = new SuperTextBox("Start Intro", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 200, 5, borderColor);

    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 1);
        setPaintOrder(Fade.class);
        SimulationFont.initalizeFont();
        titleBackground.scale(1200, 850);
        setBackground(titleBackground);
        
        addObject(title, 600, 150);
        addObject(startIntro, 600, 800);
    }
    
    public void act(){ 
        if(Greenfoot.mouseClicked(startIntro)){
            sm.playSound("click");
            Greenfoot.setWorld(new CharacterIntros());
        }
    }
}
