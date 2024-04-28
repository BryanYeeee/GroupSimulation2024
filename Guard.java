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
    // For cutscene/intro
    public Guard(int i, boolean inIntro){
        super(i, inIntro);
        GreenfootImage cutsceneImage = new GreenfootImage("images/guard/male_white/idle/D0.png");
        cutsceneImage.scale(120, 165);
        setImage(cutsceneImage);
        personType = "guard";
    }
    
    public Guard(int i) {
        super(i);
        personType = "guard";
    }
    public void act() {
        super.act();
        if(!inIntro){
            if(((MyWorld)getWorld()).isEscapeTime()) {
                if(curNode.getIndex() == STARTING_NODE_INDEX && !isMoving() && isWalkingAround) {
                    Action.walkAround(this, false);
                }
        
                if(curNode.getIndex() == WALKING_NODE_INDEX && !isMoving() && isWalkingAround) {
                    Action.walkAround(this, true);
                }
              
                // Fight intersecting MCs
                if (!inFight && !isDead) {
                    MC mc = (MC)getOneIntersectingObject(MC.class);
                    if (mc != null && !mc.isFighting() && !mc.isDead()) {
                        setInFight(mc, true);
                        mc.setInFight(this, true);
                        //curPath.clear();
                        noFights++;
                    }
                }
            }
        }
    }

    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(6);
        return result; 
    }
    
    public Accessory getAccessory(){
        return (Accessory) getOneIntersectingObject(Accessory.class);
    }
    
}
