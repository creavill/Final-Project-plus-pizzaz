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
            Point pos = entity.getPosition();
            if (this.viewport.contains(pos)) {
                Point viewPoint = this.viewport.worldToViewport(pos.getX(), pos.getY());
                this.screen.image(ImageStore.getCurrentImage(entity), (float)(viewPoint.getX() * this.tileWidth), (float)(viewPoint.getY() * this.tileHeight));
            }
        }
    }

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

  /*  public void shiftView(int colDelta, int rowDelta, MainCat cat) {
        int newCol = clamp(this.viewport.col + colDelta, 0, this.world.numCols - this.viewport.numCols);
        int newRow = clamp(this.viewport.row + rowDelta, 0, this.world.numRows - this.viewport.numRows);
        int centerX =this.world.numCols - this.viewport.numCols;
        int centerY =this.world.numRows - this.viewport.numRows;
        System.out.println("col" + centerX);
        System.out.println("cat" + cat.getPosition().getX());
        if(cat.getPosition().getX()==centerX/2 || cat.getPosition().getY()==centerY/2) {
            this.viewport.shift(newCol, newRow);
        }
    }
*/
    public static int clamp(int value, int high) {
        return Math.min(high, value);
    }

    public void shiftView(int x, int y) {
        int shiftX=10;
        int shiftY=10;
        System.out.println(clamp(x, this.world.numCols - this.viewport.numCols));
        int newCol = clamp(x-shiftX, this.world.numCols - this.viewport.numCols);
        int newRow = clamp(y-shiftY, this.world.numRows - this.viewport.numRows);
        this.viewport.shift(newCol, newRow);
    }


}
