import processing.core.PImage;

import java.util.List;

public class Animatable extends Entity{

    private int animationPeriod;

    public Animatable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {
        if (this.getClass() != OctoFull.class && this.getClass() != OctoNotFull.class && this.getClass()
                != Crab.class && this.getClass() != Quake.class && this.getClass() != Atlantis.class) {
            throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.getClass()));
        } else {
            return this.animationPeriod;
        }
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }
}
