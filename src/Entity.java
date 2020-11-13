import processing.core.PImage;

import java.util.List;

abstract class Entity {
    public static final String FISH_KEY = "fish";
    public static final String CRAB_KEY = "crab";
    public static final String CRAB_ID_SUFFIX = " -- crab";
    public static final int CRAB_PERIOD_SCALE = 4;
    public static final int CRAB_ANIMATION_MIN = 50;
    public static final int CRAB_ANIMATION_MAX = 150;
    public static final String QUAKE_KEY = "quake";
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
    public static final int FISH_CORRUPT_MAX = 30000;
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

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
