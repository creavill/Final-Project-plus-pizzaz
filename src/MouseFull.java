import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MouseFull extends Mouse {
    public MouseFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        MouseNotFull mouse = MouseNotFull.createOctoNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(mouse);
        mouse.scheduleActions(scheduler, world, imageStore);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(),
                MouseHole.class);

        if (fullTarget.isPresent() &&
                this.moveTo(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((MouseHole)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            this.transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveTo( WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition( world, target.getPosition());

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

    public static MouseFull createOctoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MouseFull(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }
}
