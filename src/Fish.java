import processing.core.PImage;

import java.util.List;

public class Fish extends ScheduledAction {

    public static final String FISH_KEY = "fish";
    public static final int FISH_NUM_PROPERTIES = 5;
    public static final int FISH_ID = 1;
    public static final int FISH_COL = 2;
    public static final int FISH_ROW = 3;
    public static final int FISH_ACTION_PERIOD = 4;
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
    public static final int FISH_CORRUPT_MAX = 30000;
    public static final int FISH_REACH = 1;

    public Fish(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Crab crab = Crab.createCrab(this.getId() + Crab.CRAB_ID_SUFFIX,
                pos, this.getActionPeriod() / Crab.CRAB_PERIOD_SCALE,
                Crab.CRAB_ANIMATION_MIN +
                        Functions.rand.nextInt(Crab.CRAB_ANIMATION_MAX - Crab.CRAB_ANIMATION_MIN),
                imageStore.getImageList(Crab.CRAB_KEY));

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

    public static Fish createFish(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Fish(id, position, images, actionPeriod);
    }
}
