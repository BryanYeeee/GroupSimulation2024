import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The MC subclass.
 * 
 * @author Bryan Y, Constructor by Ainson Z
 * @version April 2024
 */
public class MC extends Prisoner
{
    private MyWorld world;
    private ArrayList<Item> heldItems;

    private String currentAction;
    private Item[] items = new Item[2];

    private String name; // person name
    private boolean doneCrafting;
    private String escapeMethod;
    

    private String specialty; // more like a class, thief, brute etc.
    private int bannerPosX;
    private int bannerPosY;
    // For cutscene/intro world
    /**
     * MC constructor
     * 
     * @param i
     * @param inIntro                       Checks if the MC is in the intro world
     * @param specialty                     Sets specialty of MC
    */
    public MC(int i, boolean inIntro, String specialty){
        super(i, inIntro);
        this.specialty = specialty;
        GreenfootImage cutsceneImage = new GreenfootImage("images/inmate/male_white/idle/D0.png");
        cutsceneImage.scale(120, 180);
        setImage(cutsceneImage);
        bannerPosX = world.WORLD_WIDTH/2;
        bannerPosY = 50;
    }

    /**
     * MC constructor
     * 
     * @param i                         
     * @param world                     Which world to place the MC in
     * @param serializedData            takes in name, job, str, speed, intel and specialty
     */
    public MC(int i, MyWorld world, String[] serializedData) {
        super(i, serializedData[1]);
        this.name = name;
        this.world = world;
        heldItems = new ArrayList<Item>();
        name = serializedData[0];
        str = Integer.parseInt(serializedData[2]);
        speed = Double.parseDouble(serializedData[3]);
        intel = Integer.parseInt(serializedData[4]);
        specialty = serializedData[5];
        currentAction = "";
    }

    /**
     * Act method of MC
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Checks if MC is done crafting
     * 
     * @return doneCraft                Boolean to see if crafting is finished
     */
    public boolean isDoneCrafting() {
        return doneCrafting;
    }

    /**
     * 
     * @param isCrafting
     */
    public void setDoneCrafting(boolean isCrafting) {
        this.doneCrafting = isCrafting;
    }
    
    /**
     * Gets the escape method MC is performing
     * 
     * @return escapeMethod             Gets the escape method of the MC
     */
    public String getEscapeMethod(){
        return escapeMethod;
    }
    
    /**
     * 
     */
    public void setEscapeMethod(String method){
        escapeMethod = method;
    }

    /**
     * Gets the specialty of MC
     * 
     * @return specialty                Gets the MC's specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Gets the name of MC
     * 
     * @return name                     Gets the MC's name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the MC's current action
     * 
     * @return currentAction            Gets the MC's current action
     */
    public String getCurrentAction(){
        return currentAction;
    }
    
    /**
     * Sets status of MC's on game menu bar
     * 
     * @param action                    Action of MC to update status bar
     */
    public void setAction(String action) {
        if(isDead) {
            this.currentAction = "Reviving...";
            StatusBar.setUpdate(true);
        } else if(inFight) {
            this.currentAction = "In a Fight";
            StatusBar.setUpdate(true);
        } else if(!currentAction.equals(action)) {
            this.currentAction = action;
            StatusBar.setUpdate(true);
        }
    }

    /**
     * Gives item to MC
     * 
     * @param item                      Item that is given to MC
     */
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
    
    /**
     * This method will                 craft the next material item in heldItems
     * 
     * @return boolean                  returns true if there are no more materials in heldItems
     */
    public boolean craftItem() {
        for(Item i : heldItems) {
            if(i.isMaterial()) {
                setAction("Crafting Items");
                world.removeObject(i);
                heldItems.remove(i);
                i.useItem(world, this);
                return false;
            };
        }
        return true;
    }

    /**
     * Returns the held items of MC
     * 
     * @return heldItems                Array of MC's held items
     */
    public ArrayList<Item> getItems() {
        return heldItems;
    }

    /**
     * Tracks which index the MC is
     * 
     * @return index                    Index of MC in the world
     */
    public int getIndex(){
        return index;
    }

    /**
     * Gets the accessories and assigns based on MC's specialty
     * 
     * @return result                   Array of accessories given to MC
     */
    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        if(specialty.equals("Scientist")){ 
            result.add(1);
        }
        else if(specialty.equals("Thief")){
            result.add(8);
            result.add(10);
        }
        else if(specialty.equals("Brute")){
            result.add(3);
        }
        else if(specialty.equals("Weapons Dealer")){
            result.add(4);
        }
        else if(specialty.equals("Explosive Expert")){
            result.add(7);
        }
        else if(specialty.equals("Builder")){
            result.add(9);
        }
        System.out.println(result);
        return result; 
    }

    /**
     * Adds item to MC item slot
     * 
     * @param item                      Item given to MC
     */
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

    /**
     * Returns total count of items
     * 
     * @return count                    Counts amount of items
     */
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
     * Creates a dialogue of what the MC says
     * 
     * @param text                      The text of the MC
     * @param seconds                   Determines how long the banner should stay on screen
     * @param speakerName               Name of the MC dialogue is spoken by
     */
    public void dialogue(String text, int seconds, String speakerName){

        world.addObject(new Dialogue(text,seconds,speakerName),bannerPosX,bannerPosY); 
        
    }
}
