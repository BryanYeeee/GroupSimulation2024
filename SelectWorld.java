import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class SelectWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelectWorld extends AllWorld
{
    private int numSelectedPrisoners;
    private List<SavedPrisoner> selectedPrisoners = new ArrayList<>();
    private List<String> serializedPrisonersState;
    
    private GreenfootImage bgImage;
    
    /**
     * Constructor for objects of class SelectWorld.
     * 
     */
    public SelectWorld()
    {
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 1);
        numSelectedPrisoners = 0;
        
        SavedPrisoner mc1 = new SavedPrisoner("Brute", "None", 8, 1, 3, "Brute");
        SavedPrisoner mc2 = new SavedPrisoner("Thief", "Librarian", 5, 1, 3, "Thief");
        SavedPrisoner mc3 = new SavedPrisoner("WeaponDealer", "Metalworker", 5, 1, 3, "WeaponDealer");
        SavedPrisoner mc4 = new SavedPrisoner("Scientist", "Cook", 5, 1, 3, "Scientist");
        SavedPrisoner mc5 = new SavedPrisoner("ExplosiveE", "Janitor", 5, 1, 3, "Explosiveexpert");
        SavedPrisoner mc6 = new SavedPrisoner("Builder", "Woodworker", 5, 1, 3, "Builder");
        
        addObject(mc1, 150, 250);
        addObject(mc2, 150, 550);
        addObject(mc3, 500, 250);
        addObject(mc4, 500, 550);
        addObject(mc5, 850, 250);
        addObject(mc6, 850, 550);
        
        /*
        mc1.createButton(150, 250);
        mc2.createButton(150, 550);
        mc3.createButton(500, 250);
        mc4.createButton(500, 550);
        mc5.createButton(850, 250);
        mc6.createButton(850, 550);
        */
       
        SelectButton button1 = new SelectButton(this, mc1);
        addObject(button1, 150 + 150, 250);
        SelectButton button2 = new SelectButton(this, mc2);
        addObject(button2, 150 + 150, 550);
        SelectButton button3 = new SelectButton(this, mc3);
        addObject(button3, 500 + 150, 250);
        SelectButton button4 = new SelectButton(this, mc4);
        addObject(button4, 500 + 150, 550);
        SelectButton button5 = new SelectButton(this, mc5);
        addObject(button5, 850 + 150, 250);
        SelectButton button6 = new SelectButton(this, mc6);
        addObject(button6, 850 + 150, 550);
        
        NextButton next = new NextButton("NextButton.png");
        addObject(next, 1000, 800);
        
        bgImage = new GreenfootImage("PrisonBg2.png");
        setBackground(bgImage);
    }
    
    
    public List<SavedPrisoner> getSelectedPrisoners() {
        //problem here
        return selectedPrisoners;
    }
    
    
    public int getNumSelectedPrisoners() {
        return numSelectedPrisoners;
    }
    
    public void incrementNumSelectedPrisoners() {
        numSelectedPrisoners++;
    }

    public void decrementNumSelectedPrisoners() {
        numSelectedPrisoners--;
    }
    
    public void addSelectedPrisoner(SavedPrisoner prisoner) {
        selectedPrisoners.add(prisoner);
    }
    
    public void removeSelectedPrisoner(SavedPrisoner prisoner) {
        selectedPrisoners.remove(prisoner);
    }
    
    public void transitionWorld() {
        serializedPrisonersState = saveSelectedPrisonersState();
        System.out.println(serializedPrisonersState);
        Greenfoot.setWorld(new StatWorld(serializedPrisonersState));
    }
    
    /**
     * Used to save all the stats of each prisoner
     */
    public List<String> saveSelectedPrisonersState() {
        List<String> serializedDataList = new ArrayList<>();
        for (SavedPrisoner prisoner: selectedPrisoners) {
            String serializedData = prisoner.serializeState();
            serializedDataList.add(serializedData);
        }
        return serializedDataList;
    }
    
    public void act() {
        if (numSelectedPrisoners == 4) {
            if(NextButton.getSwitchWorld()) {
                //System.out.print(getSelectedPrisoners());
                NextButton.resetSwitchWorld();
                transitionWorld();
            }
            
        }
    }
}
