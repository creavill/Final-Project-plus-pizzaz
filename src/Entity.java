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
   /* public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {

        if (this instanceof OctoFull){
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
            scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0),
                    this.getAnimationPeriod());
        }
        if (this instanceof OctoNotFull) {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this, 0), this.getAnimationPeriod());
        } if (this instanceof Fish){
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }
        if (this instanceof Crab){
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this, 0), this.getAnimationPeriod());
        }
        if (this instanceof Quake){
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this, Quake.QUAKE_ANIMATION_REPEAT_COUNT),
                    this.getAnimationPeriod());
        }
        if (this instanceof Sgrass){
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
        if (this instanceof Atlantis){
            scheduler.scheduleEvent(this,
                    Animation.createAnimationAction(this, Atlantis.ATLANTIS_ANIMATION_REPEAT_COUNT),
                    this.getAnimationPeriod());
        }

    }
*/



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
