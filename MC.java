import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class MC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MC extends Prisoner
{
    private MyWorld world;
    private ArrayList<Item> heldItems;

    private String currentAction;
    private Item[] items = new Item[2];

    private String name; // person name
    private boolean doneCrafting;

    private StatSetter strength;
    private StatSetter speed;
    private StatSetter luck;
    private StatSetter intelligence;
    private String specialty; // more like a class, thief, brute etc.

    // For cutscene/intro world
    public MC(int i, boolean inIntro, String specialty){
        super(i, inIntro);
        this.specialty = specialty;
        GreenfootImage cutsceneImage = new GreenfootImage("images/inmate/male_white/idle/D0.png");
        cutsceneImage.scale(120, 180);
        setImage(cutsceneImage);
    }

    public MC (int i, MyWorld world, String jobTitle) {
        super(i, jobTitle);
        //this.strength = 11;
        this.world = world;
        heldItems = new ArrayList<Item>();
    }

    /**
     * Prisoner constructor
     * 
     * @param name                      name of the character
     * @param initialStrength           sets an initial strength stat
     * @param initialSpeed              sets an initial speed stat
     * @param initialLuck               sets an initial luck stat
     * @param initialIntelligence       sets an initial intelligence stat
     */
    public MC(int i, MyWorld world, String[] serializedData) {
        super(i, serializedData[1]);
        this.name = name;
        this.world = world;
        heldItems = new ArrayList<Item>();
        name = serializedData[0];
        str = Double.parseDouble(serializedData[2]);
        spd = Double.parseDouble(serializedData[3]);
        intel = Double.parseDouble(serializedData[4]);
        specialty = serializedData[5];
        //str += 10;
        /*
        if(name.equals("Brute")) {
        jobTitle = "None";
        //image = new GreenfootImage("");
        } else if(name.equals("Thief")) {
        jobTitle = "Librarian";
        //image = new GreenfootImage("");
        } else if(name.equals("Weapondealer")) {
        jobTitle = "Metalworker";
        //image = new GreenfootImage("");
        } else if(name.equals("Scientist")) {
        jobTitle = "Cook";
        //image = new GreenfootImage("");
        } else if(name.equals("Explosiveexpert")) {
        jobTitle = "Janitor";
        //image = new GreenfootImage("");
        } else if(name.equals("Builder")) {
        jobTitle = "Woodworker";
        //image = new GreenfootImage("");
        }
         */
    }

    /**
     * Act - do whatever the MC wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }

    
    public boolean isDoneCrafting() {
        return doneCrafting;
    }
    
    public void setDoneCrafting(boolean isCrafting) {
        this.doneCrafting = isCrafting;
    }
    
     /**
     * Method to deserialize and restore the state of the prisoner
     * 
     * help from ChatGPT
     */
    public void deserializeState(String serializedData) {
        // Deserialize the string and restore the state of the prisoner
        String[] parts = serializedData.split(",");
        name = parts[0];
        jobTitle = parts[1];
        str = Double.parseDouble(parts[2]);
        spd = Double.parseDouble(parts[3]);
        intel = Double.parseDouble(parts[4]);
        specialty = parts[5];
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getName(){
        return name;
    }
    
    public String getCurrentAction(){
        return currentAction;
    }

    public void setAction(String action) {
        this.currentAction = action;
    }

    public void giveItem(Item item) {
        for(Item i : heldItems) {
            if(i.getClass() == item.getClass()) {
                return;
            }
        }
        getWorld().addObject(item, getX(), getY());
        item.pickup(this);
        this.heldItems.add(item);
    }

    // public void removeItem(Item item) {
    // this.heldItems.remove(item);
    // }
    /**
     * This method will craft the next material item in heldItems
     * 
     * @return boolean returns true if there are no more materials in heldItems
     */
    public boolean craftItem() {
        for(Item i : heldItems) {
            if(i.isMaterial()) {
                world.removeObject(i);
                heldItems.remove(i);
                i.useItem(world, this);
                return false;
            };
        }
        return true;
    }

    public ArrayList<Item> getItems() {
        return heldItems;
    }

    public int getIndex(){
        return index;
    }

    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        if(specialty.equals("Scientist")){ 
            result.add(1);
        }
        else if(specialty.equals("Thief")){
            result.add(5);
            result.add(2);
        }
        else if(specialty.equals("Brute")){
            result.add(3);
        }
        else if(specialty.equals("Weapons Dealer")){
            result.add(4);
        }
        System.out.println(result);
        return result; 
    }

    public void addItem(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {  // Check if the slot is empty
                items[i] = item;  
                System.out.println("Item added to slot " + i);
                return;  
            }
        }
        System.out.println("Inventory is full. Cannot add item.");
    }

    public int getItemCount() {
        int count = 0;
        for (Item item : items) {
            if (item != null) {
                count++;  // Increment count for each non-null item found
            }
        }
        return count;  // Return the total count of items
    }

    /**
     * Adds strength
     * 
     * @param addStrength
     */
    public void addStr(double addStrength) {
        str += addStrength;
    }

    /**
     * Adds intelligence
     * 
     * @param addIntel
     */
    public void addIntel(double addIntel) {
        intel += addIntel;
    }

    /**
     * Adds speed
     * 
     * @param addSpd
     */
    public void addSpeed(double addSpd) {
        spd += addSpd;
    }
}
