import greenfoot.*;
import java.util.ArrayList;
/**
 * The node class is the various positions on the map that the person actor can traverse
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class Node {
        int index;
        int x;
        int y;
        
        boolean hasBounds;
        int[] boundX;
        int[] boundY;

        boolean hasPerson; // If there is a person on the node, (only nodes thats cannot have more than 1 person on it)
        ArrayList<Node> children;
        
        public Node(String[] config) {
            this.index = Integer.parseInt(config[0]);
            this.x = Integer.parseInt(config[1]);
            this.y = Integer.parseInt(config[2]);
            if (config.length > 3) {
                hasBounds = true;
                boundX = new int[] {x-Integer.parseInt(config[3]), x+Integer.parseInt(config[4])};
                boundY = new int[] {y-Integer.parseInt(config[5]), y+Integer.parseInt(config[6])};
            }
            children = new ArrayList<>();
        }
        
        public void conNode(Node otherNode) { // Link 2 nodes, by making them a child of each other
            otherNode.addChild(this);
            this.addChild(otherNode);
        }
        
        public void addChild(Node otherNode) { // Add node to the child list
            if(children.contains(otherNode)) return;
            children.add(otherNode);
        }
        
        public int calcDist(Node otherNode) { // Calculate distance between 2 nodes
            int x2 = otherNode.getX(), y2 = otherNode.getY();
            return (int)Math.sqrt(Math.pow(x2-x,2) + Math.pow(y2-y,2));
        }
        
        public int getDirection(boolean movingVertical, int x2, int y2) { // Find what direction the character would need to travel in order to get from x2 y2, to the node
            int changeInPos;
            if (movingVertical) {
                changeInPos = y-y2;
            } else {
                changeInPos = x-x2;
            }
            
            // Return 1 if the change is positive and -1 if negative
            return (changeInPos) / Math.abs(changeInPos);
        }
        
        public int getOffset(boolean movingVertical) { // Get offsetPos for character by randomizing a number between the x or y bounds
            if(!hasBounds) return 0;
            if (movingVertical) {
                return Greenfoot.getRandomNumber(boundY[1]+1-boundY[0])+boundY[0]-y;
            } else {
                return Greenfoot.getRandomNumber(boundX[1]+1-boundX[0])+boundX[0]-x;
            }        
        }
        
        public int getJoinOffset(boolean movingVertical, Node otherNode) { // Get offsetPos that is in the bounds of 2 nodes
            if(!hasBounds) return 0;
            int[] newBound;
            if (movingVertical) {
                //System.out.println(boundY[0] + " " + otherNode.getBoundY(0) + " " + boundY[1] + " " + otherNode.getBoundY(1));
                newBound = new int[]{Math.max(boundY[0],otherNode.getBoundY(0)),Math.min(boundY[1],otherNode.getBoundY(1))};
                return Greenfoot.getRandomNumber(newBound[1]+1-newBound[0])+newBound[0]-y;  
            } else {
                newBound = new int[]{Math.max(boundX[0],otherNode.getBoundX(0)),Math.min(boundX[1],otherNode.getBoundX(1))};
                return Greenfoot.getRandomNumber(newBound[1]+1-newBound[0])+newBound[0]-x;  
            }      
        }
        
        public int getAxis(int x2, int y2) { // Find whether a character will have to move vertically or horizontally to get to a node from x2 y2
            // 1 is vertical, -1 is horizontal, 0 is diagonal
            if (!hasBounds) {
                if (x2-x != 0 && y2-y != 0) return 0;
                if (x2 == x) return 1;
                if (y2 == y) return -1;
            } else {
                if (!(boundY[0] <= y2 && y2 <= boundY[1]) && !(boundX[0] <= x2 && x2 <= boundX[1])) return 0;
                
                // x2 y2 is in the bounds
                if(boundX[0] <= x2 && x2 <= boundX[1] && boundY[0] <= y2 && y2 <= boundY[1]) {
                    if (x2-x != 0 && y2-y != 0) return 0;
                    if (x2 == x) return 1;
                    if (y2 == y) return -1;
                } 
                
                if (boundX[0] <= x2 && x2 <= boundX[1]) return 1;
                if (boundY[0] <= y2 && y2 <= boundY[1]) return -1;
            } 
            return 0;
        }
        
        public int getBoundX(int i) {
            return boundX[i];
        }
        
        public int getBoundY(int i) {
            return boundY[i];
        }
        
        public boolean hasPerson() {
            return hasPerson;
        }
        
        public void setPerson(boolean hasPerson) {
            this.hasPerson = hasPerson;
        }
        
        public boolean hasBounds() {
            return hasBounds;
        }
        
        public int getIndex() {
            return index;
        }
        
        public ArrayList<Node> getChildren() {
            return children;
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
    }