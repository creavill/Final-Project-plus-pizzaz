import processing.core.PImage;

import java.util.List;

public class Animatable extends Entity{

    private int animationPeriod;

    public Animatable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {
        if (this.getClass() != MouseFull.class && this.getClass() != Cat.class && this.getClass() != MainCat.class && this.getClass() != MouseNotFull.class && this.getClass()
                != Dog.class && this.getClass() != Quake.class && this.getClass() != MouseHole.class) {
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
