public class Activity extends Action {
    public Activity(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public static Activity createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore, 0);
    }

    public void executeAction(EventScheduler scheduler) {
        if (this.entity instanceof OctoFull) {
            OctoFull tempOF = (OctoFull)this.entity;
            tempOF.executeFullActivity(this.world, this.imageStore, scheduler);
        } else if (this.entity instanceof OctoNotFull) {
            OctoNotFull tempONF = (OctoNotFull)this.entity;
            tempONF.executeOctoNotFullActivity(this.world, this.imageStore, scheduler);
        } else if (Fish.class.equals(this.entity.getClass())) {
            Fish tempF = (Fish)this.entity;
            tempF.executeFishActivity(this.world, this.imageStore, scheduler);
        } else if (Crab.class.equals(this.entity.getClass())) {
            Crab tempC = (Crab)this.entity;
            tempC.executeCrabActivity(this.world, this.imageStore, scheduler);
        } else if (Quake.class.equals(this.entity.getClass())) {
            Quake tempQ = (Quake)this.entity;
            tempQ.executeQuakeActivity(this.world, this.imageStore, scheduler);
        } else if (Sgrass.class.equals(this.entity.getClass())) {
            Sgrass tempS = (Sgrass)this.entity;
            tempS.executeSgrassActivity(this.world, this.imageStore, scheduler);
        } else {
            if (!Atlantis.class.equals(this.entity.getClass())) {
                throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getClass()));
            }

            Atlantis tempA = (Atlantis)this.entity;
            tempA.executeActivity(this.world, this.imageStore, scheduler);
        }

    }
}
