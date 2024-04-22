import greenfoot.*;

/**
 * Used to take in a certain stat and have that stat's stats increased or decreased
 * 
 * @author Ainson
 * @version April 2024
 */
public class StatSetter extends Actor{
    private double value;

    private boolean isSpeedStat;

    /**
     * Used to create an initial value for a stat
     * 
     * @param intitalValue
     */
    public StatSetter(double initialValue, boolean isSpeedStat) {
        this.value = initialValue;
        this.isSpeedStat = isSpeedStat;
    }

    /**
     * 
     */
    public double getValue() {
        return value;
    }

    public void setValue(double newValue) {
        // Ensure the new value stays within the range of 1 to 10
        value = Math.max(1, Math.min(newValue, 10));
    }

    public void increaseValue() {
        if(isSpeedStat) {
            if(value < 3) {
                value = (value * 10 + 2) / 10;
            }
        } else {
            if(value < 10) {
                value = (value * 10 + 2) / 10;
            }
        }
    }

    public void decreaseValue() {
        if (value > 1) {
            value = (value * 10 - 2) / 10;
        }
    }
}
