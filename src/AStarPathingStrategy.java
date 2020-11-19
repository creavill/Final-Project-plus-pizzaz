import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map.*;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.*;

class AStarPathingStrategy implements PathingStrategy{
    private List<Point> computed_path;
    private  Map<Point,Node> closedMap;
    private  Map<Point,Node> openMap;
    private Queue<Node> openList;
    private Node startnode;


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        this.computed_path = new ArrayList<>(); /// Return value
        this.closedMap = new HashMap<Point, Node>(); // Initialize closed list
        this.openMap = new HashMap<Point, Node>();
        this.openList = new PriorityQueue<Node>(Comparator.comparingInt(Node::getF)
        .thenComparing(Node::getG)); // Initialize open list
        startnode = new Node(0, start.heuristic(end), 0 , start, null); // Define the start position

        openList.add(startnode); // Add the start position to open list
        Node current = null;

        while (!openList.isEmpty()) { // While open list isn't empty
            current = openList.remove(); // Get the node with lowest f
            if (withinReach.test(current.getPos(), end)){ // If reached goal, return path
                return computedPath(computed_path, current);
            }

            List<Point> neighbors = potentialNeighbors.apply(current.getPos()) // Analyze what points can be
                    .filter(canPassThrough)  // travelled to
                    .filter(p -> !p.equals(end) && !p.equals(start))
                    .collect(Collectors.toList());


            for (Point neighbor: neighbors) { // For each neighbor
                if (!closedMap.containsKey(neighbor)) { // If the neighbor hasn't been evaluated
                    int g = current.getG() + 1;
                    if(openMap.containsKey(neighbor)) { // If the neighbor is in open list
                        if(g < openMap.get(neighbor).getG()){ // If g needs to be updated
                            Node updated = new Node(g, neighbor.heuristic(end),
                                    neighbor.heuristic(end) + g, neighbor, current);
                            openList.remove(openMap.get(neighbor));
                            openList.add(updated);
                            openMap.replace(neighbor, updated);
                        }
                    }
                    else { // If the neighbor isn't already in open list
                        Node newneighbor = new Node(current.getG()+1, neighbor.heuristic(end),
                                current.getG()+ 1 + neighbor.heuristic(end) , neighbor, current);
                        openList.add(newneighbor);
                        openMap.put(neighbor,newneighbor);
                    }
                }
                closedMap.put(current.getPos(),current);
            }
        }
        return computed_path;
    }


    public List<Point> computedPath(List<Point> compPath, Node end)
    {
        if(end.getPrevNode() == null)
        {
            Collections.reverse(compPath);
            return compPath;
        }
        compPath.add(end.getPos());
        return computedPath(compPath, end.getPrevNode());

    }


    class Node {
        private int g;
        private int h;
        private int f;
        private Node prev_node;
        private Point pos;

        public Node (int g, int h, int f, Point pos, Node prev_node){
            this.g = g;
            this.h = h;
            this.f = f;
            this.prev_node = prev_node;
            this.pos = pos;
        }


        public int getH(){return h;}
        public int getF(){return f;}
        public void setG(int g){this.g = g;}
        public void setH(int h){this.h = h;}
        public int getG(){return g;}
        public void setPos(Point p){pos = p;}
        public Point getPos(){return pos;}
        public Node getPrevNode(){return prev_node;}

    }
}