import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.awt.Font;

/**
 * The Banner class is a supertextbox that displays info at the top of the screen.
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Banner extends SuperTextBox
{
    /**
     * Act - do whatever the Banner wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    protected double secondsLeft;
    protected static int width = 600;
    protected static Color bannerBackgroundColor = new Color (84,84,84);
    protected static greenfoot.Font font = new greenfoot.Font("Times New Roman", false, false, 26);
    protected static greenfoot.Font pixelFont;
    protected BannerIcon icon;
    protected String iconName;

    public Banner(String text, double secondsLeft){
        this(text, secondsLeft,"announcement",bannerBackgroundColor, Color.WHITE);
    }

    public Banner(String text, double secondsLeft, String iconName){
        this(text, secondsLeft,iconName, bannerBackgroundColor, Color.WHITE);
    }

    public Banner(String text, double secondsLeft, String iconName, Color backgroundColor, Color textColor){
        // Inline call to load the font and immediately pass to the super constructor.
        // Note that if loadCustomFont returns null, this will not prevent the NullPointerException.
        
        super(text, backgroundColor, textColor, font, true, width, 8, Color.BLACK); 
        
        if(pixelFont==null){
            initalizeFont();  
        }
        setTextFont(text,pixelFont);
        // Other initialization
        this.iconName = iconName;
        this.secondsLeft = secondsLeft;
    }

    public void addedToWorld(World world){
        icon = new BannerIcon(iconName);
        world.addObject(icon,getX()-(width/2),getY());
    }

    public void act()
    {
        secondsLeft-=(1/60);
        if(secondsLeft==0){
            getWorld().removeObject(icon);
            getWorld().removeObject(this);
        }
    }   

    private void initalizeFont(){
        File f = new File("VT323-Regular.ttf");
        try {
            FileInputStream in = new FileInputStream(f);
            Font dynamicFont, dynamicFont32;

            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
            dynamicFont32 = dynamicFont.deriveFont(26f);

            java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(dynamicFont32);
            pixelFont = new greenfoot.Font(dynamicFont32.getName(), dynamicFont32.getStyle() % 2 == 1, dynamicFont32.getStyle() / 2 == 1, dynamicFont32.getSize());
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }

}
