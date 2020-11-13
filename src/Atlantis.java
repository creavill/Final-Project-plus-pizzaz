import processing.core.PImage;

import java.util.List;

public class Atlantis extends Entity {
    public Atlantis(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public static Entity createAtlantis(String id, Point position, List<PImage> images) {
        return new Atlantis(id, position, images, 0, 0, 0, 0);
    }
}
