import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class Sprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sprite  
{
    // instance variables - replace the example below with your own

    private static String personType = "inmate";
    private static String sex = "male";
    private static String skinTone;
    private static String action = "walk";
    private static char dirChar = 'D';
    private static int imageIndex = 0;
    private static Map<String, GreenfootImage> frames = new HashMap<>();

    public Sprite()
    {

    }

    public static void init(){
        for(int i = 0; i < 2; i++){
            if(i==0){
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
                for(int imageIndex = 0; imageIndex < 2; imageIndex++){
                    action = "idle";
                    String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                    frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                }
                for(int imageIndex = 0; imageIndex < 12; imageIndex++){
                    action = "walk";
                    String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
                    frames.put(key, new GreenfootImage("images/" + personType + "/" + sex + "_" + skinTone + "/" + action + "/" + dirChar + imageIndex + ".png"));
                }
            }
        }
    }

    public static GreenfootImage getFrame(String key){
        if (frames.containsKey(key)) {
            return frames.get(key);
        } else {
            System.out.println("Key not found in the map: "+ key);
            return frames.get("inmate_black_idle_D_0");
        }

    }

}
