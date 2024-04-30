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
     * Prisoner constructor
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
    
    public SavedPrisoner(String serializedData) {
        deserializeState(serializedData);
        setMaxValues(specialty);
    }
    
    /**
     * Used as an image card for the status bar in the main simulation
     */
    public SavedPrisoner(boolean img, String specialty) {
        image = new GreenfootImage("images/"+specialty.replaceAll(" ","")+".png");
        image.scale(80, 80);
        setImage(image);
    }
    
    /**
     * Creates stat's increase and decrease buttons, and text to show stat
     * 
     * 
     * help from ChatGPT
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
     * 
     * help from ChatGPT
     */
    public String serializeState() {
        // Serialize the state into a string
        return name + "," + jobTitle + "," +str + "," + spd + "," + intel + "," + specialty;
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
        str = Integer.parseInt(parts[2]);
        spd = Double.parseDouble(parts[3]);
        intel = Integer.parseInt(parts[4]);
        specialty = parts[5];
        
        image = new GreenfootImage("images/"+specialty.replaceAll(" ","")+".png");
        image.scale(100,100);
        setImage(image);
    }
    
    public int getStrength() {
        return str;
    }
    public double getSpeed() {
        return spd;
    }
    public int getIntel() {
        return intel;
    }
    
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
    
    public void addStrength(boolean increase) {
        if(increase) {
            str = str + 1 > maxStr ? maxStr: str + 1;
        } else {
            str = str - 1 < minStr ? minStr: str - 1;
        } 
    }
    
    public void addIntel(boolean increase) {
        if(increase) {
            intel = intel + 20 > maxIntel ? maxIntel: intel + 20;
        } else {
            intel = intel - 20 < minIntel ? minIntel: intel - 20;
        } 
    }

    public void addSpeed(boolean increase) {
        if(increase) {
            spd = (spd * 10 + 1) / 10 > maxSpd ? maxSpd: (spd * 10 + 1) / 10;
        } else {
            spd = (spd * 10 - 1) / 10 < minSpd ? minSpd: (spd * 10 - 1) / 10;
        } 
    }
}
