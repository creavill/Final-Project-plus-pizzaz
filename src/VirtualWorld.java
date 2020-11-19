import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public final class VirtualWorld extends PApplet {
    public static final int TIMER_ACTION_PERIOD = 100;
    public static final int VIEW_WIDTH = 640;
    public static final int VIEW_HEIGHT = 480;
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    public static final int WORLD_WIDTH_SCALE = 2;
    public static final int WORLD_HEIGHT_SCALE = 2;
    public static final int VIEW_COLS = 20;
    public static final int VIEW_ROWS = 15;
    public static final int WORLD_COLS = 40;
    public static final int WORLD_ROWS = 30;
    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    public static final int DEFAULT_IMAGE_COLOR = 8421504;
    public static final String LOAD_FILE_NAME = "world.sav";
    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5D;
    public static final double FASTER_SCALE = 0.25D;
    public static final double FASTEST_SCALE = 0.1D;
    public static double timeScale = 1.0D;
    public ImageStore imageStore;
    public WorldModel world;
    public WorldView view;
    public EventScheduler scheduler;
    public long next_time;

    public VirtualWorld() {
    }

    public void settings() {
        this.size(640, 480);
    }

    public void setup() {
        this.imageStore = new ImageStore(createImageColored(32, 32, 8421504));
        this.world = new WorldModel(30, 40, createDefaultBackground(this.imageStore));
        this.view = new WorldView(15, 20, this, this.world, 32, 32);
        this.scheduler = new EventScheduler(timeScale);
        loadImages("imagelist", this.imageStore, this);
        loadWorld(this.world, "world.sav", this.imageStore);
        scheduleActions(this.world, this.scheduler, this.imageStore);
        this.next_time = System.currentTimeMillis() + 100L;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= this.next_time) {
            this.scheduler.updateOnTime(time);
            this.next_time = time + 100L;
        }

        Viewport.drawViewport(this.view);
    }

    public void keyPressed() {
        if (this.key == '\uffff') {
            int dx = 0;
            int dy = 0;
            switch(this.keyCode) {
                case 37:
                    dx = -1;
                    break;
                case 38:
                    dy = -1;
                    break;
                case 39:
                    dx = 1;
                    break;
                case 40:
                    dy = 1;
            }

            this.view.shiftView(dx, dy);
        }

    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background("background_default", imageStore.getImageList("background_default"));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, 1);
        img.loadPixels();

        for(int i = 0; i < img.pixels.length; ++i) {
            img.pixels[i] = color;
        }

        img.updatePixels();
        return img;
    }

    private static void loadImages(String filename, ImageStore imageStore, PApplet screen) {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        } catch (FileNotFoundException var4) {
            System.err.println(var4.getMessage());
        }

    }

    public static void loadWorld(WorldModel world, String filename, ImageStore imageStore) {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.load(in, world);
        } catch (FileNotFoundException var4) {
            System.err.println(var4.getMessage());
        }

    }

    public static void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Iterator var3 = world.entities.iterator();

        while(var3.hasNext()) {
            Entity entity = (Entity)var3.next();
            if (entity.getActionPeriod() > 0) {
                entity.scheduleActions(scheduler, world, imageStore);
            }
        }

    }

    public static void parseCommandLine(String[] args) {
        String[] var1 = args;
        int var2 = args.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String arg = var1[var3];
            byte var6 = -1;
            switch(arg.hashCode()) {
                case 1288310:
                    if (arg.equals("-faster")) {
                        var6 = 1;
                    }
                    break;
                case 39937757:
                    if (arg.equals("-fastest")) {
                        var6 = 2;
                    }
                    break;
                case 44694025:
                    if (arg.equals("-fast")) {
                        var6 = 0;
                    }
            }

            switch(var6) {
                case 0:
                    timeScale = Math.min(0.5D, timeScale);
                    break;
                case 1:
                    timeScale = Math.min(0.25D, timeScale);
                    break;
                case 2:
                    timeScale = Math.min(0.1D, timeScale);
            }
        }

    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class, new String[0]);
    }
}
