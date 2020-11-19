import processing.core.PImage;

import java.util.List;

abstract class Entity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int actionPeriod;
    private int animationPeriod;

    public Entity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
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


    public int getActionPeriod() {
        return actionPeriod;
    }


    public void setPosition(Point position) {
        this.position = position;
    }


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
