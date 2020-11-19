import processing.core.PImage;

import java.util.List;

public class Mover extends ScheduledAnimation {
    public Mover(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }
}
