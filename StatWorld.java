import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;

/**
 * World where the stats of the 5 main characters can be changed
 * 
 * @author Ainson 
 * @version April 2024
 */
public class StatWorld extends AllWorld
{
    private GreenfootImage bgImage;
    
    
    /**
     * Constructor for StatWorld.
     */
    public StatWorld()
    {
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 1);

        //creation of the 5 main characters    
        SavedPrisoner mc1 = new SavedPrisoner("Bob", 5, 1, 3, 6, "none");
        SavedPrisoner mc2 = new SavedPrisoner("Alice", 5, 1, 3, 6, "none");
        SavedPrisoner mc3 = new SavedPrisoner("Neo", 5, 1, 3, 6, "none");
        SavedPrisoner mc4 = new SavedPrisoner("Hater", 5, 1, 3, 6, "none");

        //adding them to the world to edit their stats
        addObject(mc1, 100, 200);
        addObject(mc2, 100, 500);
        addObject(mc3, 450, 200);
        addObject(mc4, 450, 500);
        

        //creating stat menus
        mc1.createControls(275, 125);
        mc2.createControls(275, 425);
        mc3.createControls(625, 125);
        mc4.createControls(625, 425);
        //mc5.createControls(975, 425);
        
        

        //Go to next world button
        NextButton next = new NextButton("NextButton.png");
        addObject(next, 1000, 60);
        
        bgImage = new GreenfootImage("statsWorldBg3.jpg");
        bgImage.scale(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        setBackground(bgImage);
    }

    /**
     * Used to save all the stats of each prisoner
     */
    public List<String> savePrisonersState() {
        List<String> serializedDataList = new ArrayList<>();
        for (Object object : getObjects(SavedPrisoner.class)) {
            SavedPrisoner mc = (SavedPrisoner) object;
            String serializedData = mc.serializeState();
            serializedDataList.add(serializedData);
        }
        return serializedDataList;
    }
    

    /**
     * Act Method
     */
    public void act() {
        
        acts++;
        if(acts >= 120 && NextButton.getSwitchWorld()) {
            NextButton.resetSwitchWorld();
            switchToWorld();
        }
        
    }

    /**
     * saves all prisoner data and switches worlds
     */
    public void switchToWorld() {
        List<String> serializedDataList = savePrisonersState();
        Greenfoot.setWorld(new MyWorld(serializedDataList));
    }
}
