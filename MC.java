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
    
    public MC (int i, MyWorld world, String jobTitle, String specialty) {
        super(i, jobTitle);
        //this.strength = 11;
        this.world = world;
        this.specialty = specialty;
        this.name = name;
        name = "MC"; // temp
        heldItems = new ArrayList<Item>();
        if(specialty.equals("Thief")) {
            this.speed = 3.8;
        }
        if(specialty.equals("Brute")) {
            this.strength = Greenfoot.getRandomNumber(3)+10;
        }
        System.out.println(specialty + ": " + strength);
    }
    /**
     * Act - do whatever the MC wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    public String getSpecialty() {
        return specialty;
    }
    
    public void setAction(String action) {
        this.currentAction = action;
    }
    
    public void giveItem(Item item) {
        this.heldItems.add(item);
    }
    
    public void removeItem(Item item) {
        this.heldItems.remove(item);
    }
    
    public void useItem(int i) {
        heldItems.get(i).useItem(world, this);
    }
    
    public String getName(){
        return name;
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
}
