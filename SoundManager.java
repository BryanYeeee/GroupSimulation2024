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
        
        /*
        sounds.put("bubbling", new Sound("bubbles.mp3",100,false));
        sounds.put("waves", new Sound("waves.mp3",75,true));
        sounds.put("chomp", new Sound("chomp.wav",75,false));
        sounds.put("scream", new Sound("scream.wav",65,false));
        sounds.put("bonk", new Sound("boink.mp3",65,false));
        sounds.put("inflate", new Sound("inflate.wav",75,false));
        sounds.put("shock", new Sound("tazer.mp3",65,false));
        sounds.put("storm", new Sound("storm.mp3",65,false));
        sounds.put("whale", new Sound("whale.mp3",55,false));
        */
        
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
