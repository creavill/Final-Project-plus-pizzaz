import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Sgrass extends ScheduledAction {

    public static final String SGRASS_KEY = "seaGrass";
    public static final int SGRASS_NUM_PROPERTIES = 5;
    public static final int SGRASS_ID = 1;
    public static final int SGRASS_COL = 2;
    public static final int SGRASS_ROW = 3;
    public static final int SGRASS_ACTION_PERIOD = 4;

    public Sgrass( String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = WorldModel.findOpenAround(world, this.getPosition());

        if (openPt.isPresent())
        {
            Fish fish = Fish.createFish(Fish.FISH_ID_PREFIX + this.getId(),
                    openPt.get(), Fish.FISH_CORRUPT_MIN +
                            Functions.rand.nextInt(Fish.FISH_CORRUPT_MAX - Fish.FISH_CORRUPT_MIN),
                    imageStore.getImageList(Fish.FISH_KEY));
            world.addEntity(fish);
            fish.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent( this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }

    public static Sgrass createSgrass(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Sgrass(id, position, images, actionPeriod, 0);
    }
}
