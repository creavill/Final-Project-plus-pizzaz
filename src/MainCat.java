import processing.core.PImage;

import java.util.List;
import java.util.Optional;


public class MainCat extends ScheduledAnimation {
    public static int livesLost = 0;
    public static int fullness = 0;
    public MainCat(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.livesLost=livesLost;
    }

    public Point nextPosition(WorldModel world, int dx, int dy){
        return new Point(this.getPosition().getX()+dx, this.getPosition().getY()+dy);
    }

    public static MainCat createMainCat(String id, Point position, List<PImage> images)
    {
        return new MainCat(id, position, images, 4,4);
    }

    public void moveCat(WorldModel world,WorldView view,int dx, int dy,EventScheduler scheduler){
        Point newPos=new Point(this.getPosition().getX() + dx, this.getPosition().getY() + dy);
        Optional<Entity> mouse = world.findNearest(this.getPosition(), MouseFull.class);
        Optional<Entity> mouse2 = world.findNearest(this.getPosition(), MouseNotFull.class);

        boolean a= tryEatMouse(mouse,world,newPos,scheduler);
        boolean b = tryEatMouse(mouse2,world,newPos,scheduler);

        if(!a && !b && !world.isOccupied(new Point(this.getPosition().getX()+dx, this.getPosition().getY()+dy))) {
            newPos=new Point(this.getPosition().getX() + dx, this.getPosition().getY() + dy);
            world.moveEntity(this, newPos);

        }
    }
    public boolean tryEatMouse(Optional<Entity> mouse, WorldModel world, Point newPos,EventScheduler scheduler){
        if(world.isOccupied(newPos)&& mouse.isPresent() && this.getPosition().adjacent(mouse.get().getPosition())){
            Point tgtPos = mouse.get().getPosition();
            world.removeEntity(mouse.get());
            scheduler.unscheduleAllEvents(mouse.get());
            this.fullness+=1;
            return true;
        }
        return false;
    }
    public void death(WorldModel world){
        world.moveEntity(this,new Point(10,10));
    }

    public void rebirth(){
        this.livesLost=0;
        this.fullness=0;
    }
}

