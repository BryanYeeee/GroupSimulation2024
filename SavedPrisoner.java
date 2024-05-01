import greenfoot.*;

/**
 * Creates an instance of a prisoner with saved data for the statworld and selectworld
 * 
 * @author Ainson Z and Bryan Y
 * @version April 2024
 */
public class SavedPrisoner extends Actor {
    
    private int str, maxStr, minStr;
    private int intel, maxIntel, minIntel;
    private double spd, maxSpd, minSpd;
    
    private GreenfootImage image;
    private String name;
    private String jobTitle;
    private String specialty;

    /**
     * Constructor of SavedPrisoner
     * 
     * @param name          The name of the prisoner.
     * @param jobTitle      The job name of the prisoner.
     * @param str           The strength value of the prisoner.
     * @param spd           The speed value of the prisoner.
     * @param intel         The intelligence value of the prisoner.
     * @param specialty     The specialty of the prisoner.
     */
    public SavedPrisoner(String name, String jobTitle, int str, double spd, int intel, String specialty) {        
        this.str = str;
        this.spd = spd;
        this.intel = intel;
        
        this.name = name;
        this.jobTitle = jobTitle;
        this.specialty = specialty;
        
        image = new GreenfootImage("images/"+specialty.replaceAll(" ","")+".png");
        image.scale(100, 100);
        setImage(image);
        
    }
    
    /**
     * Secondary construtor
     * 
     * @param serializedData    The data of the prisoner.
     */
    public SavedPrisoner(String serializedData) {
        deserializeState(serializedData);
        setMaxValues(specialty);
    }
    
    /**
     * A constructor used as an image card for the status bar in the main simulation.
     * 
     * @param img       To differeniate between the previous single String constuctor
     * @param specialty The specialty of the prisoner.
     */
    public SavedPrisoner(boolean img, String specialty) {
        image = new GreenfootImage("images/"+specialty.replaceAll(" ","")+".png");
        image.scale(77, 77);
        setImage(image);
    }
    
    /**
     * Creates stat's increase and decrease buttons, and text to show stat.
     * Help from ChatGPT
     * 
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     */
    public void createControls(int x, int y) {
        // Strength controls
        StatButton increaseStrengthButton = new StatButton(this,"str", true);
        StatButton decreaseStrengthButton = new StatButton(this,"str", false);
        Textbox strengthTextbox = new Textbox(this, "str", 70);
        getWorld().addObject(increaseStrengthButton, x + 50, y + 20);
        getWorld().addObject(decreaseStrengthButton, x, y + 20);
        getWorld().addObject(strengthTextbox, x - 55, y + 20);

        // Speed controls
        StatButton increaseSpeedButton = new StatButton(this,"spd", true);
        StatButton decreaseSpeedButton = new StatButton(this,"spd", false);
        Textbox speedTextbox = new Textbox(this, "spd", 70);
        getWorld().addObject(increaseSpeedButton, x + 50, y + 70);
        getWorld().addObject(decreaseSpeedButton, x, y + 70);
        getWorld().addObject(speedTextbox, x - 55, y + 70);

        // Intelligence controls
        StatButton increaseIntelligenceButton = new StatButton(this,"intel", true);
        StatButton decreaseIntelligenceButton = new StatButton(this,"intel", false);
        Textbox intelligenceTextbox = new Textbox(this, "intel", 70);
        getWorld().addObject(increaseIntelligenceButton, x + 50, y + 120);
        getWorld().addObject(decreaseIntelligenceButton, x, y + 120);
        getWorld().addObject(intelligenceTextbox, x -55, y + 120);
    }
    
    /**
     * Method to serialize the state of the prisoner
     * Help from ChatGPT
     */
    public String serializeState() {
        // Serialize the state into a string
        return name + "," + jobTitle + "," +str + "," + spd + "," + intel + "," + specialty;
    }
    
    /**
     * Method to deserialize and restore the state of the prisoner
     * Help from ChatGPT
     * 
     * @param serializedData    The data to be operated on.
     */
    public void deserializeState(String serializedData) {
        // Deserialize the string and restore the state of the prisoner
        String[] parts = serializedData.split(",");
        name = parts[0];
        jobTitle = parts[1];
        str = Integer.parseInt(parts[2]);
        spd = Double.parseDouble(parts[3]);
        intel = Integer.parseInt(parts[4]);
        specialty = parts[5];
        
        image = new GreenfootImage("images/"+specialty.replaceAll(" ","")+".png");
        image.scale(100,100);
        setImage(image);
    }
    
    /**
     * Return the prisoner's strength stat.
     * 
     * @return int  The strength value.
     */
    public int getStrength() {
        return str;
    }
    
    /**
     * Return the prisoner's speed stat.
     * 
     * @return int  The speed value.
     */
    public double getSpeed() {
        return spd;
    }
    
    /**
     * Return the prisoner's intelligence stat.
     * 
     * @return int  The intelligence value.
     */
    public int getIntel() {
        return intel;
    }
    
    /**
     * Set the max values of the prisoner, to be varied depending on their specialty.
     * 
     * @param specialty     The specialty of the prisoner.
     */
    public void setMaxValues(String specialty) {
        maxStr = 20;
        minStr = 5;
        maxIntel = 100;
        minIntel = 0 ;
        maxSpd = 1.9;
        minSpd = 1.7;
        switch(specialty) {
            case "Thief":
                maxSpd = 4.0;
                minSpd = 3.6;
                maxStr = 10;
                break;
            case "Brute":
                maxStr = 25;
                minStr = 12;
                maxIntel = 60;
                break;
        }
    }
    
    /**
     * Increase the strength stat of the prisoner.
     * 
     * @param increase  The value to be increased by.
     */
    public void addStrength(boolean increase) {
        if(increase) {
            str = str + 1 > maxStr ? maxStr: str + 1;
        } else {
            str = str - 1 < minStr ? minStr: str - 1;
        } 
    }
    
    /**
     * Increase the intelligence stat of the prisoner.
     * 
     * @param increase  The value to be increased by.
     */
    public void addIntel(boolean increase) {
        if(increase) {
            intel = intel + 20 > maxIntel ? maxIntel: intel + 20;
        } else {
            intel = intel - 20 < minIntel ? minIntel: intel - 20;
        } 
    }

    /**
     * Increase the speed stat of the prisoner.
     * 
     * @param increase  The value to be increased by.
     */
    public void addSpeed(boolean increase) {
        if(increase) {
            spd = (spd * 10 + 1) / 10 > maxSpd ? maxSpd: (spd * 10 + 1) / 10;
        } else {
            spd = (spd * 10 - 1) / 10 < minSpd ? minSpd: (spd * 10 - 1) / 10;
        } 
    }
}
