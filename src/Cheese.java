import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Cheese extends ScheduledAction {

    public static final String CHEESE_KEY = "seaGrass";
    public static final int CHEESE_NUM_PROPERTIES = 5;
    public static final int CHEESE_ID = 1;
    public static final int CHEESE_COL = 2;
    public static final int CHEESE_ROW = 3;
    public static final int CHEESE_ACTION_PERIOD = 4;

    public Cheese( String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
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

    public static Cheese createCheese(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Cheese(id, position, images, actionPeriod);
    }
}
