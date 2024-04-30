import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The dialogue is a type of banner that shows the dialogue from the prisoners
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Dialogue extends Banner
{
    /**
     * Act - do whatever the Announcement wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int actCount;
    private int actsLeft;
    private static Color announcementBackgroundColor = new Color (255,255,255);
    private String text;
    private String currText="";
    //private static GreenfootSound dialogueSound = new GreenfootSound("sounds/bannerSound_dialogue.mp3");
    private boolean soundPlaying;
    public Dialogue(String text, String iconName){
        this(text, 2, iconName); 
        
    }
    public Dialogue(String text,double seconds, String iconName){
        super(" ", seconds, iconName, announcementBackgroundColor, Color.BLACK); 
        this.text=text;
        secondsLeft = seconds;
        actCount=0;
    }

    public void act()
    {
        actCount++;
        if(actCount==1){
            //dialogueSound.play();
            soundPlaying = true;
        }
        if (text.length() == 0) {
            currText=" ";
        }
        if(((int)actCount/1.5)<text.length()){
            currText = text.substring(0, (int)(actCount/1.5) % text.length() + 1);
            update(currText);

        }
        else if (soundPlaying==true){

            //dialogueSound.stop();
            soundPlaying = false;

        }
        else{

        }
        super.act();

    }

}
