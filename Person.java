import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Deque;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
/**
 * The Person superclass
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public abstract class Person extends Entity
{
    public final static int STARTING_NODE_INDEX = 0;
    public final static int WALKING_NODE_INDEX = 128;
    protected final static int SPRITE_OFFSET = -16; //JEFF
    protected final static int MAX_FIGHTS = 4;
    protected int index;
    protected Deque<Node> curPath = new LinkedList<Node>();
    protected Node curNode;
    public int offsetPos; // Randomized offset between the characters position and node position.
    protected Room curRoom;
    protected int roomPosition; // Index of the assigned node position based on the array of room positions in curRoom
    protected boolean isNew = true;
    // Movement Variables
    protected boolean movingVertical;
    protected int dir; // Direction, 1 = Right/Down, -1 = Left/Up, horizontal and vertical is stored in movingVertical
    protected double speed = 1.8;
    protected boolean isWalkingAround;

    public static int onGoingFights = 0;
    public static int noFights = 0;
    protected int opponentStrength;
    protected int opponentHealth;
    protected int opponentX;
    protected int opponentY;
    protected boolean inFight;

    // Person Stats
    protected int maxHp;
    protected int curHp;
    protected int strength;
    protected int intel;
    protected int luck;
    protected boolean isDead;
    protected SuperStatBar healthBar;

    //Animation Variables
    protected String personType = "inmate";
    private String sex = "male";
    private String skinTone;
    private String action = "walk";
    private char dirChar = 'D';
    private int imageIndex = 0;
    private int animationLength = 2;
    private int animationDelay = 7;
    protected int actCount;

    protected ArrayList<Integer> accessoryIndices; //JEFF
    public Person(int i) {
        this.index = i;
        curNode = MyWorld.pf.getNode(STARTING_NODE_INDEX);
        roomPosition = -1;
        maxHp = Greenfoot.getRandomNumber(25)+75;
        curHp = Greenfoot.getRandomNumber(10)+maxHp-10;
        strength = Greenfoot.getRandomNumber(5)+5;
        luck = Greenfoot.getRandomNumber(4) + 1;
        intel = 40;
        if(Greenfoot.getRandomNumber(2)==0){
            skinTone = "black";
        }
        else{
            skinTone = "white";
        }
    }

    /**
     * Set the location to the a random position on the starting node when added to the world
     */
    public void addedToWorld(World w) {
        if(isNew){
            setLocation(curNode.getX()+curNode.getOffset(false), curNode.getY() + SPRITE_OFFSET+curNode.getOffset(true));
            accessoryIndices=getAccessories();
            if (accessoryIndices != null) {
                for (Integer i : accessoryIndices) {
                    getWorld().addObject(new Accessory(this, i), getX(), getY());
                }
            }

            healthBar = new SuperStatBar(maxHp, curHp, this, 40, 6, 36, Color.GREEN, Color.RED, false, Color.BLACK, 2);
            if(this instanceof MC){
                addUnderglow();
            }
            isNew=false;

        }
    }

    public void act()
    {   
        actCount++;
        if(isDead) {
            action="sleep"; 

            //Sets a random direction L or R if person is not horizontal
            makeHorizontal();

            String key = personType + "_" + sex + "_" + skinTone + "_sleep_" + dirChar + "_1";
            GreenfootImage currentImage = Sprite.getFrame(key);
            currentImage.scale(48, 32);
            setImage(currentImage);

            if (actCount % 15 == 0) {
                healHp(1);
                if (curHp == maxHp) setDead(false);
            }
            return;
        }

        if(inFight) {
            action="attack";
            animationDelay=10;
            animate(); //Call animate before return 
            if (actCount % 30 == 0) {
                curHp -= opponentStrength;
                opponentHealth -= strength;
                if (opponentHealth <= 0 || curHp <= 0) {
                    if(curHp<=0) curHp = 0;
                    setInFight(this, false);
                    setDead(curHp <= 0);
                }
                healthBar.update(curHp);
            }
            return;
        }

        if(!inFight && !isDead){
            animate();
        }

        // If currently escaping, don't do additional effects, like rooms or free roam
        if(((MyWorld)getWorld()).isEscapeTime()) return;

        Room r = (Room)getOneObjectAtOffset(0,-SPRITE_OFFSET,Room.class);
        if (curRoom != r) { // Changed current room
            if (curRoom != null) { // Leaving a room
                curRoom.exitRoom(this, roomPosition);
                curRoom = null;
                roomPosition = -1;
            } else if(curPath.isEmpty()) { // Landed in a room
                curRoom = r;
                roomPosition = curRoom.enterRoom(this);
                //if(((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("DINING HALL"))System.out.println(roomPosition);
            }
        }

        // If in room, and it has an effect that can occur, do effect
        if (curRoom != null && curRoom.checkEffectCondition(this)) {
            curRoom.doEffect(this);
        }

        if(curNode.getIndex() == STARTING_NODE_INDEX && !isMoving() && isWalkingAround) {
            Action.walkAround(this, false);
        }

        if(curNode.getIndex() == WALKING_NODE_INDEX && !isMoving() && isWalkingAround) {
            Action.walkAround(this, true);
        }

        if(curNode.getIndex() == STARTING_NODE_INDEX && ((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("LIGHTS OUT")) {
            //getWorld().removeObject(this);
            curPath.clear();
            speed = 0;
        }

        if (!curPath.isEmpty()) {
            action ="walk";
            animationDelay = 7;
            move();
        } else{
            if(r instanceof DiningHall){
                if(personType.equals( "inmate")){
                    action = "eat";
                    animationDelay = 20;
                }
                else{
                    action="idle";
                    animationDelay = 50;
                }
            }
            else if(r instanceof Gym){
                if(personType.equals( "inmate")){
                    if((getX()>900)&&(getY()<600)){
                        action = "walk";
                        animationDelay = 7;
                    }
                    else{
                        action="idle";
                        animationDelay = 50;
                    }
                }
                else{
                    action="idle";
                    animationDelay = 50;
                }
            }
            else{
                action ="idle";
                animationDelay = 50;
            }
        }

        //Idle is slower so longer animationDelay
    }

    /**
     * Use PathFinder to add the nodes required to get from one node to another, into curPath
     */
    public void goToNode(int nodeIndex) {
        // if(isWalkingAround && !(((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("FREE TIME") || ((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("JOB TIME"))) {

        // System.out.println(((MyWorld)getWorld()).getSchedule().getCurrentEvent());curPath.clear();
        // isWalkingAround = false;
        // }
        if(MyWorld.pf.getNode(nodeIndex).hasPerson() || inFight || isDead) return; // If spot is already occupied or person is occupied
        if(!curPath.isEmpty()) { // If not starting from stationary, calculate the path starting from the last node in curPath
            MyWorld.pf.goToNode(this,curPath.peekLast(), nodeIndex);
        } else {
            MyWorld.pf.goToNode(this, curNode, nodeIndex);
            nextNodeInPath();
        }
    }

    /**
     * Set the person's location based on the next node in curPath
     */
    public void move() { 
        // Get the target position (next position in curPath), including offsetPos
        int nextX = curPath.peek().getX() + offsetPos, nextY = curPath.peek().getY() + offsetPos;
        // These booleans will check if the person has reached the target node position based on the direction the person is moving
        boolean reachedDestY = dir==1? (nextY <= getY() - SPRITE_OFFSET) : (nextY >= getY() - SPRITE_OFFSET);
        boolean reachedDestX = dir==1? (nextX <= getX()) : (nextX >= getX());
        if ((movingVertical && reachedDestY) || (!movingVertical && reachedDestX)) { // Arrived at target position
            //System.out.println(nextY + " " + (getY()-spriteOffset));
            setLocation(movingVertical ? getX() : nextX, movingVertical ? nextY + SPRITE_OFFSET: getY()); // Snap to target node position
            curNode = curPath.poll(); // Set curNode to the new node
            nextNodeInPath(); // Go to next node in curPath
        } else {
            // Change y position if movingVertical is true, change x position if movingVertical is false
            setLocation(movingVertical ? getX() : getX()+speed*dir, movingVertical ? getY()+speed*dir : getY());
        }
    }

    /**
     * This method will set the direction, offset, and axis for the person based on the next node in curPath
     */
    public void nextNodeInPath() {
        if (curPath.isEmpty()) return;
        int axis = curPath.peek().getAxis(getX(), getY() - SPRITE_OFFSET); // Which axis needed to travel in order to get to the next node (vertical, horizontal)

        if (axis == 0) { // If current position is diagonal to the next node, then adjust position to align with the next node
            //DEBUG: System.out.println("diagonal > adjusting");
            axis = curPath.peek().getAxis(curNode.getX(), curNode.getY());
            movingVertical = axis != 1; // If next node is vertical from the current node, adjust horizontally, and vice versa
            //System.out.println(index + " " + curPath.peek().getIndex() + " " + curNode.getIndex() + " "+movingVertical+" AXIS");
            offsetPos = curNode.getJoinOffset(movingVertical, curPath.peek());
            //System.out.println("                 "+offsetPos);

            curPath.push(curNode); // Go to the curNode with adjusted offset
            if(axis==0){int x = 1/0;System.out.println("deadedadeadaded");} // Shouldnt happen, here just in case for now
        } else {
            movingVertical = axis == 1;
            offsetPos = curPath.peek().getOffset(movingVertical);
            //System.out.println(index + " " + offsetPos + " " + curPath.peek().getIndex() + " " + curNode.getIndex() + "");
        }
        dir = curPath.peek().getDirection(movingVertical, getX(), getY() - SPRITE_OFFSET);
        //DEBUG: System.out.println(offsetPos);
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
        if (isDead) { // If dead, then keep the healthBar visible
            if(healthBar.getWorld() == null) getWorld().addObject(healthBar, 0, 0);
        } else { // If alive then hide the healthbar, and return to the current event's actions
            getWorld().removeObject(healthBar);
            //if(isWalkingAround) return;
            Node nextNode = curPath.peek();
            boolean nextAxis = movingVertical;
            int nextOffset = offsetPos;
            int nextDir = dir;
            curPath.clear();
            if(nextNode!=null) curPath.push(nextNode);
            ((MyWorld)getWorld()).getSchedule().doCurrentEvent(this);
            if(nextNode!=null) {
                movingVertical = nextAxis;
                offsetPos = nextOffset;
                dir = nextDir;
            }

        }
    }

    public void makeHorizontal(){
        if(dirChar != 'L' && dirChar != 'R'){
            if (Greenfoot.getRandomNumber(2) == 0) {
                dirChar='L';
            } else {
                dirChar='R';
            }
        }
    }

    public void setInFight(Person opponent, boolean inFight) {
        this.inFight = inFight;
        if (inFight) { // If starting fight then show healthBar and store the opponent's health and strength
            onGoingFights++;
            opponentHealth = opponent.getHealth();
            opponentStrength = opponent.getStrength();
            getWorld().addObject(healthBar, 0, 0);

            //When fighting, set dirChar to face the opponent
            int dx = opponent.getX() - getX();
            int dy = opponent.getY() - getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    dirChar = 'R'; 
                } else {
                    dirChar = 'L'; 
                }
            } else {
                if (dy > 0) {
                    dirChar = 'D'; 
                } else {
                    dirChar = 'U'; 
                }
            }
        } else { // If ending a fight, then hide the healthBar and reset opponent's data
            onGoingFights--;
            opponentHealth = 0;
            opponentStrength = 0;
            getWorld().removeObject(healthBar);
            Node nextNode = curPath.peek();
            boolean nextAxis = movingVertical;
            int nextOffset = offsetPos;
            int nextDir = dir;
            curPath.clear();
            if(nextNode!=null) curPath.push(nextNode);
            ((MyWorld)getWorld()).getSchedule().doCurrentEvent(this);
            if(nextNode!=null) {
                movingVertical = nextAxis;
                offsetPos = nextOffset;
                dir = nextDir;
            }
        }
        //System.out.println("FIGHT: " +onGoingFights);
    }

    public int getIndex() {
        return index;
    }

    protected ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);
        return result;
    }

    public void setWalking(boolean walking) {
        isWalkingAround = walking;
    }

    public boolean isWalking() {
        return isWalkingAround;
    }

    public void addUnderglow(){
        getWorld().addObject(new Underglow(this), getX(), getY());
    }

    public void setAction(String action){
        this.action=action;
    }

    public void animate() {
        //frames only change at intervals of animationDelay
        if (actCount % animationDelay != 0) {
            return;
        }

        //Preset animationLengths per action, and delay
        if (action.equals("walk")) {
            animationDelay = 7;
            animationLength = 12;
        } else if (action.equals("idle")) {
            animationDelay = 50;
            animationLength = 2;
        }
        else if(action.equals("attack")){
            animationLength = 4;
        }
        else if(action.equals("eat")){
            animationLength = 4;
            if(getX()<134||(195<getX()&&getX()<256)){
                dirChar = 'R';
            }
            else{
                dirChar = 'L';
            }
        }

        //Attacking does not mean they are facing the direction of movement
        if(!action.equals("attack")&&!action.equals("eat")){
            if (dir == 1 && movingVertical) {
                dirChar = 'D';
            } else if (dir == -1 && movingVertical) {
                dirChar = 'U';
            } else if (dir == 1 && !movingVertical) {
                dirChar = 'R';
            } else if (dir == -1 && !movingVertical) {
                dirChar = 'L';
            }
            else{
                dirChar = 'D';
            }

        }
        imageIndex = (imageIndex + 1) % animationLength;
        String key = personType + "_" + sex + "_" + skinTone + "_" + action + "_" + dirChar + "_" + imageIndex;
        GreenfootImage currentImage = Sprite.getFrame(key);

        try {
            currentImage.scale(32, 48);
        }
        catch (NullPointerException e) {
            System.out.println(key);
        }

        setImage(currentImage);
    }

    public void showHealthBar(boolean show) {
        if(show && healthBar.getWorld() == null) {
            getWorld().addObject(healthBar, 0, 0);
        } else if (!show){
            getWorld().removeObject(healthBar);
        }
    }

    public void healHp(int healAmount){
        curHp+=healAmount;
        if (curHp > maxHp) curHp = maxHp;
        healthBar.update(curHp);
        if(this instanceof MC)  StatusBar.setUpdate(true);
    }

    public void addIntel(int intelAmount) {
        intel+=intelAmount;
        if(this instanceof MC)  StatusBar.setUpdate(true);
    }

    public void addStrength(int strengthAmount) {
        if(this instanceof MC && ((MC)this).getSpecialty().equals("Brute") && actCount % 720 == 0)strengthAmount++;
        strength+=strengthAmount;
        if(this instanceof MC)  StatusBar.setUpdate(true);
    }

    public void setStrength(int s) {
        strength = s;
    }

    public void setRoomPosition(int roomPosition) {
        this.roomPosition = roomPosition;
    }

    public void setDirection(int dir) {
        this.dir = dir;
    }

    public void setMovingVertical(boolean movingVertical) {
        this.movingVertical = movingVertical;
    }

    public boolean isFighting() {
        return inFight;
    }

    public void clearPath() {
        curPath.clear();
    }

    public boolean isMoving() {
        return !curPath.isEmpty();
    }

    public void addToPath(Node nextNode) {
        curPath.add(nextNode);
    }

    public boolean isDead() {
        return isDead;
    }

    public int getActCount() {
        return actCount;
    }

    public int getMaxHealth() {
        return maxHp;
    }

    public int getHealth() {
        return curHp;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntel(){
        return intel;
    }

    public int getLuck(){
        return luck;
    }

    public Node getCurNode() {
        return curNode;
    }

    public Room getCurRoom() {
        return curRoom;
    }

    public int getRoomPosition() {
        return roomPosition;
    }

    public char getDirChar(){
        return dirChar;
    }

    public String getAction(){
        return action;
    }

    //JEFF ADDED
    public int getImageIndex(){
        if (action.equals("walk")) {
            animationLength = 12;
        } else if (action.equals("idle")) {
            animationLength = 2;
        }
        imageIndex = imageIndex % animationLength;
        return imageIndex;
    }
}
