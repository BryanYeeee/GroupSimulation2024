import greenfoot.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * SoundManager is a class that stores and controls the sound files.
 * 
 * @author Bryan Yee (with tips from Dawson Li)
 * @version March 2024
 */
public class SoundManager  
{
    public static HashMap<String, Sound> sounds;
    private static ArrayList<GreenfootSound> activeSounds;
    
    /**
     * Constructor for objects of class SoundManager
     */
    public static void initSounds()
    {
        activeSounds = new ArrayList<GreenfootSound>();
        
        // Map all the sounds to an array to access sound class by name
        sounds = new HashMap<String, Sound>();
        
        // Non-looped
        sounds.put("click", new Sound("click.mp3", 35, false));
        sounds.put("bomb", new Sound("bomb.mp3", 35, false));
        sounds.put("GeneratorOff", new Sound("GeneratorOff.mp3", 35, false));
        sounds.put("WoodBurning", new Sound("WoodBurning.mp3", 35, false));
        sounds.put("WallBreak", new Sound("WallBreak.mp3", 35, false));
        sounds.put("VictoryEscape", new Sound("VictoryEscape.mp3", 30, false));
        sounds.put("MetalCraft", new Sound("MetalCraft.mp3", 35, false));
        sounds.put("DoorOpen", new Sound("DoorOpen.mp3", 35, false));
        sounds.put("WoodCraft", new Sound("WoodCraft.mp3", 30, false));
        sounds.put("Fighting", new Sound("Fighting.mp3", 35, false));
        
        // Looped
        sounds.put("Statscreen", new Sound("Statscreen.mp3", 25, true));
        sounds.put("Titlescreen", new Sound("Titlescreen.mp3", 25, false));
        
        
    }
    
    /**
     * Method to play a sound or a sound loop
     */
    public static void playSound(String sound) {
        sounds.get(sound).playSound();
    }
    
    /**
     * Method to pause all the currently playing sounds
     */
    public static void pauseSounds() {
        // Loop through the sound types
        for(Map.Entry<String, Sound> set: sounds.entrySet()){
            // Retrieve a list of the sounds playing for the current sound type
            // Since the parameter in the following method is true, it will also pause the sounds for each one currently playing
            ArrayList<GreenfootSound> soundsPlaying = set.getValue().getListOfPlayingSounds(true);
            
            // Store the paused sounds in the active sounds arraylist
            activeSounds.addAll(soundsPlaying);
        }
    }
    
    /**
     * Method to resume all the currently paused sounds
     */
    public static void resumeSounds(){
        // Loop through the list of active sounds and resume them
        for(int i = 0; i < activeSounds.size(); i++){
            activeSounds.get(i).play();
        }
        activeSounds.clear(); // Clear the arraylist
    }
    
    
    
}
