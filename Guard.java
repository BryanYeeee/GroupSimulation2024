import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Write a description of class Guard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guard extends Person
{
    public Guard(int i) {
        super(i);
        personType = "guard";
    }

    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(6);
        return result; 
    }
}
