import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;

/**
 * A class to hold the cutsceneappens after the character introductions
 * After every click (C): Prisoner ABCD will be random from the chosen 4 MCs
 * The 4 MCs should each have their own special dialogue
 * Default: All prisoners are gathered together
 * A guard comes in from them right, tells them they will be executed at dawn tomorrow morning, then leaves after some number of seconds to the right
 * The prisoners all? agree to try to gather materials during the day and escape during night
 * Potential Dialogue:
 * (Guard enters)
 * Guard: "Enjoy your last day here at the prison."
 * Prisoner A: "We are being released?"
 * Guard: "In your dreams, you all will be executed at dawn tomorrow."
 * Prisoner B: "That isn't fair, why didn't we get an earlier notice?"
 * Guard: "Because we felt like it."
 * (Guard leaves)
 * Prisoner C: "HEYYYYY!!! COME BACK!!!" 
 * Prisoner D: "Look, surely we all don't want to die tomorrow."
 * Prisoner D: "We should make a plan to escape."
 * Prisoner D: "I say we can gather materials by day and escape at night."
 * (Now each MC will have their own special line)
 * Ex. I do/don't agree, + some line that connects with the character's traits
 * Note: If they don't agree they shouldn't be Prisoner D
 * +4 lines
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroWorld extends AllWorld
{
    /**
     * Constructor for objects of class IntroWorld.
     * 
     */
    
    // Background
    private GreenfootImage introBackground = new GreenfootImage("prison_cell.jpg");

    //Font
    private greenfoot.Font PixelFont;
    
    // Color 
    private Color bgColor = new Color(119, 136, 153);
    private Color borderColor = new Color(192, 192, 192);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color textColor = new Color(250, 249, 246);
    
    // MCs
    //int MCs[];  // track which MCs were selected by the user
    //private int[] MCs = {1,3,4,6}; //temp
    //MCs[] MCs;
    
    // Dialogue, Speaker, General Text
    // 9 lines + 4 lines (one special line per chosen MC)
    private SuperTextBox[] dialogues = new SuperTextBox[13];
    // Guard + 4 chosen MCs
    private SuperTextBox[] speakers = new SuperTextBox[5];
    // Start-up message
    private SuperTextBox guideMessage;
    // Boxes
    private TempBox dialogueBox;
    private TempBox speakerBox;
    
    // Counters
    private int dialogueCounter = 0;
    
    // Coordinates
    int[] xCoords = {150, 275, 400, 525}; 
    int[] yCoords = {615, 615, 615, 615}; 
    
    // Guard
    private Guard guard;
    private int actsLeft;
    Accessory tempAccessory;
    
    // Indicators
    Indicator[] bubbles = {new Indicator(50, 50), new Indicator(50, 50), new Indicator(50, 50), new Indicator(50, 50)};

    // InnerIcon
    InnerIcon[] exclamationMarks = {new InnerIcon(0, 30, 30), new InnerIcon(0, 30, 30), new InnerIcon(0, 30, 30), new InnerIcon(0, 30, 30)}; 
    InnerIcon[] questionMarks = {new InnerIcon(1, 30, 30), new InnerIcon(1, 30, 30), new InnerIcon(1, 30, 30), new InnerIcon(1, 30, 30)}; 

    private List<String> serializedPrisonersState;
    
    private SavedPrisoner[] savedPrisoners;
    private List<String> MCs;
    
    public IntroWorld(List<String> selectedPrisoners)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        // The world will have prisoners in cells, no dialogue box or dialogue yet
        super(1200, 850, 1); 
        introBackground.scale(1200, 700); // purposely scaled to lower than world height to add dialogue box in a logical place, will cause double background
        setBackground(introBackground);
        // Covering the double background
        SuperTextBox cover = new SuperTextBox("A", Color.BLACK, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf",80), true, 1200, 0, Color.BLACK);
        addObject(cover, 600, 780); 
        // Initalize font so the text isn't displayed as default font
        SimulationFont.initalizeFont();
        
        MCs = selectedPrisoners;
        
        displayCharacters();
        fillSpeakersAndDialogue();
        
        savedPrisoners = new SavedPrisoner[4];
        String[] savedData = new String[4];
        
        int index = 0;
        for(String serializedData : selectedPrisoners) {
            if(index < 4) {
                savedData[index] = serializedData;
                index++;
            } else {
                break;
            }
        }
        
        for(int i = 0; i < 4; i++) {
            savedPrisoners[i] = new SavedPrisoner("", "", 0, 0, 0, "");
            savedPrisoners[i].deserializeState(savedData[i]);
        }
        
        //adding them to the world to edit their stats
        addObject(savedPrisoners[0], 100, 200);
        addObject(savedPrisoners[1], 100, 500);
        addObject(savedPrisoners[2], 450, 200);
        addObject(savedPrisoners[3], 450, 500);
        
        guideMessage = new SuperTextBox("Click to start & advance dialogue", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 54), true, 768, 5, borderColor);
        addObject(guideMessage, 600, 775);
    }

    public void act(){
        // before this it should be just prisoners in the cell
        if(Greenfoot.mouseClicked(null) && dialogueCounter == 0){
            // add code for when guard comes in
            removeObject(guideMessage);
            // add new boxes for dialogue
            dialogueBox = new TempBox(1200, 150, bgColor, borderColor, 5);
            speakerBox = new TempBox(400, 50, bgColor, borderColor, 5);
            addObject(dialogueBox, 600, 780);
            addObject(speakerBox, 350, 700);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[0], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 1){
            for(int i = 0; i < 4; i++){ // add question marks
                addObject(bubbles[i], xCoords[i], yCoords[i] - 100);
                addObject(questionMarks[i], xCoords[i], yCoords[i] - 105);
            }
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[0]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[1], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 2){
            for(int i = 0; i < 4; i++){ // remove question marks
                removeObject(bubbles[i]);
                removeObject(questionMarks[i]);
            }
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[1]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[0], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 3){
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[0]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[2], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 4){
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[2]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[0], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 5){
            actsLeft = 60; // fade out the guard
            for(int i = 0; i < 4; i++){ // add exclamation marks
                addObject(bubbles[i], xCoords[i], yCoords[i] - 100);
                addObject(exclamationMarks[i], xCoords[i], yCoords[i] - 105);
            }
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[0]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[3], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 6){
            for(int i = 0; i < 4; i++){ //remove exclamation marks
                removeObject(bubbles[i]);
                removeObject(exclamationMarks[i]);
            }
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[3]);
            addObject(dialogues[dialogueCounter], 600, 775);
            if(speakers[4].equals("Brute")){ // so doesn't go against personality
                addObject(speakers[3], 350, 695);
            } else {
                addObject(speakers[4], 350, 695);
            }
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 7){
            removeObject(dialogues[dialogueCounter-1]);
            addObject(dialogues[dialogueCounter], 600, 775);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 8){
            removeObject(dialogues[dialogueCounter-1]);
            addObject(dialogues[dialogueCounter], 600, 775);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 9){
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[4]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[1], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 10){
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[1]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[2], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 11){
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[2]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[3], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 12){ // end of dialogue
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[3]);
            addObject(dialogues[dialogueCounter], 600, 775);
            addObject(speakers[4], 350, 695);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 13){
            removeObject(dialogues[dialogueCounter-1]);
            removeObject(speakers[4]);
            removeObject(dialogueBox);
            removeObject(speakerBox);
            SuperTextBox enterSimulation = new SuperTextBox("Click to enter main simulation", bgColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 54), true, 768, 5, borderColor);
            addObject(enterSimulation, 600, 775);
            dialogueCounter++;
        } else if (Greenfoot.mouseClicked(null) && dialogueCounter == 14){ //once world clicked, proceed to main simulation
            Person.setIntro(false);
            switchWorld();
        }
        if(actsLeft <= 60 && actsLeft > 0){
            actsLeft--;
            guard.fade(actsLeft, 60);
            Accessory guardHat = guard.getAccessory();
            guardHat.setActsLeft(actsLeft);
            if(actsLeft == 0){
                removeObject(guardHat);
            }
        }
    }
    // needs a method from character select world that returns some indicator of what MCs the user chose
    private void displayCharacters(){
        // This will be the int[] of MC's that the user chooses, assuming it is getMCs() method returning int[]
        // int[] MCs = getMCs()
        MC[] MCs = {new MC(0, true, "Thief"),new MC(1, true, "Scientist"), new MC(2, true, "Brute"), new MC(3, true, "Weapons Dealer")};
        
        for(int i = 0; i < 4; i++){
            addObject(MCs[i], xCoords[i], yCoords[i]);
        }
        
        guard = new Guard(0, true);
        addObject(guard, 900, 615);
        /*
        for(int i = 0; i < 4; i++){
            int characterNumber = MCs[i]; 
            if(characterNumber == 1){
                mc1 = new MC1();
                addObject(mc1, xCoords[i], yCoords[i]);
            } else if(characterNumber == 2){
                mc2 = new MC2();
                addObject(mc2, xCoords[i], yCoords[i]);
            } else if(characterNumber == 3){
                mc3 = new MC3();
                addObject(mc3, xCoords[i], yCoords[i]);
            } else if(characterNumber == 4){
                mc4 = new MC4();
                addObject(mc4, xCoords[i], yCoords[i]);
            } else if(characterNumber == 5){
                mc5 = new MC5();
                addObject(mc5, xCoords[i], yCoords[i]);
            } else { // must be mc6 if none of the above
                mc6 = new MC6();
                addObject(mc6, xCoords[i], yCoords[i]);
            }
        }
        */
    }
    
    
    private void fillSpeakersAndDialogue(){
        int index = 0;
        // Fill dialogues array with preset dialogue
        // Manadatory dialogue
        dialogues[0] = new SuperTextBox("Enjoy your last day here at the prison.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[1] = new SuperTextBox("Are we being released?", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[2] = new SuperTextBox("In your dreams, you all will be executed at dawn tomorrow.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[3] = new SuperTextBox("That isn't fair, why didn't we get an earlier notice?", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[4] = new SuperTextBox("Because I felt like it.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[5] = new SuperTextBox("HEY!!! COME BACK!!!", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[6] = new SuperTextBox("Look, surely we all don't want to die tomorrow.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[7] = new SuperTextBox("We should make a plan to escape.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        dialogues[8] = new SuperTextBox("I say we can gather materials by day and escape at night.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
        // Special lines for each character, will be chosen depending on which characters were selected as main 4
        for(int i = 9; i < 13; i++){
            String name = MCs.get(index);
            String[] splitName = name.split(",");
            if(splitName[0].equals("Thief")){ // Thief
                dialogues[i] = new SuperTextBox("Sure, I'll steal something useful.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
            } else if(splitName[0].equals("Brute")){ // Brute
                dialogues[i] = new SuperTextBox("Screw you all, I'm doing my own thing.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
            } else if(splitName[0].equals("Scientist")){ // Scientist
                dialogues[i] = new SuperTextBox("Ok, I'll brew up some deadly potions.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
            } else if(splitName[0].equals("WeaponDealer")){ // Weapons Dealer
                dialogues[i] = new SuperTextBox("Very well, I'll create some makeshift weapons.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
            } else if(splitName[0].equals("ExplosiveE")){ // Expolsive Expert
                dialogues[i] = new SuperTextBox("Its time to blow this place up!", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
            } else { // Builder
                dialogues[i] = new SuperTextBox("Time to dig out of this place.", transparentColor, Color.BLACK, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), false, 1024, 0, transparentColor);
            }
            index ++;
        }
        index = 0;
        
        // Fill speakers array with preset speaker
        speakers[0] = new SuperTextBox("Guard", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);
        // Fill speakers array with chosen MCs
        for(int i = 1; i < 5; i++){
            String name = MCs.get(index);
            String[] splitName = name.split(",");
            System.out.println("-----");
            System.out.println(splitName[0]);
            if(splitName[0].equals("Thief")){
                speakers[i] = new SuperTextBox("Thief", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);        
            } else if(splitName[0].equals("Brute")){
                speakers[i] = new SuperTextBox("Brute", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);        
            } else if(splitName[0].equals("Scientist")){
                speakers[i] = new SuperTextBox("Scientist", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);        
            } else if(splitName[0].equals("WeaponDealer")){
                speakers[i] = new SuperTextBox("Weapons Dealer", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);        
            } else if(splitName[0].equals("ExplosiveE")){
                speakers[i] = new SuperTextBox("Expolsive Expert", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);        
            } else { // must be builder
                speakers[i] = new SuperTextBox("Builder", transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 36), true, 256, 0, transparentColor);        
            }
            index++;
        }
        index = 0;
    }
    
    public void switchWorld() {
        serializedPrisonersState = savePrisonersState();
        System.out.println(serializedPrisonersState);
        Greenfoot.setWorld(new MyWorld(serializedPrisonersState));
    }
    
    /**
     * Used to save all the stats of each prisoner
     */
    public List<String> savePrisonersState() {
        List<String> serializedDataList = new ArrayList<>();
        for (SavedPrisoner prisoner : savedPrisoners) {
            String serializedData = prisoner.serializeState();
            serializedDataList.add(serializedData);
        }
        
        return serializedDataList;
    }
}
