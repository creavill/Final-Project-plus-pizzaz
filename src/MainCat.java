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
        return new MainCat(id, position, images, 4,4);
    }

    public void moveCat(WorldModel world,WorldView view,int dx, int dy){
        Point newPos= new Point(this.getPosition().getX(), this.getPosition().getY() );
        if(!world.isOccupied(new Point(this.getPosition().getX()+dx, this.getPosition().getY()+dy))) {
            newPos=new Point(this.getPosition().getX() + dx, this.getPosition().getY() + dy);
            world.moveEntity(this, newPos);
            //view.shiftView(this.getPosition().getX(),this.getPosition().getY());
         //   if(this.getPosition().getX()==)
         //   view.shiftView(dx,dy,this);
           // view.shiftView(this.getPosition().getX(), this.getPosition().getY());
        }
    }
}

