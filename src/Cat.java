import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Cat extends Mover {

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
        Optional<Entity> mouse = world.findNearest(this.getPosition(), MouseNotFull.class);
        long nextPeriod = this.getActionPeriod();

        if (mouse.isPresent())
        {
            Point tgtPos = mouse.get().getPosition();
            if (this.moveToCat(world, mouse.get(), scheduler))
            {
                world.removeEntity(mouse.get());
            }
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public static Cat createCat(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Cat(id, position, images, actionPeriod);
    }

    public boolean moveToCat(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            //world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, int dx, int dy){
        return new Point(this.getPosition().getX()+dx, this.getPosition().getY()+dy);
    }
}
