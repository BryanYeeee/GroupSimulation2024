import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Select button is used for selecting which main prisoners the user wants in the simulation
 * 
 * @author Ainson Z
 * @version April 2024
 */
public class SelectButton extends Button
{
    private GreenfootImage selectedImage;
    private GreenfootImage deselectedImage;
    private SavedPrisoner savedPrisoner;
    private boolean isSelected = false;
    private SelectWorld selectWorld;
    
    private SoundManager sm;
    
    /**
     * Constructor for SelectButton, set and scale images.
     * 
     * @param selectWorld   The world to be placed in.
     * @param savedPrisoner The associated prisoner with the button.
     */
    public SelectButton(SelectWorld selectWorld, SavedPrisoner savedPrisoner) {
        this.selectWorld = selectWorld;
        this.savedPrisoner = savedPrisoner;
        selectedImage = new GreenfootImage("DeselectButton.png");
        deselectedImage = new GreenfootImage("SelectButton.png");
        selectedImage.scale(150, 50);
        deselectedImage.scale(150, 50);
        setImage(deselectedImage);
    }
    
    /**
     * The act method of SelectButton, vary image depending on clicks and number of selected prisoners.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            sm.playSound("click");
            if (!isSelected && selectWorld.getNumSelectedPrisoners() < 4) {
                isSelected = true;
                setImage(selectedImage); // Change image to indicate selection
                selectWorld.incrementNumSelectedPrisoners(); // Increment the count of selected prisoners
                selectWorld.addSelectedPrisoner(savedPrisoner); // Add this prisoner to the selected list
            } else if (isSelected) {
                isSelected = false;
                setImage(deselectedImage); // Change image to indicate deselection
                selectWorld.decrementNumSelectedPrisoners(); // Decrement the count of selected prisoners
                selectWorld.removeSelectedPrisoner(savedPrisoner); // Remove this prisoner from the selected list
            }
        }
    }
}
