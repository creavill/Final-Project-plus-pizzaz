public class Activity extends Action {
    public Activity(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public static Activity createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore, 0);
    }

    public void executeAction(EventScheduler scheduler)
    {
        if (this.entity instanceof MouseFull) {
            ((MouseFull)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof MouseNotFull) {
            ((MouseNotFull)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Cat) {
            ((Cat)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Dog) {
            ((Dog)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Quake) {
            ((Quake)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof Cheese) {
            ((Cheese)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
        if (this.entity instanceof MouseHole) {
            ((MouseHole)this.entity).execute(this.world,
                    this.imageStore, scheduler);
        }
    }
}
