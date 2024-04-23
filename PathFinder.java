import greenfoot.*;
import java.util.PriorityQueue;
import java.util.ArrayList;
/**
 * The PathFinder class will store the nodes for each coordinate that the people will walk on.
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class PathFinder  
{
    private Node[] nodes; // 

    public PathFinder(MyWorld w)
    {
        String[] nodeConfig = new String[] {
            // Spawn Node
            "0 378 155 45 15 138 0",
            // Hallway:
            "1 378 294 45 15 3 3",
            "2 378 324 45 15 3 3",
            "3 378 474 45 15 43 18",
            
            "36 469 474 0 0 43 18",
            "37 530 474 0 0 43 18",
            "38 591 474 0 0 43 18",
            "39 682 474 0 0 43 18",
            "40 761 474 0 0 6 18",
            
            "41 682 294","42 743 294",
            "125 925 263","126 956 263","127 987 263",
            "43 925 294",               "128 987 294",
            "129 925 325","130 956 325","131 987 325",
            // Dining Hall (connected to node 2): 
            "107 105 172", "108 287 172",
            "5 104 233","6 165 233","7 196 233","8 226 233","9 287 233",
            "20 104 263","21 165 263",          "22 226 263","23 287 263",
            "24 104 294","25 165 294",          "26 226 294","27 287 294",
            "10 104 324","11 165 324","12 196 324","13 226 324","14 287 324",
            "28 104 355","29 165 355",          "30 226 355","31 287 355",
            "32 104 385","33 165 385",          "34 226 385","35 287 385",
            "15 104 415","16 165 415","17 196 415","18 226 415","19 287 415",
            // Roll Call (connected to node 1, 41, 37):
                         "69 500 204","70 530 204","71 560 204",
            "44 470 234","45 500 234","46 530 234","47 560 234","48 590 234",
            "49 470 264","50 500 264","51 530 264","52 560 264","53 590 264",
            "54 470 294","55 500 294","56 530 294","57 560 294","58 590 294",
            "59 470 324","60 500 324","61 530 324","62 560 324","63 590 324",
            "64 470 354","65 500 354","66 530 354","67 560 354","68 590 354",
                                      "72 530 384",
            // Gym (connected to node 40):
                                                          "94 924 443",           "95 972 443",               "99 1014 443", "103 1063 443",
            "73 834 474 0 0 12 16","74 864 474 0 0 12 16","75 924 474 31 0 12 16","96 972 474",               "100 1014 474","104 1063 474",
                                                          "76 924 504 31 0 0 0",  "97 972 504",               "101 1014 504","105 1063 504",
            "79 834 533",          "80 864 533",          "77 924 535 31 0 0 0",  "98 972 535",               "102 1014 535","106 1063 535",
            "81 834 566",          "82 864 566",          "78 924 566 31 0 0 0",  "83 956 566","84 986 566",  "85 1014 566","86 1047 566","87 1077 566",
            "88 834 624",          "89 864 624",                                  "90 956 624","91 986 624",                "92 1047 624","93 1077 624",
            // Library (connected to node 42):
            "109 711 210","110 743 210","111 774 210",
            // Woodworking (connected to node 3):
            "116 316 554","117 347 554","4 378 554",
            "113 316 565","112 347 565",
            "114 316 603","115 347 603",
            // Metalworking (connected to node 39):
            "118 682 554","121 713 554","122 744 554",
                          "119 713 565","120 744 565",
                          "123 713 603","124 744 603",
            // Janitor
            // MC Cells (connected to node 36 38):
            "132 438 569","133 469 569","134 500 569","135 559 569","136 591 569","137 621 569"
        };
        String[] nodeConnections = new String[] {
            // Hallway:
            "0 1",          "41 42","42 43",
            "1 2",
            "2 3",
            "3 36","36 37","37 38","38 39","39 41","39 40",
            "125 43","125 126","126 127",
            "127 128","128 131",
            "129 43","129 130","131 130",
            // Dining Hall (connected to node 2):
            "107 108","108 9",
            "5 6","6 7",        "7 8","8 9",
            "5 20","6 21","7 12","8 22","9 23",
            "10 24","11 25",    "13 26","14 27",
            "10 11","11 12","12 13","13 14","14 2",
            "10 28","11 29",    "13 30","14 31",
            "15 32","33 16","12 17","34 18","35 19",
            "15 16","16 17",        "17 18","18 19",
            // Roll Call (connected to node 1, 41, 37):
            "69 70","70 71",
            "69 45","70 46","71 47",
            "44 45","45 46","46 47","47 48",
            "49 50","50 51","51 52","52 53",
            "54 55","55 56","56 57","57 58",
            "59 60","60 61","61 62","62 63",
            "64 65","65 66","66 67","67 68",
            "1 54","58 41","66 72","72 37",
            "44 49","45 50","46 51","47 52","48 53",
            "49 54","50 55","51 56","52 57","53 58",
            "54 59","55 60","56 61","57 62","58 63",
            "59 64","60 65","61 66","62 67","63 68",
            "3 4",
            // Gym (connected to node 40):
                    "40 73","94 75", "94 95",        "99 100", "99 103",
            "73 74","74 75",         "75 96",        "100 101","100 104",
                            "75 76", "76 97",        "101 102","101 105",
            "79 81","80 82", "76 77", "77 98",        "102 85", "102 106",
            "81 82","82 78","77 78", "78 83","83 84","84 85","85 86","86 87",
            "81 88","82 89",                 "83 90","84 91","86 92","87 93",
            // Library (connected to node 42):
            "109 110","110 111","110 42",
            // Woodworking (connected to node 3):
            "116 117","117 4","4 3",
            "113 116","112 117",
            "114 113","115 112",
            // Metalworking (connected to node 39):
            "118 39","121 118","122 121",
                     "119 121","120 122",
                     "123 119","124 120",
            // Janitor
            // MC Cells (connected to node 36 38):
            "132 133","133 134","133 36","135 136","136 137","136 38"
        };
               
        nodes = new Node[nodeConfig.length]; 
        for (int i = 0; i < nodes.length; i++) {
            String[] c = nodeConfig[i].split(" ");
            if(c.length<=3){
                c = (nodeConfig[i]+" 5 5 5 5").split(" ");
            }
            if(nodes[Integer.parseInt(c[0])] != null) {int x = 1/0;} //SHOULDNT HAPPEN
            nodes[Integer.parseInt(c[0])] = new Node (c);
            if (MyWorld.SHOW_NODES) {
                if (c.length > 3) {
                    w.addObject(new Tile(c), (Integer.parseInt(c[1])-Integer.parseInt(c[3])+Integer.parseInt(c[1])+Integer.parseInt(c[4]))/2, 
                                             (Integer.parseInt(c[2])-Integer.parseInt(c[5])+Integer.parseInt(c[2])+Integer.parseInt(c[6]))/2);
                } else {
                    w.addObject(new Tile(c), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
                }
            }
                
        }
        
        for (int i = 0; i < nodeConnections.length; i++) {
            String[] c = nodeConnections[i].split(" ");        // maybe make it impossible to connect if nodes r diagonal
            nodes[Integer.parseInt(c[0])].conNode(nodes[Integer.parseInt(c[1])]);      
            //System.out.println(nodes[2].getChildren().get(1) + " " + nodes[2].getChildren().get(2).getIndex());
        }
        
        

        // System.out.println("Asd");
        //findPath(0,14);
         // findPath(2,73);
         // findPath(3,73);
        // findPath(4,3);
        // findPath(0,3);
        // findPath(2,4);
        // System.out.println("Asd");
    }
    
    /**
     * This method will print the pathfinder's path from one node to another (only used for debugging).
     * 
     * @param int index of the starting node
     * @param int index of the ending node
     */
    public void findPath(int startNodeIndex, int endNodeIndex) {
        goToNode(null, getNode(startNodeIndex), endNodeIndex);
    }
    
    /**
     * Using the A* algorithm, this method will find the optimal path from one node to another. It will then add the series nodes into the person's path
     * 
     * @param Person the person that will be following the calculated path
     * @param Node starting node
     * @param int index of the ending node
     */
    public void goToNode(Person p, Node startNode, int endNodeIndex) { 
        // a* search algorithm, generate a string path of the nodes needed to travel from one node to another
        ArrayList<Integer> visited = new ArrayList<>();
        String path = "";
        Node endNode = getNode(endNodeIndex);
        
        // Sort from least to greatest distance. When distances are the same, randomize order to have random pathing
        PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>((a,b) -> (a.getDist() == b.getDist() ? Greenfoot.getRandomNumber(3)-1:(a.getDist() - b.getDist())));
        pq.add(new SearchNode(startNode, 0,""));
        
        while(!pq.isEmpty()) {
            SearchNode cur = pq.poll();
            //System.out.println("= " +cur.getNode().getIndex());
            Node curNode = cur.getNode();
            int newIndex = curNode.getIndex();
            if(visited.contains(newIndex)) continue;
            visited.add(newIndex);
            
            ArrayList<Node> children = curNode.getChildren();
            for(Node next : children) {
                if (visited.contains(next.getIndex())) continue;
                //System.out.println("." + next.getIndex());
                
                pq.add(new SearchNode(next, cur.getDist()+curNode.calcDist(endNode), cur.getPath()+","+next.getIndex()));
                if(next.getIndex() == endNodeIndex) {
                    path = cur.getPath()+","+next.getIndex();
                    pq.clear();
                    break;
                }
            }
        }
        
        //DEBUG:System.out.println(path);
        //For testing purposes, in the findPath method
        if(p == null) {
            System.out.println(path);
            return;
        }
        
        // Convert the string of node Ids, to nodes and add the nodes to the character's path
        String[] nodePath = path.split(",");
        for(int i = 1; i< nodePath.length; i++) {
            p.addToPath(getNode(Integer.parseInt(nodePath[i])));
        }
        
    }
    
    public Node getNode(int nodeIndex) {
        return nodes[nodeIndex];
    }
    
    // Helper class to make sorting the nodes in the PriorityQueue more efficient
    private class SearchNode {
        private Node n;
        private String path;
        private int dist;
        
        public SearchNode(Node n, int dist, String path) {
            this.n = n;
            this.dist = dist;
            this.path = path;
        }
        
        public int getDist() {
            return dist;
        }
        
        public String getPath() {
            return path;
        }
        
        public Node getNode() {
            return n;
        }
    }

    
}
