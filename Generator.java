import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The generator class is the responsible for turning off the electric fence allowing a possible escape
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Generator extends Actor
{
    private final String offImg = "generatorOff.png";
    
    private SoundManager sm;
    public void turnOff() {
        sm.playSound("GeneratorOff");
        setImage(new GreenfootImage(offImg));
        ElectricFence.setOnOff(false);
    }
}
