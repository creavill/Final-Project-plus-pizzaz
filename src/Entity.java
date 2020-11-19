import processing.core.PImage;

import java.util.List;

abstract class Entity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Entity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public List<PImage> getImages() {
        return images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    //    public void scheduleActions(WorldModel world, ImageStore imageStore) {
//        if (this.getClass() == OctoFull.class) {
//            EventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), (long)this.actionPeriod);
//            EventScheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), (long)this.getAnimationPeriod());
//        } else if (this.getClass() == OctoNotFull.class) {
//            EventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), (long)this.actionPeriod);
//            EventScheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), (long)this.getAnimationPeriod());
//        } else if (this.getClass() == Fish.class) {
//            EventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), (long)this.actionPeriod);
//        } else if (this.getClass() == Crab.class) {
//            EventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), (long)this.actionPeriod);
//            EventScheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), (long)this.getAnimationPeriod());
//        } else if (this.getClass() == Quake.class) {
//            EventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), (long)this.actionPeriod);
//            EventScheduler.scheduleEvent(this, Animation.createAnimationAction(this, 10), (long)this.getAnimationPeriod());
//        } else if (this.getClass() == Sgrass.class) {
//            EventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), (long)this.actionPeriod);
//        } else if (this.getClass() == Atlantis.class) {
//            EventScheduler.scheduleEvent(this, Animation.createAnimationAction(this, 7), (long)this.getAnimationPeriod());
//        }
//
//    }



    public int getAnimationPeriod() {
        if (this.getClass() != OctoFull.class && this.getClass() != OctoNotFull.class && this.getClass() != Crab.class && this.getClass() != Quake.class && this.getClass() != Atlantis.class) {
            throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.getClass()));
        } else {
            return this.animationPeriod;
        }
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
}
