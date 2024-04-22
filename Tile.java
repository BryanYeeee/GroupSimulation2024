import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends Actor
{
    private int id;
    public Tile(String[] config) {
        this.id = Integer.parseInt(config[0]);
        if (config.length > 3) {
            getImage().scale(Integer.parseInt(config[3])+Integer.parseInt(config[4])+1,Integer.parseInt(config[5])+Integer.parseInt(config[6])+1);
        } else {
            getImage().scale(9,9);
        }
    }
    public void act() {
        if (Greenfoot.mouseClicked(this))
        {
            MyWorld.g.goToNode(id);
            System.out.println(id);
        }
    }
}
