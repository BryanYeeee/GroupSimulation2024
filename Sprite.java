import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.Map;

/**
 * The sprite class is in charge of initializing the prisoner and guard sprites
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Sprite  
{

    private static String personType = "inmate";
    private static String sex = "male";
    private static String skinTone;
    private static String action = "walk";
    private static char dirChar = 'D';
    private static int imageIndex = 0;
    private static Map<String, GreenfootImage> frames = new HashMap<>();
    
    /**
     * Initalize the map with all frames depending on person type, skin tone, direction and action.
     */
    public static void init(){
        for(int i = 0; i<2; i++){
            if(i==0){
                personType = "guard";   
            }
            else{
                personType = "inmate";
            } 

            for(int j = 0; j < 2; j++){
                if(j==0){
                    skinTone = "black";   
                }
                else{
                    skinTone = "white";
                }
                for(int dirIndex = 0; dirIndex < 4; dirIndex++){ 
                    if(dirIndex == 0){
                        dirChar = 'D';
                    }
                    else if(dirIndex == 1){
                        dirChar = 'L';
                    }
                    else if(dirIndex == 2){
                        dirChar = 'R';
                    }
                    else if(dirIndex == 3){
                        dirChar = 'U';
                    }
                    for(int imageIndex = 0; imageIndex < 3; imageIndex++){
                        action = "idle";
                        String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                        frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                    }
                    for(int imageIndex = 0; imageIndex < 12; imageIndex++){
                        action = "walk";
                        String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                        frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                    }     
                    for(int imageIndex = 0; imageIndex < 4; imageIndex++){
                        action = "attack";
                        String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                        frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                    }
                    if(dirChar == 'L' || dirChar == 'R'){
                        for(int imageIndex = 0; imageIndex < 2; imageIndex++){
                            action="sleep";
                            String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                            frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                        }

                    }
                }
            }

        }
        personType="inmate";
        for(int j = 0; j < 2; j++){
            if(j==0){
                skinTone = "black";   
            }
            else{
                skinTone = "white";
            }
            for(int dirIndex = 0; dirIndex < 4; dirIndex++){ 
                if(dirIndex == 0){
                    dirChar = 'D';
                }
                else if(dirIndex == 1){
                    dirChar = 'L';
                }
                else if(dirIndex == 2){
                    dirChar = 'R';
                }
                else if(dirIndex == 3){
                    dirChar = 'U';
                }
                if(dirChar == 'L' || dirChar == 'R'){
                    for(int imageIndex = 0; imageIndex < 4; imageIndex++){
                        action="eat";
                        String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                        System.out.println(key);
                        frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                    }

                }
            }
        }
    }

    /**
     * Return a GreenfootImage of a frame of an animation. If key is not found, return a default black inmate idle frame.
     * 
     * @param key               The key to access the frame within the map.
     * @return GreenfootImage   The frame of an animation.
     */
    public static GreenfootImage getFrame(String key){
        if (frames.containsKey(key)) {
            return frames.get(key);
        } else {
            System.out.println("Key not found in the map: "+ key);
            return frames.get("inmate_black_idle_D_0");
        }

    }

}
