import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Utility class to draw text using a preset Font. Add this object to the world to display text. <p>
 * Note: Majority of code is taken from crissty21 from Greenfoot forum: "Import Font from .ttf file" <p>
 * Link: https://www.greenfoot.org/topics/65058/0
 * 
 * @author Jamison Huang
 * 
 */
public class SimulationFont extends Actor
{
    // Text variables
    private String text;
    private int scale;
    
    /**
     * Construct a Greenfoot Font from a .ttf file, then draw text
     * @param1 The text to be shown
     * @param2 The size of the text
     * @param3 The size of the blank image the text is drawn on
     */
    public SimulationFont(String text, int textScale, int imageScale){
        File f = new File("VT323-Regular.ttf");
        try {
            FileInputStream in = new FileInputStream(f);
            Font dynamicFont, dynamicFont32;
 
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
            dynamicFont32 = dynamicFont.deriveFont(32f);
 
            java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(dynamicFont32);
            greenfoot.Font PixelFont = new greenfoot.Font(dynamicFont32.getName(), dynamicFont32.getStyle() % 2 == 1, dynamicFont32.getStyle() / 2 == 1, dynamicFont32.getSize());
            in.close();
            
            //new code (Location is scuffed, due to font scaling, drawing text, blank image creation)
            GreenfootImage img = new GreenfootImage(imageScale, imageScale);
            img.setColor(Color.BLACK);
            img.setFont(PixelFont);
            img.drawString(text, 50, 50);
            img.scale(textScale, textScale);
            setImage(img);
            // ends here
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
}
