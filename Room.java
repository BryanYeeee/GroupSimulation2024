import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Room here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Room extends Actor
{

    private Node[] prisonerPositions;
    private Node[] guardPositions;
    
    public abstract void doEffect(Person p);
    public abstract boolean checkEffectCondition(Person p);
    
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
    
    public void exitRoom (Person p, int roomPosition) {
        if (p instanceof Prisoner)
        prisonerPositions[roomPosition].setPerson(false);
        
        if (p instanceof Guard) 
        guardPositions[roomPosition].setPerson(false);
    }
    
    public int getPositionIndex(Person p, int roomPosition) {
        //System.out.println(p.getIndex());
        if (p instanceof Prisoner)
        return prisonerPositions[roomPosition].getIndex();
        
        if (p instanceof Guard) 
        return guardPositions[roomPosition].getIndex();
        
        return -1;
    }
    
    public void printPositions() {
        for(int i = 0; i< prisonerPositions.length; i++) {
            System.out.println(prisonerPositions[i].hasPerson());
        }
    }
}
