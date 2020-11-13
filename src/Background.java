import processing.core.PImage;

import java.util.List;
import java.util.Optional;

final class Background {
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public static boolean parseBackground(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            String id = properties[1];
            setBackground(world, pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == 4;
    }

    public static Optional<PImage> getBackgroundImage(WorldModel world, Point pos) {
        return world.withinBounds(pos) ? Optional.of(ImageStore.getCurrentImage(getBackgroundCell(world, pos))) : Optional.empty();
    }

    public static void setBackground(WorldModel world, Point pos, Background background) {
        if (world.withinBounds(pos)) {
            setBackgroundCell(world, pos, background);
        }

    }

    public static Background getBackgroundCell(WorldModel world, Point pos) {
        return world.background[pos.y][pos.x];
    }

    public static void setBackgroundCell(WorldModel world, Point pos, Background background) {
        world.background[pos.y][pos.x] = background;
    }

    public static void drawBackground(WorldView view) {
        for(int row = 0; row < view.viewport.numRows; ++row) {
            for(int col = 0; col < view.viewport.numCols; ++col) {
                Point worldPoint = view.viewport.viewportToWorld(col, row);
                Optional<PImage> image = getBackgroundImage(view.world, worldPoint);
                if (image.isPresent()) {
                    view.screen.image((PImage)image.get(), (float)(col * view.tileWidth), (float)(row * view.tileHeight));
                }
            }
        }

    }
}
