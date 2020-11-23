import processing.core.PImage;

import java.util.List;

public class MainCat extends ScheduledAnimation {
    public MainCat(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, int dx, int dy){
        return new Point(this.getPosition().getX()+dx, this.getPosition().getY()+dy);
    }

    public static MainCat createMainCat(String id, Point position, int actionPeriod, List<PImage> images)
    {
        System.out.println("moving");
        return new MainCat(id, position, images, 4,4);
    }
}

