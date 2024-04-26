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
    
    private String name;
    private StatSetter strength;
    private StatSetter speed;
    private StatSetter luck;
    private StatSetter intelligence;
    private String specialty;
    
    
    public MC (int i, MyWorld world, String jobTitle) {
        super(i, jobTitle);
        //this.strength = 11;
        this.world = world;
        heldItems = new ArrayList<Item>();
        if(specialty.equals("Thief")) {
            spd = 3.8;
        }
        if(specialty.equals("Brute")) {
            str = Greenfoot.getRandomNumber(3)+10;
        }
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
       
        name = serializedData[0];
        str = Double.parseDouble(serializedData[2]);
        spd = Double.parseDouble(serializedData[3]);
        intel = Double.parseDouble(serializedData[4]);
        specialty = serializedData[5];

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
