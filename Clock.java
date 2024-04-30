import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The clock is the timer at the top left of the main simulation that displays the current time
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Clock extends SuperTextBox
{
    private double preciseMinutes;
    private int minute;
    private int hour;
    private String display;
    /**
     * Creates a clock that goes through 24 game hours in 120 real life seconds, 
     * 
     * @param startTime is a String for example "7:30"
     */
    public Clock(String startTime){
        super(startTime,new Color (84,84,84), Color.WHITE,new Font("Calibri",true,false,26),true,100,8,Color.BLACK);
        display=startTime;
        String[] parts = startTime.split(":");

        hour = Integer.parseInt(parts[0]);
        minute = Integer.parseInt(parts[1]);
        preciseMinutes = minute;
        
    }
    
    public void act()
    {
        preciseMinutes+=0.2;
        if(((int)preciseMinutes%5)==0){
            updateDisplay();
        }
        if(preciseMinutes>=60){
            preciseMinutes=0;
            hour++;
        }
        if(hour>12){
            hour=1;
        }
    }
    
    private void updateDisplay(){
        minute=(int)Math.floor(preciseMinutes);
        display=hour+":"+String.format("%02d", minute);
        update(display);
    }
    
    public int getHour(){
        return hour;
    }
    
    public int getMinute(){
        minute=(int)Math.floor(preciseMinutes);
        return minute;
    }
    
    public String getTimeAsString(){
        updateDisplay();
        return display;
    }
}
