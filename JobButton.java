import greenfoot.*;
import java.util.List;

public class JobButton extends Button {
    private String[] jobs;
    private int selectedJobIndex;
    private SavedPrisoner savedPrisoner;

    public JobButton(String[] jobs, SavedPrisoner savedPrisoner) {
        this.jobs = jobs;
        selectedJobIndex = 0;
        this.savedPrisoner = savedPrisoner;
        updateDisplay();
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            // Cycle to the next job option
            selectedJobIndex = (selectedJobIndex + 1) % jobs.length;
            updateDisplay();
            updatePrisonerJob();
        }
    }

    private void updateDisplay() {
        setImage(new GreenfootImage(jobs[selectedJobIndex], 24, Color.BLACK, Color.WHITE));
    }

    private void updatePrisonerJob() {
        savedPrisoner.setJob(jobs[selectedJobIndex]);
    }
}
