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
    protected int slotX=100;
    protected int slotY=100;
    protected double deltaY;
    protected double hoverSpeed=0.51;
    protected double speed=0;
    protected boolean isMaterial;
    protected String itemName;
    protected GreenfootImage image;
    public Item(String itemName){
        this.itemName = itemName;
        try {
        image = new GreenfootImage("images/"+itemName+".png");
    
        setImage(image);
    }catch(Exception e) {
    }
        setStaticRotation(true);
        pickupActs=-1;
    }

    public void act()
    {

        if(pickupActs==-1){
            hover(actCount);
        }
        if(pickupActs>0){
            if(pickupActs>=30){
                setSize(originalSize);
                size+=1;
                setSize(size);
                setLocation(getX(), getY()-5);
            }
            if(pickupActs==1){
                collect();
            }
            pickupActs--;
        }
        if(collecting ){
            if(distanceFrom(slotX,slotY)>=speed){
                deltaY=distanceFrom(slotX,slotY)/3;
                turnTowards(slotX,slotY+(int)Math.round(deltaY));
                move(speed);
                speed+=0.5;
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

    public void pickup(){
        pickupActs=50;
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
