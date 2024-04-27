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
    
    private String name;
    private String specialty;
    private String currentAction;
    private Item[] items = new Item[2];
    
    // For cutscene/intro world
    public MC(int i, boolean inIntro, String specialty){
        super(i, inIntro);
        this.specialty = specialty;
        GreenfootImage cutsceneImage = new GreenfootImage("images/inmate/male_white/idle/D0.png");
        cutsceneImage.scale(120, 180);
        setImage(cutsceneImage);
    }
    
    // For main prisoner world
    public MC (int i, MyWorld world, String jobTitle, String specialty) {
        super(i, jobTitle);
        //this.strength = 11;
        this.world = world;
        this.specialty = specialty;
        this.name = name;
        name = "MC"; //temp
        heldItems = new ArrayList<Item>();
        if(specialty.equals("Thief")) {
            this.speed = 3.8;
        }
        if(specialty.equals("Brute")) {
            this.strength = Greenfoot.getRandomNumber(3)+10;
        }
        System.out.println(specialty + ": " + strength);
    }

    public void act()
    {
        super.act();
    }
    
    public String getSpecialty() {
        return specialty;
    }
    
    public String getName(){
        return name;
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
                i.useItem(world, this);
                heldItems.remove(i);
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
    
}
