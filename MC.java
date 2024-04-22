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
    private String[] jobOptions = {"None", "Librarian", "Cook", "Woodwoker", "Metalworker", "Janitor"};
    
    /*
    public MC (int i, MyWorld world, String jobTitle, String specialty) {
        super(i, jobTitle);
        //this.strength = 11;
        this.world = world;
        this.specialty = specialty;
        heldItems = new ArrayList<Item>();
        if(specialty.equals("Thief")) {
            this.speed = 3.8;
        }
        if(specialty.equals("Brute")) {
            this.strength = Greenfoot.getRandomNumber(3)+10;
        }
        System.out.println(specialty + ": " + strength);
    }
    */
    
    /**
     * Prisoner constructor
     * 
     * @param name                      name of the character
     * @param initialStrength           sets an initial strength stat
     * @param initialSpeed              sets an initial speed stat
     * @param initialLuck               sets an initial luck stat
     * @param initialIntelligence       sets an initial intelligence stat
     */
    public MC(int i, String name, double strength, double speed, double luck, double intelligence, String jobTitle) {
        super(i);
        this.name = name;
        this.str = strength;
        this.spd = speed;
        this.luc = luck;
        this.intel = intelligence;
        this.jobTitle = jobTitle;
        
        
        if(jobTitle.equals("Janitor")) {
            specialty  = "Scientist";
        } else if(jobTitle.equals("Librarian")) {
            specialty = "Thief";
        } else if(jobTitle.equals("Metalworker")) {
            specialty = "Weapons Dealer";
        } else if(jobTitle.equals("Woodworker")) {
            specialty = "";
        } else if(jobTitle.equals("Cook")) {
            specialty = "";
        } else if(jobTitle.equals("None")) {
            specialty = "Brute";
        }
        
    }
    
    /**
     * Act - do whatever the MC wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Method to deserialize and restore the state of the prisoner
     * 
     * help from ChatGPT
     */
    public void deserializeState(String serializedData) {
        // Deserialize the string and restore the state of the prisoner
        String[] parts = serializedData.split(",");
        //yes = Double.parseDouble(parts[0]);
        name = parts[0];
        str = Double.parseDouble(parts[1]);
        //addStr(Double.parseDouble(parts[1]));
        spd = Double.parseDouble(parts[2]);
        //addSpeed(Double.parseDouble(parts[2]));
        luc = Double.parseDouble(parts[3]);
        //addLuck(Double.parseDouble(parts[3]));
        intel = Double.parseDouble(parts[4]);
        //addIntel(Double.parseDouble(parts[4]));
        jobTitle = parts[5];
    }
    
    public String getJob() {
        return jobTitle;
    }
    
    public boolean hasJob() {
        return !jobTitle.equals("None");
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
