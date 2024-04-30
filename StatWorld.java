import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;

/**
 * World where the stats of the 4main characters can be changed
 * 
 * @author Ainson Z
 * @version April 2024
 */
public class StatWorld extends AllWorld
{
    private GreenfootImage bgImage;
    private SavedPrisoner[] savedPrisoners;
    private SavedPrisoner[] imageSavedPrisoners;
    
    private List<String> serializedPrisonersState;
    
    /**
     * Constructor for StatWorld.
     */
    public StatWorld(List<String> selectedPrisoners)
    {
        super(AllWorld.WORLD_WIDTH, AllWorld.WORLD_HEIGHT, 1);
        setPaintOrder(Fade.class);
        
        sm.playSound("Statscreen");
        
        savedPrisoners = new SavedPrisoner[4];
        
        int index = 0;
        for(String serializedData : selectedPrisoners) {
            savedPrisoners[index] = new SavedPrisoner(serializedData);
            index++;
        }

        //adding them to the world to edit their stats
        addObject(savedPrisoners[0], 100, 200);
        addObject(savedPrisoners[1], 100, 500);
        addObject(savedPrisoners[2], 450, 200);
        addObject(savedPrisoners[3], 450, 500);

        //creating stat menus
        savedPrisoners[0].createControls(275, 125);
        savedPrisoners[1].createControls(275, 425);
        savedPrisoners[2].createControls(625, 125);
        savedPrisoners[3].createControls(625, 425);
        
        //Go to next world button
        NextButton next = new NextButton("NextButton.png");
        addObject(next, 1000, 60);

        bgImage = new GreenfootImage("statsWorldBg3.jpg");
        bgImage.scale(AllWorld.WORLD_WIDTH, AllWorld.WORLD_HEIGHT);
        setBackground(bgImage);
    }

    /**
     * Used to save all the stats of each prisoner
     */
    public List<String> savePrisonersState() {
        List<String> serializedDataList = new ArrayList<>();
        for (SavedPrisoner prisoner : savedPrisoners) {
            String serializedData = prisoner.serializeState();
            serializedDataList.add(serializedData);
        }
        
        return serializedDataList;
    }
    

    /**
     * Act Method
     */
    public void act() {
        if(NextButton.getSwitchWorld()) {
            //sm.pauseSounds();
            NextButton.resetSwitchWorld();
            switchToWorld();
        }
    }

    /**
     * saves all prisoner data and switches worlds
     */
    public void switchToWorld() {
        serializedPrisonersState = savePrisonersState();
        System.out.println(serializedPrisonersState);
        Greenfoot.setWorld(new IntroWorld(serializedPrisonersState));
    }
}
