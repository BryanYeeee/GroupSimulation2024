import greenfoot.*;
import java.util.ArrayList;
/**
 * The Sound class
 * 
 * @author Bryan Yee (with tips from Dawson Li)
 * @version March 2024
 */
public class Sound  
{
    private int soundIndex;
    private GreenfootSound[] sounds;
    private boolean isLoop;

    /**
     * Constructor for objects of class Sound.
     * Create many sounds in an array so they can be simultaneously called.
     * 
     * @param soundFile     The sound file's name.
     * @param volume        The volume of the sound.
     * @param loop          True if the sound will loop, false if the sound will be played only once.
     */
    public Sound(String soundFile, int volume, boolean loop)
    {
        soundIndex = 0;
        isLoop = loop;
        sounds = new GreenfootSound[10]; // Many sounds, so they can be simultaneously called
        for (int i = 0; i < sounds.length; i++) {
            sounds[i] = new GreenfootSound(soundFile);
            sounds[i].setVolume(volume);
            
            // The below was reccommended by Mr Cohen, but it was not helping the sound delays, so I removed it
            // sounds[i].play();
            // Greenfoot.delay(1);
            // sounds[i].stop();
        }
    }
    
    /**
     * Play a sound within the array of identical sounds, loop if required.
     */
    public void playSound() {
        if (isLoop) { // If the sound is a loop, then run playLoop instead of playing it once
             sounds[soundIndex].playLoop();
        } else {
            // Play current sound and iterate the sound index
            sounds[soundIndex].play();
            soundIndex = (soundIndex + 1) % sounds.length;
        }
    }
    
    /**
     * Pause the sound.
     */
    public void pauseSound(){
        sounds[soundIndex].pause();
    }
    
    /**
     * This method is called when the simulation is paused. It will retrieve a list of the active sounds and pause them.
     * 
     * @param pause                         True if the active sounds should be paused, false if not.
     * @return ArrayList<GreenfootSounds>   Sounds to be stored by the sound manager.
     */
    public ArrayList<GreenfootSound> getListOfPlayingSounds(boolean pause){
        ArrayList<GreenfootSound> soundsPlaying = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(sounds[i].isPlaying()) { // If sound is playing then add it to the list of sounds playing
                soundsPlaying.add(sounds[i]);
                if (pause) { // Pause the sound if requested to do so
                    sounds[i].pause();
                }
            }
        }
        return soundsPlaying;
    }
    
    /**
     * Return whether the sound should be played in a loop.
     * 
     * @return isLoop   True if sound should be played in loop, false if only once.
     */
    public boolean isLoop() {
        return isLoop;
    }
    
    /**
     * Completely stop the sound.
     */
    public void stopSound(){
        sounds[soundIndex].stop();
    }
    
}