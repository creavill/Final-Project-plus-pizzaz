public class Activity extends Action {
    public Activity(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public static Activity createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore, 0);
    }

    public void executeAction(EventScheduler scheduler)
    {
        if (this.entity instanceof OctoFull) {
            ((OctoFull)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof OctoNotFull) {
            ((OctoNotFull)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Fish) {
            ((Fish)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Crab) {
            ((Crab)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Quake) {
            ((Quake)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Sgrass) {
            ((Sgrass)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Atlantis) {
            ((Atlantis)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
    }
}
