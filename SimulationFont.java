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
 * @author
 * 
 */
public class SimulationFont extends Actor
{
    // Text variables
    private String text;
    private int xCoord;
    private int yCoord;
    private double widthScale;
    private double heightScale;
    private Color color;
    private static greenfoot.Font PixelFont;

    /**
     * Construct a Greenfoot Font from a .ttf file, then draw text
     * @param1 The text to be drawn
     * @param2 The x-coordinate for the text to be drawn 
     * @param3 The y-coordinate for the text to be drawn
     * @param4 The scaling of the width of the text, scaling based on a factor to the default size
     * @param5 The scaling of the height of the text, scaling based on a factor to the default size
     * @param6 The color of the text
     */
    public SimulationFont(String text, int xCoord, int yCoord, double widthScale, double heightScale, Color color){
        File f = new File("VT323-Regular.ttf");
        try {
            FileInputStream in = new FileInputStream(f);
            Font dynamicFont, dynamicFont32;

            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
            dynamicFont32 = dynamicFont.deriveFont(32f);

            java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(dynamicFont32);
            PixelFont = new greenfoot.Font(dynamicFont32.getName(), dynamicFont32.getStyle() % 2 == 1, dynamicFont32.getStyle() / 2 == 1, dynamicFont32.getSize());
            in.close();

            //new code
            this.text = text;
            this.xCoord = xCoord;
            this.yCoord = yCoord;
            this.widthScale = widthScale;
            this.heightScale = heightScale;
            this.color = color;
            
            GreenfootImage img = new GreenfootImage(1200, 850); //default to this 
            img.setFont(PixelFont);
            img.setColor(color);
            // Draw the text at this coordinate in the world
            img.drawString(text, xCoord, yCoord); 
            // Scale the text where 1024, 800 parameters are the default scale
            img.scale((int)(1200 * widthScale), (int)(850 * heightScale)); 
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
    
    /**
     * Return a greenfoot.Font object and the font size from a .tff file
     * Made from ChatGPT
     * @param1 The .tff file's name
     * @param2 The size of the font
     */
    public static greenfoot.Font loadCustomFont(String path, float size) {
        try {
            java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(path));
            awtFont = awtFont.deriveFont(size);
            return new greenfoot.Font(awtFont.getFamily(), awtFont.isBold(), awtFont.isItalic(), (int)size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void initalizeFont(){
        File f = new File("VT323-Regular.ttf");
        try {
            FileInputStream in = new FileInputStream(f);
            Font dynamicFont, dynamicFont32;

            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
            dynamicFont32 = dynamicFont.deriveFont(32f);

            java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(dynamicFont32);
            PixelFont = new greenfoot.Font(dynamicFont32.getName(), dynamicFont32.getStyle() % 2 == 1, dynamicFont32.getStyle() / 2 == 1, dynamicFont32.getSize());
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


