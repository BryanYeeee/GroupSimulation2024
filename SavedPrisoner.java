import greenfoot.*;
import java.io.Serializable;

/**
 * Creates an instance of a prisoner
 * There will be 5 total prisoners that are the main characters
 * 
 * ChatGPT
 */
public class SavedPrisoner extends Actor {
    
    private double str, maxStr;
    private double intel, maxIntel;
    private double luc, maxLuc;
    private double spd, maxSpd;
    
    private String name;
    private StatSetter strength;
    private StatSetter speed;
    private StatSetter luck;
    private StatSetter intelligence;
    private String job;
    
    //private GreenfootImage inmate;
    
    private String[] jobOptions = {"None", "Librarian", "Cook", "Woodwoker", "Metalworker", "Janitor"};

    /**
     * Prisoner constructor
     * 
     * @param name                      name of the character
     * @param initialStrength           sets an initial strength stat
     * @param initialSpeed              sets an initial speed stat
     * @param initialLuck               sets an initial luck stat
     * @param initialIntelligence       sets an initial intelligence stat
     */
    public SavedPrisoner(String name, double initialStrength, double initialSpeed, double initialLuck, double initialIntelligence, String job) {
        
        maxStr = 10;
        maxIntel = 10;
        maxLuc = 10;
        maxSpd = 3;
        
        this.name = name;
        strength = new StatSetter(initialStrength, false);
        speed = new StatSetter(initialSpeed, true);
        luck = new StatSetter(initialLuck, false);
        intelligence = new StatSetter(initialIntelligence, false);
        this.job = job;
        
    }
    
    /**
     * Method to get prisoner's name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get prisoner's strength 
     * 
     * @return strength
     */
    public StatSetter getStrength() {
        return strength;
    }

    /**
     * Method to get prisoner's speed
     * 
     * @return speed
     */
    public StatSetter getSpeed() {
        return speed;
    }

    /**
     * Method to get prisoner's luck
     * 
     * @return luck
     */
    public StatSetter getLuck() {
        return luck;
    }

    /**
     * Method to get prisoner's intelligence
     * 
     * @return intelligence
     */
    public StatSetter getIntelligence() {
        return intelligence;
    }
    
    
    public void setJob(String newJob) {
        job = newJob;
    }

    public String getJob() {
        return job;
    }
    
    
    /**
     * Creates stat's increase and decrease buttons, and text to show stat
     * 
     * 
     * help from ChatGPT
     */
    public void createControls(int x, int y) {
        // Strength controls
        StatButton increaseStrengthButton = new StatButton(strength, true);
        StatButton decreaseStrengthButton = new StatButton(strength, false);
        Textbox strengthTextbox = new Textbox("Str", 70, strength);
        getWorld().addObject(increaseStrengthButton, x + 50, y);
        getWorld().addObject(decreaseStrengthButton, x, y);
        getWorld().addObject(strengthTextbox, x - 55, y);

        // Speed controls
        StatButton increaseSpeedButton = new StatButton(speed, true);
        StatButton decreaseSpeedButton = new StatButton(speed, false);
        Textbox speedTextbox = new Textbox("Spd", 70, speed);
        getWorld().addObject(increaseSpeedButton, x + 50, y + 50);
        getWorld().addObject(decreaseSpeedButton, x, y + 50);
        getWorld().addObject(speedTextbox, x - 55, y + 50);

        // Luck controls
        StatButton increaseLuckButton = new StatButton(luck, true);
        StatButton decreaseLuckButton = new StatButton(luck, false);
        Textbox luckTextbox = new Textbox("Luc", 70, luck);
        getWorld().addObject(increaseLuckButton, x + 50, y + 100);
        getWorld().addObject(decreaseLuckButton, x, y + 100);
        getWorld().addObject(luckTextbox, x -55, y + 100);

        // Intelligence controls
        StatButton increaseIntelligenceButton = new StatButton(intelligence, true);
        StatButton decreaseIntelligenceButton = new StatButton(intelligence, false);
        Textbox intelligenceTextbox = new Textbox("Int", 70 , intelligence);
        getWorld().addObject(increaseIntelligenceButton, x + 50, y + 150);
        getWorld().addObject(decreaseIntelligenceButton, x, y + 150);
        getWorld().addObject(intelligenceTextbox, x - 55, y + 150);
        
        // Job controls
        JobButton switchJobs = new JobButton(jobOptions, this);
        getWorld().addObject(switchJobs, x, y + 200);
        
    }
    
    /**
     * Method to serialize the state of the prisoner
     * 
     * help from ChatGPT
     */
    public String serializeState() {
        // Serialize the state into a string
        return name + "," + strength.getValue() + "," + speed.getValue() + "," + luck.getValue() + "," + intelligence.getValue() + "," + job;
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
        strength.setValue(Double.parseDouble(parts[1]));
        //addStr(Double.parseDouble(parts[1]));
        speed.setValue(Double.parseDouble(parts[2]));
        //addSpeed(Double.parseDouble(parts[2]));
        luck.setValue(Double.parseDouble(parts[3]));
        //addLuck(Double.parseDouble(parts[3]));
        intelligence.setValue(Double.parseDouble(parts[4]));
        //addIntel(Double.parseDouble(parts[4]));
        job = parts[5];
    }
    
    /**
     * Adds strength
     * 
     * @param addStrength
     */
    public void addStr(double addStrength) {
        str = str + addStrength > maxStr ? maxStr: str + addStrength;
    }
    
    /**
     * Adds intelligence
     * 
     * @param addIntel
     */
    public void addIntel(double addIntel) {
        intel = intel + addIntel> maxIntel ? maxIntel: intel + addIntel;
    }
    
    /**
     * Adds luck
     * 
     * @param addLuc
     */
    public void addLuck(double addLuc) {
        luc = luc + addLuc > maxLuc ? maxLuc: luc + addLuc;
    }
    
    /**
     * Adds speed
     * 
     * @param addSpd
     */
    public void addSpeed(double addSpd) {
        spd = spd + addSpd > maxSpd ? maxSpd: spd + addSpd;
    }

    /**
     * Act method
     */
    public void act() {
        //move();
    }

}
