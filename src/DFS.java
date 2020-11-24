//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.function.BiPredicate;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import java.util.Map.*;
//import java.util.Queue;
//import java.util.PriorityQueue;
//import java.util.*;
//
//class DFS implements PathingStrategy{
//    private List<Point> computed_path;
//    private  Map<Point, AStarPathingStrategy.Node> closedMap;
//    private  Map<Point, AStarPathingStrategy.Node> openMap;
//    private Queue<AStarPathingStrategy.Node> searched;
//    private AStarPathingStrategy.Node startnode;
//
//    private boolean DFS(Point pos,Point start, Point end, Predicate<Point> canPassThrough, List<Point> path) {
//        this.computed_path=new ArrayList<>();
//        boolean move = false;
//
//
//        //test if this is a valid grid cell
//        if ( grid[pos.y][pos.x] != GridValues.OBSTACLE && grid[pos.y][pos.x] != GridValues.SEARCHED) {
//
//            //check if p_i is goal
//            if (grid[pos.y][pos.x] == GridValues.GOAL) {
//                //get valid = true
//                valid = true;
//
//            }
//            //if p_i isnt the p_goal
//            else {
//
//                //set this value as searched
//                grid[pos.y][pos.x] = GridValues.SEARCHED;
//
//                Point rightN = new Point(pos.x + 1, pos.y);
//
//                Point downN = new Point(pos.x, pos.y + 1);
//
//                Point leftN = new Point(pos.x - 1, pos.y);
//
//                Point upN = new Point(pos.x, pos.y - 1);
//
//                //if up has to catches at ith position conintue up else go left from that ith position  then down
//
//                // if all the points around that one point is
//                valid = moveOnce(rightN, grid, path) ||  moveOnce(leftN, grid, path) ||  moveOnce(downN, grid, path) || moveOnce(upN, grid, path);
//            }
//
//        } //end of condition of pos not osbtactle or not searched
//
//        if (valid == true) {
//
//            System.out.print(pos);
//            this.path.add(pos);
//        }
//        return valid;
//    }
//
//    @Override
//    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
//                                   BiPredicate<Point, Point> withinReach,
//                                   Function<Point, Stream<Point>> potentialNeighbors) {
//        this.computed_path=new ArrayList<>();
//        boolean move=false;
//        Point current=start;
//        List<Point> neighbors = potentialNeighbors.apply(current) // Analyze what points can be
//                .filter(canPassThrough)  // travelled to
//                .filter(p -> !p.equals(end) && !p.equals(start))
//                .collect(Collectors.toList());
//
//        for (Point neighbor: neighbors) { // For each neighbor
//            if(neighbor.equals(end)){move=true;}
//            if (!searched.contains(neighbor)) { // If the neighbor hasn't been evaluated
//                int g = current.getG() + 1;
//                if(openMap.containsKey(neighbor)) { // If the neighbor is in open list
//                    if(g < openMap.get(neighbor).getG()){ // If g needs to be updated
//                        AStarPathingStrategy.Node updated = new AStarPathingStrategy.Node(g, neighbor.heuristic(end),
//                                neighbor.heuristic(end) + g, neighbor, current);
//                        openList.remove(openMap.get(neighbor));
//                        openList.add(updated);
//                        openMap.replace(neighbor, updated);
//                    }
//                }
//                else { // If the neighbor isn't already in open list
//                    AStarPathingStrategy.Node newneighbor = new AStarPathingStrategy.Node(current.getG()+1, neighbor.heuristic(end),
//                            current.getG()+ 1 + neighbor.heuristic(end) , neighbor, current);
//                    openList.add(newneighbor);
//                    openMap.put(neighbor,newneighbor);
//                }
//            }
//            closedMap.put(current.getPos(),current);
//        }
//    }
//}
