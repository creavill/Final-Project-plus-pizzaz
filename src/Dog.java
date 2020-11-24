import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Dog extends Mover {

    public static final String DOG_KEY = "dog";
    public static final String DOG_ID_SUFFIX = " -- dog";
    public static final int DOG_PERIOD_SCALE = 4;
    public static final int DOG_ANIMATION_MIN = 50;
    public static final int DOG_ANIMATION_MAX = 150;

    public Dog(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> catTarget = world.findNearest(this.getPosition(), MainCat.class);
        long nextPeriod = this.getActionPeriod();

        if (catTarget.isPresent())
        {
            Point tgtPos = catTarget.get().getPosition();
            if (this.moveToDog(world, catTarget.get(), scheduler))
            {
                VirtualWorld.killCat();
            }
        }

        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }


    public boolean moveToDog(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()))
        {
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
                //---------------so the dog doesn't move on the main cats spawn------------------------//
                try {
                    world.moveEntity(this, nextPos);
                }
                catch (ArrayIndexOutOfBoundsException e ){}
            }
            return false;
        }
    }

    public static Dog createDog(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Dog( id, position, images, actionPeriod, animationPeriod);
    }

}
