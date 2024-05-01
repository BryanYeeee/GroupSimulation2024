import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The room superclass that contains all main simulation rooms.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public abstract class Room extends Actor
{

    private Node[] prisonerPositions;
    private Node[] guardPositions;
    
    /**
     * Each room will have a unique effect to be implemented.
     */
    public abstract void doEffect(Person p); 
    
    /**
     * The room will do this effect based on its own unique condition to be implemented.
     */
    public abstract boolean checkEffectCondition(Person p); 
    
    /**
     * Constructor for Room that initalizes indexes and dimensions.
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param guardPosIndexes       The indexes of guards for nodes.
     * @param dimensions            The width and height of the room.
     */
    public Room (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        prisonerPositions = new Node[prisonerPosIndexes.length];
        for (int i = 0; i < prisonerPosIndexes.length; i++) {
            prisonerPositions[i] = MyWorld.pf.getNode(prisonerPosIndexes[i]);
        }
        
        guardPositions = new Node[guardPosIndexes.length];
        for (int i = 0; i < guardPosIndexes.length; i++) {
            guardPositions[i] = MyWorld.pf.getNode(guardPosIndexes[i]);
        }
        getImage().scale(dimensions[0],dimensions[1]);
    }
    
    /**
     * The enterRoom method will assign a node for the person enterring to stand on
     * 
     * @param p The person enterring the room
     * @return The index of the assigned node
     */
    public int enterRoom (Person p) {
        if (p instanceof Guard) {
            for (int i = 0; i < guardPositions.length; i++) {
                if (!guardPositions[i].hasPerson()) {
                    p.goToNode(guardPositions[i].getIndex());
                    guardPositions[i].setPerson(true);
                    return i;
                }
            }
        } else if (p instanceof Prisoner) {
            for (int i = 0; i < prisonerPositions.length; i++) {
                if (!prisonerPositions[i].hasPerson()) {
                    p.goToNode(prisonerPositions[i].getIndex());
                    prisonerPositions[i].setPerson(true);
                    return i;
                }
            }
        }
        return -1; // Room full
    }
    
    /**
     * Let the room know that a person has left.
     * 
     * @param p             The person that left.
     * @param roomPosition  The node of a room's position.
     */
    public void exitRoom (Person p, int roomPosition) {
        if (p instanceof Prisoner)
        prisonerPositions[roomPosition].setPerson(false);
        
        if (p instanceof Guard) 
        guardPositions[roomPosition].setPerson(false);
    }
    
    /**
     * Return the position index of a person.
     * 
     * @param p             The person who's index is being returned.
     * @param roomPosition  The node of a room's position.
     * @return int          The position index, -1 if not available.
     */
    public int getPositionIndex(Person p, int roomPosition) {
        //System.out.println(p.getIndex());
        if (p instanceof Prisoner)
        return prisonerPositions[roomPosition].getIndex();
        
        if (p instanceof Guard) 
        return guardPositions[roomPosition].getIndex();
        
        return -1;
    }
    
    /**
     * Debugging method to print nodes/positions of prisoners.
     */
    public void printPositions() {
        for(int i = 0; i< prisonerPositions.length; i++) {
            System.out.println(prisonerPositions[i].hasPerson());
        }
    }
}
