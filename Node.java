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

        boolean hasPerson; // If there is a person on the node, (only nodes thats cannot have more than 1 person on it use this)
        ArrayList<Node> children;
        
        /**
         * Constructor for the Node
         *
         * @param config    The x, y position and possible boundary size of the node.
         */
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
        
        /**
         * Link 2 nodes, by making them a child of each other
         * 
         * @param otherNode The other node
         */
        public void conNode(Node otherNode) {
            otherNode.addChild(this);
            this.addChild(otherNode);
        }
        
        /**
         * Add node to the child list
         * 
         * @param otherNode The other node
         */
        public void addChild(Node otherNode) {
            if(children.contains(otherNode)) return;
            children.add(otherNode);
        }
        
        /**
         * Calculate distance between 2 nodes
         * 
         * @param otherNode The other node
         * @return int The distance between the 2 nodes
         */
        public int calcDist(Node otherNode) {
            int x2 = otherNode.getX(), y2 = otherNode.getY();
            return (int)Math.sqrt(Math.pow(x2-x,2) + Math.pow(y2-y,2));
        }
        
        /**
         * Find what direction the person would need to travel in order to get from a position to a node
         * 
         * @param movingVertical The current axis the person is moving on
         * @param x2 The x position
         * @param y2 The y position
         * @return int The direction that person will have to face
         */
        public int getDirection(boolean movingVertical, int x2, int y2) {
            int changeInPos;
            if (movingVertical) {
                changeInPos = y-y2;
            } else {
                changeInPos = x-x2;
            }
            
            // Return 1 if the change is positive and -1 if negative
            return (changeInPos) / Math.abs(changeInPos);
        }
        
        /**
         * Get offsetPos for character by randomizing a number between the x or y bounds
         * 
         * @param movingVertical The current axis the person is moving on
         * @return int The offset position that the person will use 
         */
        public int getOffset(boolean movingVertical) {
            if(!hasBounds) return 0;
            if (movingVertical) {
                return Greenfoot.getRandomNumber(boundY[1]+1-boundY[0])+boundY[0]-y;
            } else {
                return Greenfoot.getRandomNumber(boundX[1]+1-boundX[0])+boundX[0]-x;
            }        
        }
        
        /**
         * Get offsetPos that is in the bounds of 2 nodes
         * 
         * @param movingVertical The current axis the person is moving on
         * @param otherNode The other node
         * @return int The offset position that the person will use 
         */
        public int getJoinOffset(boolean movingVertical, Node otherNode) {
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
        
        /**
         * Find whether a person will have to move vertically or horizontally to get to a node from x2 y2
         * 
         * @param x2 The x position
         * @param y2 The y position
         * @return int The axis that the person will use to decide its movement 
         */
        public int getAxis(int x2, int y2) {
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
        
        /**
         * Return the x boundary
         * 
         * @param i The index of x boundaries (x to the left or x to the right)
         * @return boundX[i] The x boundary
         */
        public int getBoundX(int i) {
            return boundX[i];
        }
        
        /**
         * Return the y boundary
         * 
         * @param i The index of y boundaries (y up or y down)
         * @return boundY[i] The y boundary
         */
        public int getBoundY(int i) {
            return boundY[i];
        }
        
        /**
         * Returns whether the node is occupied
         * 
         * @return hasPerson Whether the node has a person on it or not
         */
        public boolean hasPerson() {
            return hasPerson;
        }
        
        /**
         * Set the node to be occupied
         * 
         * @param hasPerson The new value of hasPerson
         */
        public void setPerson(boolean hasPerson) {
            this.hasPerson = hasPerson;
        }
        
        /**
         * Returns whether the node has a boundary or not
         * 
         * @return hasBounds Whether the node has boundaries or not
         */
        public boolean hasBounds() {
            return hasBounds;
        }
        
        /**
         * Returns the index of the node
         * 
         * @return index The index of the node
         */
        public int getIndex() {
            return index;
        }
        
        /**
         * Returns the children nodes connected to the current node
         * 
         * @return children The list of children nodes
         */
        public ArrayList<Node> getChildren() {
            return children;
        }
        
        /**
         * Returns the x position of the node
         * 
         * @return x The x position
         */
        public int getX() {
            return x;
        }
        
        /**
         * Returns the y position of the node
         * 
         * @return y The y position
         */
        public int getY() {
            return y;
        }
    }