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
        str = Integer.parseInt(serializedData[2]);
        speed = Double.parseDouble(serializedData[3]);
        intel = Integer.parseInt(serializedData[4]);
        specialty = serializedData[5];
        currentAction = "";
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
    
    public String getEscapeMethod(){
        return escapeMethod;
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
                setAction("Crafting Items");
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

    public void dialogue(String text, int seconds, String speakerName){

        world.addObject(new Dialogue(text,seconds,speakerName),bannerPosX,bannerPosY); 
        
    }
}
