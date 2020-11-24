import processing.core.PImage;

import java.util.List;

public class Mouse extends Mover {

    public static final String MOUSE_KEY = "mouse";
    public static final int MOUSE_NUM_PROPERTIES = 7;
    public static final int MOUSE_ID = 1;
    public static final int MOUSE_COL = 2;
    public static final int MOUSE_ROW = 3;
    public static final int MOUSE_LIMIT = 4;
    public static final int MOUSE_ACTION_PERIOD = 5;
    public static final int MOUSE_ANIMATION_PERIOD = 6;

    private int resourceCount;
    private int resourceLimit;

    public Mouse( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
}
