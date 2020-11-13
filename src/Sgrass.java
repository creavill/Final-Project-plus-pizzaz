import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Sgrass extends Entity {
    public Sgrass( String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeSgrassActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = WorldModel.findOpenAround(world, this.position);

        if (openPt.isPresent())
        {
            Entity fish = Fish.createFish(FISH_ID_PREFIX + this.id,
                    openPt.get(), FISH_CORRUPT_MIN +
                            Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                    imageStore.getImageList(FISH_KEY));
            world.addEntity(fish);
            scheduler.scheduleActions(fish, world, imageStore);
        }

        scheduler.scheduleEvent( this,
                Activity.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public static Sgrass createSgrass(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Sgrass(id, position, images, 0, 0, actionPeriod, 0);
    }
}
