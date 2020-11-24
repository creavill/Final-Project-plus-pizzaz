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
    public static WorldModel world;
    public WorldView view;
    public EventScheduler scheduler;
    public long next_time;
    public static MainCat cat;
    private boolean start=true;
    private boolean lose =false;
    private boolean win =false;

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
                    this.cat = (MainCat)e;
            }
    }


    public void draw() {
        if(start) {
            fill(153,204,255);
            rect(0,0,1300,800);
            fill(0,0,0);
            textSize(50);
            text("TITLE OF THE GAME (FILLER)", 350, 200);
            textSize(25);
            text("Use ARROW KEYS to move cat and eat mice ", 350, 270);
            text("WATCH OUT for Dogs!!", 350, 340);
            text("CLICK to add more Cheese and Mice", 350, 410);
            text("Lose 9 lives to lose :(", 350, 480);
            text("Eat 18 Mice to win!", 350, 550);
            text("Press SPACE to start", 350, 620);
        }
        if(win){
            fill(0,155,0);
            rect(0,0,1300,800);
            fill(0,0,0);
            textSize(50);
            text("U win", 350, 200);
            textSize(25);
        }
        if(lose){
            fill(155,0,0);
            rect(0,0,1300,800);
            fill(0,0,0);
            textSize(50);
            text("U lsoe", 350, 200);
            textSize(25);
        }
        if(!start){
            if(cat.livesLost==9){
                lose=true;
                start=true;
            }
            else if(cat.fullness==18){
                win=true;
                start=true;
            }
            long time = System.currentTimeMillis();
            if (time >= this.next_time) {
                this.scheduler.updateOnTime(time);
                this.next_time = time + TIMER_ACTION_PERIOD;
            }

            Viewport.drawViewport(this.view);
            strokeWeight(3);
            fill(0, 255, 0);
            stroke(0, 0, 0);
            rect(965, 0, 288, 32);
            fill(153, 204, 255);
            rect(965, 32, 288, 32);
            fill(255, 0, 0);
            rect(1248 - cat.livesLost * 32, 0, 288, 32);
            rect(965 + cat.fullness * 16, 32, 288, 32);
            fill(0, 0, 0);
            textSize(15);
            text("hunger", 970, 47);
            text("health", 970, 15);
        }
    }

    public void keyPressed() {
        if(key==' '){
            if(win||lose){
               win=false;
               lose=false;
            }
            else {
                start = false;
            }
        }
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
            this.cat.moveCat(world,view,dx,dy,scheduler);
        }

    }

    public void mousePressed(){
        System.out.println("mouse pressed");
        System.out.println("X: "+ mouseX+ " Y: " + mouseY);
        Point randomPosition= new Point((int)random(39),(int)random(29));
        Point randomPositionCAT= new Point((int)random(39),(int)random(29));
        Point randomPositionDOG= new Point((int)random(39),(int)random(29));
        int dogOrCat= (int) random(0,1);
        try {
            Entity cheese = new Cheese("cheese", new Point(mouseX / 32, mouseY / 32), imageStore.getImageList(Cheese.CHEESE_KEY), 4);
            world.tryAddEntity(cheese);
            MouseNotFull entity = MouseNotFull.createMouseNotFull("mouse", 2, randomPosition, 954, 100, imageStore.getImageList("mouse"));
            world.tryAddEntity(entity);
            entity.scheduleActions(scheduler, world, imageStore);
            if(dogOrCat==1) {
                Cat cat = Cat.createCat("cat", randomPositionCAT, 100, imageStore.getImageList("cat"));
                world.tryAddEntity(cat);
                cat.scheduleActions(scheduler, world, imageStore);
            }else {
                Dog dog = Dog.createDog("dog", randomPositionDOG, 5000, 2, imageStore.getImageList("dog"));
                world.tryAddEntity(dog);
                dog.scheduleActions(scheduler, world, imageStore);
            }

        }
        catch(IllegalArgumentException e){
            return;
        }

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

    public static void killCat(){
        cat.death(world);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var9) {
            var9.printStackTrace();
        }
        cat.livesLost+=1;

    }


    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class, new String[0]);
    }
}
