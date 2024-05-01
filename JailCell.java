import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The jail cell room is where the main prisoners go to during lights out to craft their items and formulate an escape
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class JailCell extends Room
{
    /**
     * Constructor for JailCell.
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param dimensions            The width and height of the room.
     * @param index                 The index of the image.
     */
    public JailCell (int[] prisonerPosIndexes, int[] dimensions, int index) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
        setImage("JailCell"+index+".png");
        getImage().scale(dimensions[0],dimensions[1]);
    }
    
    /**
     * Do the effect of the JailCell room, crafting items.
     * 
     * @param p     The person being affected.
     */
    public void doEffect (Person p) {
        if(p.getIndex()==17){System.out.println("testguyinthecell");return;}//HERE FOR TEST
        if(!((MC)p).isDoneCrafting() && p.getActCount() % 240 == 0 && ((MC)p).craftItem()) {
            ((MC)p).setDoneCrafting(true);
            ((MyWorld)getWorld()).doEscape();
        }
    }
    
    /**
     * Return if a person is at its assigned room position node.
     * 
     * @param p         The person to be checked.
     * @return boolean  True if at the position, false if not.
     */
    public boolean checkEffectCondition (Person p) { 
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
