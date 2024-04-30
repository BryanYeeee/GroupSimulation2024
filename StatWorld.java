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
    
    
    // Colors
    private Color bgColor = new Color(119, 136, 153, 240);
    private Color borderColor = new Color(192, 192, 192);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color textColor = new Color(250, 249, 246);
    
    // Info
    private SuperTextBox bruteInfo = new SuperTextBox("Buck: Brute", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 300, 5, borderColor);
    private SuperTextBox thiefInfo = new SuperTextBox("Wyatt: Thief", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 300, 5, borderColor);
    private SuperTextBox weaponsDealerInfo = new SuperTextBox("Leon: Weapons Dealer", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 300, 5, borderColor);
    private SuperTextBox scientistInfo = new SuperTextBox("Waldo: Scientist", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 300, 5, borderColor);
    private SuperTextBox explosiveExpertInfo = new SuperTextBox("Aron: Explosive Expert", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 300, 5, borderColor);
    private SuperTextBox builderInfo = new SuperTextBox("Reuben: Builder", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 300, 5, borderColor);
    
    private List<String> serializedPrisonersState;
    
    /**
     * Constructor for StatWorld.
     */
    public StatWorld(List<String> selectedPrisoners)
    {
        super(AllWorld.WORLD_WIDTH, AllWorld.WORLD_HEIGHT, 1);
        setPaintOrder(Fade.class, SavedPrisoner.class, Button.class, Textbox.class);
        
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

        //creating box under stat selections
        StatBox box1 = new StatBox();
        addObject(box1, 386, 399); // was 386, 339
        
        // Info
        int[] xCoords = {200, 200, 550, 550};
        int[] yCoords = {310, 615, 310, 615};
        
        // Top left, bottom left, top right, bottom right
        for(int i = 0; i < 4; i++){
            String name = selectedPrisoners.get(i);
            String[] splitName = name.split(",");
            if(splitName[5].equals("Thief")){ // Thief
                addObject(thiefInfo, xCoords[i], yCoords[i]);
            } else if(splitName[5].equals("Brute")){ // Brute
                addObject(bruteInfo, xCoords[i], yCoords[i]);
            } else if(splitName[5].equals("Scientist")){ // Scientist
                addObject(scientistInfo, xCoords[i], yCoords[i]);
            } else if(splitName[5].equals("Weapons Dealer")){ // Weapons Dealer
                addObject(weaponsDealerInfo, xCoords[i], yCoords[i]);
            } else if(splitName[5].equals("Explosive Expert")){ // Expolsive Expert
                addObject(explosiveExpertInfo, xCoords[i], yCoords[i]);
            } else { // Builder
                addObject(builderInfo, xCoords[i], yCoords[i]);
            }
        }
        
        
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
            sm.stopSoundLoop("Statscreen");
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
