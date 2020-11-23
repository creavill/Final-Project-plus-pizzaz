import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public final class VirtualWorld extends PApplet {
    public static final int TIMER_ACTION_PERIOD = 100;
//    public static final int VIEW_WIDTH = 640;
//    public static final int VIEW_HEIGHT = 480;
    public static final int VIEW_WIDTH = 1248;
    public static final int VIEW_HEIGHT = 768;
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    public static final int WORLD_WIDTH_SCALE = 2;
    public static final int WORLD_HEIGHT_SCALE = 2;

    public static final int VIEW_COLS = 40;
    public static final int VIEW_ROWS = 30;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

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
    public MainCat cat;


    private ColorRGB green = new ColorRGB(0,255,0);
    private ColorRGB red = new ColorRGB(255,0,0);
    //private ColorRGB blue = new ColorRGB(0,0,255);

    private Rectangle health = new Rectangle(new Point(960, 32), new Point(1248,0), green);
    private Rectangle fullness = new Rectangle(new Point(960, 64), new Point(1248,32), red);

    public VirtualWorld() {
    }

    public void settings() {
        this.size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    public void setup() {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS, createDefaultBackground(this.imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, this.world, TILE_WIDTH, TILE_HEIGHT);
        //this.view.shiftView(10, 10);
        this.scheduler = new EventScheduler(timeScale);
        loadImages(IMAGE_LIST_FILE_NAME, this.imageStore, this);
        loadWorld(this.world, LOAD_FILE_NAME, this.imageStore);
        scheduleActions(this.world, this.scheduler, this.imageStore);
        this.next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;

        for (Iterator<Entity> it = this.world.entities.iterator(); it.hasNext(); ) {
                Entity e = it.next();
                if (e instanceof MainCat)
                    cat = (MainCat)e;
            }
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= this.next_time) {
            this.scheduler.updateOnTime(time);
            this.next_time = time + TIMER_ACTION_PERIOD;
        }

        Viewport.drawViewport(this.view);
    }

    public void keyPressed() {
        if (this.key == CODED) {
            int dx = 0;
            int dy = 0;
            switch (keyCode)
            {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
          //  WorldModel.moveEntity()
//            MainCat cat = new MainCat("mainCat", new Point(2,2),
//                    this.imageStore.getImageList(Cat.CAT_KEY  ), 4, 4);
//            for (Iterator<Entity> it = this.world.entities.iterator(); it.hasNext(); ) {
//                Entity e = it.next();
//                if (e instanceof MainCat)
//                    cat = (MainCat)e;
//            }
            System.out.println(cat.getPosition().getX());
            cat.moveCat(world,view,dx,dy);
            /*
                Only view.shiftview if the x and y value are not center
             */
        }

    }

    public void mousePressed(){
        System.out.println("mouse pressed");

    }



    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
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
                if (entity instanceof ScheduledAction){
                    ((ScheduledAction)entity).scheduleActions(scheduler, world, imageStore);
                } else if (entity instanceof ScheduledAnimation){
                    ((ScheduledAnimation)entity).scheduleActions(scheduler, world, imageStore);
                }
            }
        }

    }

    /*public static void parseCommandLine(String[] args) {
        String[] var1 = args;
        int var2 = args.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String arg = var1[var3];
            byte var6 = -1;
            switch(arg.hashCode()) {
                case 1288310:
                    if (arg.equals(FASTER_FLAG)) {
                        var6 = 1;
                    }
                    break;
                case 39937757:
                    if (arg.equals(FASTEST_FLAG)) {
                        var6 = 2;
                    }
                    break;
                case 44694025:
                    if (arg.equals(FAST_FLAG)) {
                        var6 = 0;
                    }
            }

            switch(var6) {
                case 0:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case 1:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case 2:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
            }
        }
    }*/
    private static void parseCommandLine(String [] args)
    {
        for (String arg : args)
        {
            switch (arg)
            {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class, new String[0]);
    }
}
