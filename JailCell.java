import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RollCall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JailCell extends Room
{
    public JailCell (int[] prisonerPosIndexes, int[] dimensions, int index) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
        setImage("JailCell"+index+".png");
        getImage().scale(dimensions[0],dimensions[1]);
    }
    
    public void doEffect (Person p) {
        if(p.getIndex()==17){System.out.println("testguyinthecell");return;}//HERE FOR TEST
        if(!((MC)p).isDoneCrafting() && p.getActCount() % 240 == 0 && ((MC)p).craftItem()) {
            ((MC)p).setDoneCrafting(true);
            ((MyWorld)getWorld()).doEscape();
        }
    }
    
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
