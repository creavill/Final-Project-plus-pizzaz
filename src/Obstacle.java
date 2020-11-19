import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity{
    public Obstacle(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }
    public static Entity createObstacle(String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images, 0);
    }
}
