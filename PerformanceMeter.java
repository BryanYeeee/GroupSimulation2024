import greenfoot.*;
import java.awt.Color;

public class PerformanceMeter extends Actor
{
    private long lastActTime = 0;
    private int updateInterval = 2;  // Update the display every 10 acts
    private int actCounter = 0;

    public void act()
    {
        // Increment the act counter
        actCounter++;

        // Get the current system time at the start of the act method
        long startActTime = System.currentTimeMillis();

        // Only update the display every 10 acts
        if (actCounter % updateInterval == 0) {
            if (lastActTime != 0) {
                // Calculate the time taken for the last set of acts
                long timeTaken = startActTime - lastActTime;

                // Calculate average time per act
                long averageTime = timeTaken / updateInterval;

                // Display the average time taken per act over the interval
                getWorld().showText("Avg time per act: " + averageTime + " ms", 1050, 40);
            }

            // Reset lastActTime to the current start time
            lastActTime = startActTime;
        }
    }
}
