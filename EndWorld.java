import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndWorld here.
 * 
 * @author (your name) 
 * @version April 2024
 */
public class EndWorld extends AllWorld
{
    GreenfootImage endingBackground = new GreenfootImage("ending-background.jpeg");

    private Color bgColor = new Color(119, 136, 153, 240);
    private Color borderColor = new Color(192, 192, 192);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color textColor = new Color(250, 249, 246);

    TempBox backImage = new TempBox(800, 600, bgColor, borderColor, 10);
    
    private SuperTextBox[] displayNames = new SuperTextBox[4];
    private SuperTextBox[] displaySpecialties = new SuperTextBox[4];
    private SuperTextBox[] displayHPs = new SuperTextBox[4];
    private SuperTextBox[] displayInts = new SuperTextBox[4];
    private SuperTextBox[] displayStrs = new SuperTextBox[4];
    private SuperTextBox displayEscapes;
    private String[] escapes; // no duplicates in here
    
    SuperTextBox endingText1; 
    SuperTextBox endingText2; 
    // MC[] mainPrisoners must be passed on, right now will just be temp preset values
    public EndWorld()
    {
        super(1200, 850, 1);
        SimulationFont.initalizeFont();
        endingBackground.scale(1200, 850);
        setBackground(endingBackground);
        // Temp
        MC[] mainPrisoners = {new MC(0, true, "Thief"), new MC(1, true, "Brute"), new MC(2, true, "Explosive Expert"), new MC(3, true, "Weapons Dealer")};
        
        // Set texts
        endingText1 = new SuperTextBox("SIMULATION END", transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 54), true, 1024, 0, transparentColor);
        endingText2 = new SuperTextBox("Return to Title Screen", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 600, 5, borderColor);
        displayEscapes = new SuperTextBox("Escapes Occured: " + mainPrisoners[0].getEscapeMethod() + ", " + mainPrisoners[1].getEscapeMethod() + ", " + mainPrisoners[2].getEscapeMethod() + ", " + mainPrisoners[3].getEscapeMethod(), transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), true, 1000, 0, Color.BLACK); 
 
        
        // Display values
        addObject(backImage, 600, 425);
        addObject(endingText1, 600, 200);
        addObject(endingText2, 600, 720);
        addObject(displayEscapes, 600, 635);
        
        for(int i = 0; i < 4; i++){
            addObject(mainPrisoners[i], 340 + (175 * i), 350); 
            // If text is too long
            if(mainPrisoners[i].getSpecialty().equals("Explosive Expert")){
                displaySpecialties[i] = new SuperTextBox("Explo. Expert", transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), false, 200, 0, Color.BLACK); 
            } else if (mainPrisoners[i].getSpecialty().equals("Weapons Dealer")){
                displaySpecialties[i] = new SuperTextBox("Weap. Dealer", transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), false, 200, 0, Color.BLACK); 
            } else {
                displaySpecialties[i] = new SuperTextBox(mainPrisoners[i].getSpecialty(), transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), false, 200, 0, Color.BLACK); 
            }
            displayHPs[i] = new SuperTextBox("HP: " + String.valueOf(mainPrisoners[i].getMaxHealth()), transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), false, 200, 0, Color.BLACK);
            displayInts[i] = new SuperTextBox("INT: " + String.valueOf(mainPrisoners[i].getIntel()), transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), false, 200, 0, Color.BLACK);
            displayStrs[i] = new SuperTextBox("STR: " + String.valueOf(mainPrisoners[i].getStrength()), transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 28), false, 200, 0, Color.BLACK);
            addObject(displaySpecialties[i], 375 + (175 * i), 450);
            addObject(displayHPs[i], 375 + (175 * i), 490);
            addObject(displayInts[i], 375 + (175 * i), 530);
            addObject(displayStrs[i], 375 + (175 * i), 570);
        }
        

    }

    public void act(){
        if(Greenfoot.mouseClicked(endingText2)){
            sm.playSound("click");
            
            Greenfoot.setWorld(new TitleScreen());
        }
    }

}
