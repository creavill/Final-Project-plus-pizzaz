import processing.core.PImage;

import java.util.List;

public class Fish extends Entity {
    public Fish(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity crab = Crab.createCrab(this.id + CRAB_ID_SUFFIX,
                pos, this.actionPeriod / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                imageStore.getImageList( CRAB_KEY));

        world.addEntity(crab);
        scheduler.scheduleActions(crab, world, imageStore);
    }

    public static Entity createFish(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Fish(id, position, images, 0, 0, actionPeriod, 0);
    }
}
