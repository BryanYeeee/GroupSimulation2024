import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * The electric fence is an actor to show the electricity graphics on the main simulation's fence
 * 
 * @author Jeff G
 * @version April 2024
 */
public class ElectricFence extends Actor
{

    private static boolean isOn = true;
    private int actCount;
    private int animationDelay = 4;
    private int imageIndex = 0;
    private int animationLength = 11;
    private int loopLength = 45;
    private boolean isRotated;
    public ElectricFence(){
        this(0,false);
    }

    public ElectricFence(int startingFrame){
        this(startingFrame,false);
    }

    public ElectricFence(int startingFrame, boolean isRotated){
        imageIndex = startingFrame;
        this.isRotated = isRotated;
        if(isRotated){
            setRotation(-90);
        }
    }

    public void act()
    {
        actCount++;
        animate();
    }

    public void animate() {
        if (actCount % animationDelay != 0) {
            return;
        }
        imageIndex++;
        if(isOn){

            if(imageIndex<animationLength){
                GreenfootImage currentImage = new GreenfootImage("images/electricity/"+imageIndex+".png");
                currentImage.scale(40, 100);
                setImage(currentImage);
            }
            else{

                GreenfootImage currentImage = new GreenfootImage("images/electricity/0.png");
                currentImage.scale(1,1);
                setImage(currentImage);
            }  
        }
        else{
            GreenfootImage currentImage = new GreenfootImage("images/electricity/0.png");
            currentImage.scale(1,1);
            setImage(currentImage);
        }

        if(imageIndex>loopLength){
            imageIndex=0;
        }
    }
    /**
     * Turns the electric fence on or off
     * @param onOff true means on false means off
     */
    public static void setOnOff(boolean onOff){
        isOn = onOff;
    }

}