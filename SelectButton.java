import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SelectButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SelectButton extends Button
{
    private GreenfootImage selectedImage;
    private GreenfootImage deselectedImage;
    private SavedPrisoner savedPrisoner;
    private boolean isSelected = false;
    private SelectWorld selectWorld;
    
    public SelectButton(SelectWorld selectWorld, SavedPrisoner savedPrisoner) {
        this.selectWorld = selectWorld;
        this.savedPrisoner = savedPrisoner;
        selectedImage = new GreenfootImage("NextButton.png");
        deselectedImage = new GreenfootImage("gameArrow.png");
        selectedImage.scale(100, 50);
        deselectedImage.scale(100, 50);
        setImage(deselectedImage);
    }
    
    /**
     * Act - do whatever the SelectButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
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
