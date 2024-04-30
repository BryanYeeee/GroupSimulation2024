import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The BannerIcon is the image that appears on the side of the Banner class
 * 
 * @author Jeff G
 * @version April 2024
 */
public class BannerIcon extends Actor
{

    public BannerIcon(String imageName){
        GreenfootImage image = new GreenfootImage("images/announcements/icon_"+imageName+".png");
        image.scale(48,48);
        int thickness = 8;
        Color borderColor = Color.BLACK;

        GreenfootImage borderedImage = new GreenfootImage(image.getWidth() + 2 * thickness, image.getHeight() + 2 * thickness);
        borderedImage.setColor(borderColor);

        for (int i = 0; i < thickness; i++) {
            borderedImage.drawRect(i, i, borderedImage.getWidth() - 1 - 2*i, borderedImage.getHeight() - 1 - 2*i);
        }

        borderedImage.drawImage(image, thickness, thickness);
        setImage(borderedImage);
    }

    public void act()
    {
        // Add your action code here.
    }
}
