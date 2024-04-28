import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Item extends SuperSmoothMover
{
    /**
     * Act - do whatever the Item wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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
                setSize(48);
            }
        }
        actCount++;
    }
    
    public boolean isMaterial() {
        return isMaterial;
    }

    public void setSize(int size){
        GreenfootImage image = getImage();
        image.scale(size,size);
        setImage(image);
    }

    public void pickup(MC origin){
        this.origin=origin;
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

        return;
    }

    public void collect(){ 
        collecting=true;
    }

    public void hover(int actCount){
        if(actCount%5==0){
            setLocation(getX(),getY()+hoverSpeed);
        }
        if(actCount%60==0){
            hoverSpeed=hoverSpeed*-1;
        }
    }

    public double distanceFrom(int x, int y) {
        int deltaX = getX() - x; 
        int deltaY = getY() - y; 
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY); 
    }

    public abstract void useItem(MyWorld w, MC p);
}
