import processing.core.PImage;

import java.util.List;

public class Cat extends ScheduledAnimation {

    public static final String CAT_KEY = "cat";
    public static final int CAT_NUM_PROPERTIES = 5;
    public static final int CAT_ID = 1;
    public static final int CAT_COL = 2;
    public static final int CAT_ROW = 3;
    public static final int CAT_ACTION_PERIOD = 4;
    public static final int CAT_ANIMATION_PERIOD = 4;
    public static final String CAT_ID_PREFIX = "cat -- ";
    public static final int CAT_CORRUPT_MIN = 20000;
    public static final int CAT_CORRUPT_MAX = 30000;
    public static final int CAT_REACH = 1;

    public Cat(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod,CAT_ANIMATION_PERIOD);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Dog dog = Dog.createDog(this.getId() + Dog.DOG_ID_SUFFIX,
                pos, this.getActionPeriod() / Dog.DOG_PERIOD_SCALE,
                Dog.DOG_ANIMATION_MIN +
                        Functions.rand.nextInt(Dog.DOG_ANIMATION_MAX - Dog.DOG_ANIMATION_MIN),
                imageStore.getImageList(Dog.DOG_KEY));

        world.addEntity(dog);
        dog.scheduleActions(scheduler, world, imageStore);
    }

    public static Cat createCat(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Cat(id, position, images, actionPeriod);
    }

    public Point nextPosition(WorldModel world, int dx, int dy){
        return new Point(this.getPosition().getX()+dx, this.getPosition().getY()+dy);
    }
}
