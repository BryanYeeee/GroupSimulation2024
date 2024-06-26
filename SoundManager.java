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
     * Constructor for objects of class SoundManager.
     * Initalize all sounds into a map of names and respective sounds.
     */
    public static void initSounds()
    {
        activeSounds = new ArrayList<GreenfootSound>();
        
        // Map all the sounds to an array to access sound class by name
        sounds = new HashMap<String, Sound>();
        
        // Non-looped
        sounds.put("click", new Sound("click.mp3", 35, false));
        sounds.put("bomb", new Sound("bomb.mp3", 55, false));
        sounds.put("GeneratorOff", new Sound("GeneratorOff.mp3", 35, false));
        sounds.put("WoodBurning", new Sound("WoodBurning.mp3", 25, false));
        sounds.put("WallBreak", new Sound("WallBreak.mp3", 28, false));
        sounds.put("VictoryEscape", new Sound("VictoryEscape.mp3", 30, false));
        sounds.put("MetalCraft", new Sound("MetalCraft.mp3", 24, false)); //could be looped
        sounds.put("DoorOpen", new Sound("DoorOpen.mp3", 35, false));
        sounds.put("WoodCraft", new Sound("WoodCraft.mp3", 43, false)); // could be looped
        sounds.put("Fighting", new Sound("Fighting.mp3", 31, true));
        sounds.put("RollCall", new Sound("RollCall.mp3", 21, false));
        sounds.put("CarStart", new Sound("CarStart.mp3", 50, false));
        sounds.put("PickUp", new Sound("PickUp.mp3", 42, false));
        
        // Looped
        sounds.put("Statscreen", new Sound("Statscreen.mp3", 25, true));
        sounds.put("Titlescreen", new Sound("Titlescreen.mp3", 25, true));
        sounds.put("MainEscape", new Sound("MainEscape.mp3", 25, true));
        sounds.put("LightsOut", new Sound("LightsOut.mp3", 25, true));
        sounds.put("Fighting", new Sound("Fighting.mp3", 31, true));
        sounds.put("ShovelDirt", new Sound("ShovelDirt.mp3", 43, true));
        sounds.put("CutFence", new Sound("CutFence.mp3", 33, true));
        
    }
    
    /**
     * Method to play a sound or a sound loop.
     */
    public static void playSound(String sound) {
        sounds.get(sound).playSound();
    }
    
    /**
     * Method to pause all the currently playing sounds.
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
     * Method to resume all the currently paused sounds.
     */
    public static void resumeSounds(){
        // Loop through the list of active sounds and resume them
        for(int i = 0; i < activeSounds.size(); i++){
            activeSounds.get(i).play();
        }
        activeSounds.clear(); // Clear the arraylist
    }
    
    /**
     * Stop the looping of a certain sound.
     * 
     * @param sound     The sound to be stopped.
     */
    public static void stopSoundLoop(String sound) {
        sounds.get(sound).stopSound();
    }
    
}
