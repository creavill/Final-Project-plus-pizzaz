import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MouseNotFull extends Mouse {
    public MouseNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        if (this.getResourceCount() >= this.getResourceLimit())
        {
            MouseFull mouse = MouseFull.createOctoFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(mouse);
            mouse.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(),
                Cat.class);

        if (!notFullTarget.isPresent() ||
                !this.moveTo( world, notFullTarget.get(), scheduler) ||
                !this.transform( world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveTo( WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition()))
        {
            this.setResourceCount(this.getResourceCount()+1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

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

    public static MouseNotFull createOctoNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new MouseNotFull(id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }
}
