import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Deque;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
/**
 * The Person superclass
 * 
 * @author Bryan Y , Animations by Jeff G
 * @version April 2024
 */
public abstract class Person extends Entity
{
    public final static int STARTING_NODE_INDEX = 0;
    public final static int WALKING_NODE_INDEX = 128;
    public final static int SPRITE_OFFSET = -16; //JEFF
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
    protected double opponentStrength;
    protected double opponentHealth;
    protected boolean inFight;
    protected static boolean inIntro = false;

    protected int maxHp;
    protected int curHp;
    protected int str;
    protected int intel;
    protected boolean isDead;
    protected SuperStatBar healthBar;

    //Animation Variables
    protected String personType = "inmate";
    protected String sex = "male";
    protected String skinTone;
    protected String action = "walk";
    protected char dirChar = 'D';
    private int imageIndex = 0;
    private int animationLength = 2;
    private int animationDelay = 7;
    protected int actCount;
    private Color[] colors = {new Color(255,64,56), new Color(255,231,22),new Color(34,121,227), new Color(45,247,38)};
    protected ArrayList<Integer> accessoryIndices; //JEFF
    
    protected SoundManager sm;
    // For cutscene/intro done by Jamison H
    /**
     * Constructor for Person used for cutscene/intro world
     */
    public Person(int i, boolean intro){
        this.index = i;
        inIntro = intro;
        roomPosition = -1;
        maxHp = Greenfoot.getRandomNumber(25)+75;
        curHp = Greenfoot.getRandomNumber(10)+maxHp-10;
        str = Greenfoot.getRandomNumber(5)+5;
        intel = 40;
    }

    /**
     * Constructor for Person
     */
    public Person(int i) {
        this.index = i;
        curNode = MyWorld.pf.getNode(STARTING_NODE_INDEX);
        roomPosition = -1;
        maxHp = Greenfoot.getRandomNumber(25)+75;
        curHp = Greenfoot.getRandomNumber(10)+maxHp-10;
        str = Greenfoot.getRandomNumber(5)+5;
        intel = 40;
        // Since all images are white, no need to roll for skin color unless NPC
        if(this instanceof MC){
            skinTone = "white";
        } else {
            if(Greenfoot.getRandomNumber(2)==0){
                skinTone = "black";
            }
            else{
                skinTone = "white";
            }
        }
    }

    /**
     * Set the location to the a random position on the starting node when added to the world
     */
    public void addedToWorld(World w) {
        if(isNew){
            
            // Both intro and simulation world need accessories
            accessoryIndices=getAccessories();
            if (accessoryIndices != null) {
                for (Integer i : accessoryIndices) {
                    getWorld().addObject(new Accessory(this, i), getX(), getY());
                }
            }

            if(!inIntro){
                setLocation(curNode.getX()+curNode.getOffset(false), curNode.getY() + SPRITE_OFFSET+curNode.getOffset(true));
                healthBar = new SuperStatBar(maxHp, curHp, this, 40, 6, 36, Color.GREEN, Color.RED, false, Color.BLACK, 2);
                if(this instanceof MC){
                    addUnderglow(colors[this.getIndex()]);
                }
            }
            isNew=false;
        }
    }

    /**
     * Act method for Person
     */
    public void act(){   
        actCount++;
        if(!inIntro){
            if(isDead) {
                if (this instanceof MC) ((MC)this).setAction("Reviving...");
                action="sleep"; 

                //Sets a random direction L or R if person is not horizontal
                if(dirChar != 'L' && dirChar != 'R'){
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        dirChar='L';
                    } else {
                        dirChar='R';
                    }
                }
                String key = personType + "_" + sex + "_" + skinTone + "_sleep_" + dirChar + "_1";
                GreenfootImage currentImage = Sprite.getFrame(key);
                currentImage.scale(48, 32);
                setImage(currentImage);

                if (actCount % (this instanceof MC ? 5: 15) == 0) {
                    healHp(1);
                    if (curHp == maxHp) setDead(false);
                }
                return;
            }
            if(inFight) {
                if (this instanceof MC) ((MC)this).setAction("In a Fight");
                action="attack";
                animationDelay=10;
                animate(); //Call animate before return 
                if (actCount % 30 == 0) {
                    //sm.playSound("Hit");
                    curHp -= opponentStrength;
                    opponentHealth -= str;
                    if(this instanceof MC) StatusBar.setUpdate(true);
                    if (opponentHealth <= 0 || curHp <= 0) {
                        if(curHp<=0) curHp = 0;
                        sm.stopSoundLoop("Fighting");
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

            Room r = (Room)getOneObjectAtOffset(0,-SPRITE_OFFSET,Room.class);

            if (!curPath.isEmpty()) {
                action ="walk";
                animationDelay = 7;
                move();
            }

            //Idle is slower so longer animationDelay
            
            // If currently escaping, don't do additional effects, like rooms or free roam
            if(this instanceof MC && ((MyWorld)getWorld()).isEscapeTime()) return;
            if(this instanceof Guard && ((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("LIGHTS OUT")) return;

            if(curPath.isEmpty()) {
                if(r instanceof DiningHall && personType.equals("inmate") && getY()>190){
                    action = "eat";
                    animationDelay = 20;
                }
                else if(r instanceof Gym && personType.equals( "inmate") && (getX()>900)&&(getY()<600)){
                    action = "walk";
                    animationDelay = 7;
                } else {
                    action ="idle";
                    animationDelay = 50;
                }
            }

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

        }

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
    
    public void doCurrentEvent() {
        if(this instanceof MC && ((MyWorld)getWorld()).isEscapeTime()) return;
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
    
    /**
     * Returns the chance that a Person gets an item
     * 
     * @return                          Chance of obtaining an item
     */
    public int getChance() {
        if (intel < 20) {
            return 6;
        } else if (intel < 40) {
            return 4;
        } else if (intel < 60) {
            return 3;
        } else if (intel < 80) {
            return 2;
        }
        return 1;
    }

    /**
     * Checks if the Person is currently dead or alive 
     * 
     * @param isDead                    Checks if Person is dead or alive
     */
    public void setDead(boolean isDead) {
        this.isDead = isDead;
        if (isDead) { // If dead, then keep the healthBar visible
            if(healthBar.getWorld() == null) getWorld().addObject(healthBar, 0, 0);
        } else { // If alive then hide the healthbar, and return to the current event's actions
            getWorld().removeObject(healthBar);
            doCurrentEvent();
        }
    }

    /**
     * Horizontal animations
     */
    public void makeHorizontal(){
        if(dirChar != 'L' && dirChar != 'R'){
            if (Greenfoot.getRandomNumber(2) == 0) {
                dirChar='L';
            } else {
                dirChar='R';
            }
        }
    }

    /**
     * Puts two Person's in a fight
     * 
     * @param opponent                      Opponent of the fighters
     * @param inFight                       Set them in a fight
     */
    public void setInFight(Person opponent, boolean inFight) {
        this.inFight = inFight;
        if (inFight) { // If starting fight then show healthBar and store the opponent's health and strength
            sm.playSound("Fighting");
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
            doCurrentEvent();
        }
        //System.out.println("FIGHT: " +onGoingFights);
    }

    /**
     * Fades the object in or out of the world
     * 
     * @param timeLeft              Time left for fade to complete
     * @param totalFadeTime         Total time for fade duration
     */
    protected void fade (int timeLeft, int totalFadeTime){
        double percent = timeLeft / (double)totalFadeTime;
        if (percent > 1.00) return;
        int newTranparency = (int)(percent * 255);
        getImage().setTransparency (newTranparency);
    }

    /**
     * Returns the index of the Person in the world
     * 
     * @return index                Gets the index of Person
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns Person items
     * 
     * @return result                   Gets Person items
     */
    protected ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);
        return result;
    }

    /**
     * Checks if Person is walking around 
     * 
     * @param walking                   Checks if Person is walking
     */
    public void setWalking(boolean walking) {
        isWalkingAround = walking;
    }

    /**
     * Returns if Person is walking around
     * 
     * @return isWalkingAround          Checks if Person is walking
     */
    public boolean isWalking() {
        return isWalkingAround;
    }

    /**
     * Adds an underglow to Person
     * 
     * @param color                     Color of the underglow
     */
    public void addUnderglow(Color color){
        getWorld().addObject(new Underglow(this, color), getX(), getY());
    }

    /**
     * Sets the action Person follows
     * 
     * @param action                    Sets action of Person
     */
    public void setAction(String action){
        this.action=action;
    }

    /**
     * Animations
     */
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

    /**
     * Shows the healthbar of Person
     * 
     * @param show                      Display healthbar or not 
     */
    public void showHealthBar(boolean show) {
        if(show && healthBar.getWorld() == null) {
            getWorld().addObject(healthBar, 0, 0);
        } else if (!show){
            getWorld().removeObject(healthBar);
        }
    }

    /**
     * Method used to heal Person's HP
     * 
     * @param healAmount                    Sets the amount which Person heals by
     */
    public void healHp(int healAmount){
        curHp+=healAmount;
        if (curHp > maxHp) curHp = maxHp;
        healthBar.update(curHp);
        if(this instanceof MC)  StatusBar.setUpdate(true);
    }

    /**
     * Adds intel to Person
     * 
     * @param intelAmount                   Increase intel by an amount
     */
    public void addIntel(int intelAmount) {
        intel+=intelAmount;
        if(this instanceof MC)  StatusBar.setUpdate(true);
    }

    /**
     * Adds strength to Person
     * 
     * @param strengthAmount                Increase strength by an amount
     */
    public void addStrength(int strengthAmount) {
        if(this instanceof MC && ((MC)this).getSpecialty().equals("Brute"))strengthAmount++;
        str+=strengthAmount;
        if(this instanceof MC)  StatusBar.setUpdate(true);
    }

    /**
     * Sets strength value
     * 
     * @param s                             Assigns strength a value
     */
    public void setStrength(int s) {
        str = s;
    }
    
    /**
     * Returns speed
     * 
     * @return speed                        Speed of Person
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * Sets the speed of Person
     * 
     * @param speed                         Sets speed of Person
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Sets the room position of Person in it
     * 
     * @param roomPosition                  Position in Person's room
     */
    public void setRoomPosition(int roomPosition) {
        this.roomPosition = roomPosition;
    }

    /**
     * Sets the direction Person will face
     * 
     * @param dir                           Direction of Person
     */
    public void setDirection(int dir) {
        this.dir = dir;
    }

    /**
     * Checks if Person is moving vertically
     * 
     * @param movingVertical                Moving vertically or horizontally
     */
    public void setMovingVertical(boolean movingVertical) {
        this.movingVertical = movingVertical;
    }

    /**
     * Is Person fighting
     * 
     * @return inFight                      Currently in a fight
     */
    public boolean isFighting() {
        return inFight;
    }

    /**
     * Clears paths Person is taking
     */
    public void clearPath() {
        curPath.clear();
    }

    /**
     * Is Person moving
     * 
     * @return !curPath.isEmpty()           Person is moving if path is not empty
     */
    public boolean isMoving() {
        return !curPath.isEmpty();
    }

    /**
     * Adds the next node Person has to walk to
     * 
     * @param nextNode                      Next node to walk to
     */
    public void addToPath(Node nextNode) {
        curPath.add(nextNode);
    }

    /**
     * Returns if Person is dead
     * 
     * @return isDead                       Person is dead or alive
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets Person in intro or not in intro
     * 
     * @param intro                         Person is in or not in intro
     */
    public static void setIntro(boolean intro){
        inIntro = intro;
    }

    /**
     * Returns act
     * 
     * @return actCount                     Current acts
     */
    public int getActCount() {
        return actCount;
    }

    /**
     * Returns maxHP
     * 
     * @return maxHp                        MaxHp of Person
     */
    public int getMaxHealth() {
        return maxHp;
    }

    /**
     * Returns the current health of Person
     * 
     * @return curHp                        Current Hp of Person
     */
    public int getHealth() {
        return curHp;
    }

    /**
     * Returns the strength of Person
     * 
     * @return str                          Strength of Person
     */
    public int getStrength() {
        return str;
    }

    /**
     * Returns the intel of Person
     * 
     * @return intel                        Intelligence of Person
     */
    public int getIntel(){
        return intel;
    }

    /**
     * Returns the node Person is on
     * 
     * @return currNode                     Current node
     */
    public Node getCurNode() {
        return curNode;
    }

    /**
     * Returns Person's current room
     * 
     * @return curRoom                      Current Room
     */
    public Room getCurRoom() {
        return curRoom;
    }

    /**
     * Return Person's position in the room
     * 
     * @return roomPosition                 Room position
     */
    public int getRoomPosition() {
        return roomPosition;
    }

    /**
     * Returns the direction Person is currently facing
     * 
     * @return dirChar                      Direction Person is facing
     */
    public char getDirChar(){
        return dirChar;
    }

    /**
     * Returns the current action Person is performing
     * 
     * @return action                       Action of Person
     */
    public String getAction(){
        return action;
    }

    //JEFF ADDED
    /**
     * Animation for walking and idle
     * 
     * @return imageIndex                     Index of animation  
     */
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
