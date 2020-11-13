import processing.core.PApplet;

import java.util.Iterator;

final class WorldView {
    public PApplet screen;
    public WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world, int tileWidth, int tileHeight) {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void drawEntities() {
        Iterator var1 = this.world.entities.iterator();

        while(var1.hasNext()) {
            Entity entity = (Entity)var1.next();
            Point pos = entity.position;
            if (this.viewport.contains(pos)) {
                Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
                this.screen.image(Functions.getCurrentImage(entity), (float)(viewPoint.x * this.tileWidth), (float)(viewPoint.y * this.tileHeight));
            }
        }

    }

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = clamp(this.viewport.col + colDelta, 0, this.world.numCols - this.viewport.numCols);
        int newRow = clamp(this.viewport.row + rowDelta, 0, this.world.numRows - this.viewport.numRows);
        this.viewport.shift(newCol, newRow);
    }
}
