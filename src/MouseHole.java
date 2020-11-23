import processing.core.PImage;

import java.util.List;

public class MouseHole extends Animatable {

    public static final String MOUSEHOLE_KEY = "mouseHole";
    public static final int MOUSEHOLE_NUM_PROPERTIES = 4;
    public static final int MOUSEHOLE_ID = 1;
    public static final int MOUSEHOLE_COL = 2;
    public static final int MOUSEHOLE_ROW = 3;
    public static final int MOUSEHOLE_ANIMATION_PERIOD = 70;
    public static final int MOUSEHOLE_ANIMATION_REPEAT_COUNT = 7;

    public MouseHole(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public static Entity createMouseHole(String id, Point position, List<PImage> images) {
        return new MouseHole(id, position, images, 0, 0);
    }
}
