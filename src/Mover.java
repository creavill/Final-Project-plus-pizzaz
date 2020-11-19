import processing.core.PImage;

import java.util.List;

public class Mover extends ScheduledAnimation {
    public Mover(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
}
