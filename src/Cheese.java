import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class Cheese extends ScheduledAction {

    public static final String CHEESE_KEY = "cheese";
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
            Cat cat = Cat.createCat(Cat.CAT_ID_PREFIX + this.getId(),
                    openPt.get(), Cat.CAT_CORRUPT_MIN +
                            Functions.rand.nextInt(Cat.CAT_CORRUPT_MAX - Cat.CAT_CORRUPT_MIN),
                    imageStore.getImageList(Cat.CAT_KEY));
            world.addEntity(cat);
            cat.scheduleActions(scheduler, world, imageStore);
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
