import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Item super class.
 * 
 * @author Jeff G
 * @version April 2024
 */
public abstract class Item extends SuperSmoothMover
{
    protected int actCount=0;
    protected int originalSize = 32;
    protected int size = originalSize;
    protected int pickupActs;
    protected boolean collecting = false;
    protected int slotX;
    protected int slotY;
    protected double deltaY;
    protected double hoverSpeed=0.51;
    protected double speed=0;
    protected boolean isMaterial;
    protected String itemName;
    protected GreenfootImage image;
    protected MC origin;
    
    private SoundManager sm;
    /**
     * Constructor for Item
     * 
     * @param itemName  The item name, used for finding its file.
     */
    public Item(String itemName){
        this.itemName = itemName;
        try {
            image = new GreenfootImage("images/items/"+itemName+".png");

            setImage(image);
        }catch(Exception e) {
            System.out.println("image not found: "+"images/items/"+itemName+".png");
        }
        setStaticRotation(true);
        pickupActs=-1;
    }
    
    /**
     * If an MC touches the item, hover the item, then get sent to the status bar.
     */
    public void act()
    {
        if(pickupActs==-1){
            if (isTouching(MC.class)) {
                MC touchingMC = (MC) getOneIntersectingObject(MC.class);
                if (touchingMC != null) {
                    pickup(touchingMC);
                }
            }
            hover(actCount);
        }
        if(pickupActs>0){
            if(pickupActs>=30){
                setSize(originalSize);
                size+=1;
                setSize(size);
                setLocation(origin.getX(), getY()-3);
            }
            if(pickupActs==1){
                collect();
            }
            pickupActs--;
        }
        if(collecting ){
            if(distanceFrom(slotX,slotY)>=speed){
                deltaY=distanceFrom(slotX,slotY)/4;
                turnTowards(slotX,slotY+(int)Math.round(deltaY));
                move(speed);
                speed+=0.5;
            }
            else{
                setLocation(slotX,slotY);
                setSize(32);
            }
        }
        actCount++;
    }
    
    /**
     * Return whether this item is a material.
     * 
     * @return isMaterial   True if item is a material, false if not.
     */
    public boolean isMaterial() {
        return isMaterial;
    }

    /**
     * Set the size of the item image.
     * @param size  The width and height of the image.
     */
    public void setSize(int size){
        GreenfootImage image = getImage();
        image.scale(size,size);
        setImage(image);
    }

    /**
     * Give an MC an item, add dialogue, and also add item to the status bar.
     * 
     * @param origin The MC that picked up the item.
     */
    public void pickup(MC origin){
        this.origin=origin;
        sm.playSound("PickUp");
        if(origin.getItemCount()<=1){
            if(origin.getItemCount()==0){
                slotX= 170 + (300 * origin.getIndex());
            }
            else{
                slotX= 245 + (300 * origin.getIndex());
            }
            slotY= 780;
            pickupActs=50;
            origin.addItem(this);
        }
        MyWorld w = (MyWorld) getWorld();

        if (this instanceof Chemicals) {
            w.dialogue("\"Warning! Explosive\", that's convenient.", 4, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Fork) {
            w.dialogue("Got away with a fork? it's my lucky day.", 4, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Keycard) {
            w.dialogue("YES!A KEYCARD!!", 3, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Food) {
            w.dialogue("A nice treat", 3, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Metal) {
            w.dialogue("I could turn this into something useful", 4, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Wood) {
            w.dialogue("Could use this for a handle or something", 4, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Shovel) {
            w.dialogue("Dis shovel is not disheveled", 4, origin.getSpecialty().toLowerCase());
        }
        if (this instanceof Bomb) {
            w.dialogue("Bombaclaat!", 4, origin.getSpecialty().toLowerCase());
        }
        return;
    }

    /**
     * Set the action of collecting the item to be true.
     */
    public void collect(){ 
        collecting=true;
    }

    /**
     * The hovering effect of the item after being picked up.
     * 
     * @param actCount  The acts to hover for.
     */
    public void hover(int actCount){
        if(actCount%5==0){
            setLocation(getX(),getY()+hoverSpeed);
        }
        if(actCount%60==0){
            hoverSpeed=hoverSpeed*-1;
        }
    }

    /**
     * Return the distance between myself and another (x,y) coordinate pair.
     * 
     * @param x         The other x coordinate.
     * @param y         The other y coordinate.
     * @return double   The distance between myself and another coordinate.
     */
    public double distanceFrom(int x, int y) {
        int deltaX = getX() - x; 
        int deltaY = getY() - y; 
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY); 
    }

    /**
     * Implement the item's effect if applicable.
     */
    public abstract void useItem(MyWorld w, MC p);
}
