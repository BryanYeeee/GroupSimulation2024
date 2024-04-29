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
    private SavedPrisoner[] savedPrisoners;
    private SavedPrisoner[] imageSavedPrisoners;
    
    private List<String> serializedPrisonersState;
    
    /**
     * Constructor for StatWorld.
     */
    public StatWorld(List<String> selectedPrisoners)
    {
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 1);

        savedPrisoners = new SavedPrisoner[4];
        String[] savedData = new String[4];
        
        int index = 0;
        SelectWorld selectWorld = new SelectWorld();// what does this do?
        for(String serializedData : selectedPrisoners) {
            if(index < 4) {
                savedData[index] = serializedData;
                index++;
            } else {
                break;
            }
        }
        
        
        for(int i = 0; i < 4; i++) {
            savedPrisoners[i] = new SavedPrisoner("", "", 0, 0, 0, "");
            savedPrisoners[i].deserializeState(savedData[i]);
            
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
        
        //This is to get the image of the chosen prisoners
        imageSavedPrisoners = new SavedPrisoner[4];
        
        imageSavedPrisoners[0] = new SavedPrisoner(savedPrisoners[0].getSpecialty(), 100, 100);
        imageSavedPrisoners[1] = new SavedPrisoner(savedPrisoners[1].getSpecialty(), 100, 100);
        imageSavedPrisoners[2] = new SavedPrisoner(savedPrisoners[2].getSpecialty(), 100, 100);
        imageSavedPrisoners[3] = new SavedPrisoner(savedPrisoners[3].getSpecialty(), 100, 100);
        
        addObject(imageSavedPrisoners[0], 100, 200);
        addObject(imageSavedPrisoners[1], 100, 500);
        addObject(imageSavedPrisoners[2], 450, 200);
        addObject(imageSavedPrisoners[3], 450, 500);
        

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
        serializedPrisonersState = savePrisonersState();
        //System.out.println(serializedPrisonersState);
        Greenfoot.setWorld(new IntroWorld(serializedPrisonersState));
    }
}
