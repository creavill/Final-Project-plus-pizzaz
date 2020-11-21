import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Mover extends ScheduledAnimation {
    public Mover(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy aStarStrategy = new AStarPathingStrategy();
        List<Point> nextPoints = aStarStrategy.computePath(getPosition(), destPos, canPassThrough(world), withinReach(), PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
        if (nextPoints.size() == 0) {
            return getPosition();
        }
        return nextPoints.get(0);
    }



    private static Predicate<Point> canPassThrough(WorldModel world) {
        return p -> (world.withinBounds(p) && !world.isOccupied(p));
    }

    private static BiPredicate<Point, Point> withinReach() {
        return Point::adjacent;
    }

}
