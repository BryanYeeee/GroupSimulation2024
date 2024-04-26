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
    private double hp, maxHp;
    private double intel, maxIntel;
    private double spd, maxSpd;
    
    private GreenfootImage image;
    private String name;
    private StatSetter strength;
    private StatSetter speed;
    private StatSetter intelligence;
    private String jobTitle;
    private String specialty;
    
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
    public SavedPrisoner(String name, String jobTitle, double initialStrength, double initialSpeed, double initialIntelligence, String specialty) {
        maxStr = 10;
        hp = 100;
        maxHp = 200;
        maxIntel = 10;
        maxSpd = 3;
        
        this.name = name;
        this.jobTitle = jobTitle;
        strength = new StatSetter(initialStrength, false);
        speed = new StatSetter(initialSpeed, true);
        intelligence = new StatSetter(initialIntelligence, false);
        this.specialty = specialty;
        
        /*
        if(name.equals("Brute")) {
            job = "Brute";
            //image = new GreenfootImage("");
        } else if(name.equals("Thief")) {
            job = "Thief";
            //image = new GreenfootImage("");
        } else if(name.equals("Weapondealer")) {
            job = "Weapondealer";
            //image = new GreenfootImage("");
        } else if(name.equals("Scientist")) {
            job = "Scientist";
            //image = new GreenfootImage("");
        } else if(name.equals("Explosiveexpert")) {
            job = "Explosiveexpert";
            //image = new GreenfootImage("");
        } else if(name.equals("Builder")) {
            job = "Builder";
            //image = new GreenfootImage("");
        }
        */
    }
    
    public String getSpecialty() {
        return specialty;
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
     * Method to get prisoner's intelligence
     * 
     * @return intelligence
     */
    public StatSetter getIntelligence() {
        return intelligence;
    }
    
    public void setJob(String newJob) {
        jobTitle = newJob;
    }

    public String getJob() {
        return jobTitle;
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

        // Intelligence controls
        StatButton increaseIntelligenceButton = new StatButton(intelligence, true);
        StatButton decreaseIntelligenceButton = new StatButton(intelligence, false);
        Textbox intelligenceTextbox = new Textbox("Int", 70, intelligence);
        getWorld().addObject(increaseIntelligenceButton, x + 50, y + 100);
        getWorld().addObject(decreaseIntelligenceButton, x, y + 100);
        getWorld().addObject(intelligenceTextbox, x -55, y + 100);
    }
    
    /**
     * Method to serialize the state of the prisoner
     * 
     * help from ChatGPT
     */
    public String serializeState() {
        // Serialize the state into a string
        return name + "," + jobTitle + "," +strength.getValue() + "," + speed.getValue() + "," + intelligence.getValue() + "," + specialty;
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
        strength.setValue(Double.parseDouble(parts[2]));
        speed.setValue(Double.parseDouble(parts[3]));
        intelligence.setValue(Double.parseDouble(parts[4]));
        specialty = parts[5];
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
     * Adds hp
     * 
     * @param addHp
     */
    public void addHp(double addHp) {
        hp = hp + addHp > maxHp ? maxHp: hp + addHp;
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
     * Adds speed
     * 
     * @param addSpd
     */
    public void addSpeed(double addSpd) {
        spd = spd + addSpd > maxSpd ? maxSpd: spd + addSpd;
    }
}
