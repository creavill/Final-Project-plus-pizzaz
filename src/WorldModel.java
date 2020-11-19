
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

final class WorldModel {
    public int numRows;
    public int numCols;
    public Background[][] background;
    public Entity[][] occupancy;
    public Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet();

        for(int row = 0; row < numRows; ++row) {
            Arrays.fill(this.background[row], defaultBackground);
        }

    }

    public static Optional<Point> findOpenAround(WorldModel world, Point pos) {
        for(int dy = -1; dy <= 1; ++dy) {
            for(int dx = -1; dx <= 1; ++dx) {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (world.withinBounds(newPt) && !world.isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.position)) {
            this.setOccupancyCell(entity.position, entity);
            this.entities.add(entity);
        }

    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0 && pos.getX() < this.numCols;
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.position)) {
            throw new IllegalArgumentException("position occupied");
        } else {
            this.addEntity(entity);
        }
    }

    public Optional<Entity> findNearest(Point pos, Class classtype) {
        List<Entity> ofType = new LinkedList();
        Iterator var4 = this.entities.iterator();

        while(var4.hasNext()) {
            Entity entity = (Entity)var4.next();
            if (entity.getClass() == classtype) {
                ofType.add(entity);
            }
        }

        return this.nearestEntity(ofType, pos);
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.position;
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, (Entity)null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.position = pos;
        }

    }

    public void removeEntity(Entity entity) {
        this.removeEntityAt(entity.position);
    }

    public void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);
            entity.position = new Point(-1, -1);
            this.entities.remove(entity);
            this.setOccupancyCell(pos, (Entity)null);
        }

    }

    public Optional<Entity> getOccupant(Point pos) {
        return this.isOccupied(pos) ? Optional.of(this.getOccupancyCell(pos)) : Optional.empty();
    }

    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = (Entity)entities.get(0);
            int nearestDistance = pos.distanceSquared(nearest.position);
            Iterator var5 = entities.iterator();

            while(var5.hasNext()) {
                Entity other = (Entity)var5.next();
                int otherDistance = pos.distanceSquared(other.position);
                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
}
