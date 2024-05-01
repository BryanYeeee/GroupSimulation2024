import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * The electric fence is an actor to show the electricity graphics on the main simulation's fence.
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
    /**
     * The default constructor of ElectricFence, starting Frame of 0 and no rotation.
     */
    public ElectricFence(){
        this(0,false);
    }
    
    /**
     * The secondary constructor of ElectricFence
     * 
     * @param startingFrame     The starting image frame of the fence.
     */
    public ElectricFence(int startingFrame){
        this(startingFrame,false);
    }

    /**
     * The primary constructor of ElectricFence that sets image and rotation.
     * 
     * @param startingFrame     The starting image frame of the fence.
     * @param isRotated         True if the animation should be rotated, false if not.
     */
    public ElectricFence(int startingFrame, boolean isRotated){
        imageIndex = startingFrame;
        this.isRotated = isRotated;
        if(isRotated){
            setRotation(-90);
        }
    }

    /**
     * The act method of ElectricFence
     * Increment the act count to help animation, continously animate.
     */
    public void act()
    {
        actCount++;
        animate();
    }

    /**
     * Animate the fence with a blueish wave.
     */
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
     * Turns the electric fence on or off.
     * 
     * @param onOff True means on false means off.
     */
    public static void setOnOff(boolean onOff){
        isOn = onOff;
    }

}