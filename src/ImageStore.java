import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

final class ImageStore {
    public Map<String, List<PImage>> images = new HashMap();
    public List<PImage> defaultImages = new LinkedList();

    public ImageStore(PImage defaultImage) {
        this.defaultImages.add(defaultImage);
    }

    public List<PImage> getImageList(String key) {
        return (List)this.images.getOrDefault(key, this.defaultImages);
    }

    public void loadImages(Scanner in, PApplet screen) {
        for(int lineNumber = 0; in.hasNextLine(); ++lineNumber) {
            try {
                this.processImageLine(in.nextLine(), screen);
            } catch (NumberFormatException var5) {
                System.out.println(String.format("Image format error on line %d", lineNumber));
            }
        }

    }

    public void processImageLine(String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = this.getImages(key);
                imgs.add(img);
                if (attrs.length >= 5) {
                    int r = Integer.parseInt(attrs[2]);
                    int g = Integer.parseInt(attrs[3]);
                    int b = Integer.parseInt(attrs[4]);
                    Functions.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }

    }

    public List<PImage> getImages(String key) {
        List<PImage> imgs = (List)this.images.get(key);
        if (imgs == null) {
            imgs = new LinkedList();
            this.images.put(key, imgs);
        }

        return (List)imgs;
    }

    public void load(Scanner in, WorldModel world) {
        for(int lineNumber = 0; in.hasNextLine(); ++lineNumber) {
            try {
                if (!processLine(in.nextLine(), world, this)) {
                    System.err.println(String.format("invalid entry on line %d", lineNumber));
                }
            } catch (NumberFormatException var5) {
                System.err.println(String.format("invalid entry on line %d", lineNumber));
            } catch (IllegalArgumentException var6) {
                System.err.println(String.format("issue on line %d: %s", lineNumber, var6.getMessage()));
            }
        }

    }

    public static boolean processLine(String line, WorldModel world, ImageStore imageStore) {
        String[] properties = line.split("\\s");
//
//        System.out.println("dog".hashCode());

        if (properties.length > 0) {
            String var4 = properties[0];
//            if(!properties[0].equals("background")) {
//                System.out.println(properties[0]);
//            }
            byte var5 = -1;
            switch(var4.hashCode()) {
                case -1332194002:
                    if (var4.equals("background")) {
                        var5 = 0;
                    }
                    break;
                case 3143256:
                    if (var4.equals("fish")) {
                        var5 = 3;
                    }
                    break;
                case 104086693:
                    if (var4.equals("mouse")) {
                        var5 = 1;
                    }
                    break;
                case 310044248:
                    if (var4.equals("atlantis")) {
                        var5 = 4;
                    }
                    break;
                case 361935503:
                    if (var4.equals("obstacle")) {
                        var5 = 2;
                    }
                    break;
                case 849374887:
                    if (var4.equals("seaGrass")) {
                        var5 = 5;
                    }
            }

            switch(var5) {
                case 0:
                    return Background.parseBackground(properties, world, imageStore);
                case 1:
                    return imageStore.parseOcto(properties, world);
                case 2:
                    return imageStore.parseObstacle(properties, world);
                case 3:
                    return imageStore.parseCat(properties, world);
                case 4:
                    return imageStore.parseAtlantis(properties, world);
                case 5:
                    return imageStore.parseSgrass(properties, world);
            }
        }

        return false;
    }

    public static PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return (PImage)((Background)entity).images.get(((Background)entity).imageIndex);
        } else if (entity instanceof Entity) {
            return (PImage) ((Entity) entity).getImages().get(((Entity) entity).getImageIndex());
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", entity));
        }
    }

    public boolean parseOcto(String[] properties, WorldModel world) {
        if (properties.length == 7) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = MouseNotFull.createOctoNotFull(properties[1], Integer.parseInt(properties[4]), pt, Integer.parseInt(properties[5]), Integer.parseInt(properties[6]), this.getImageList("mouse"));
            world.tryAddEntity(entity);
        }

        return properties.length == 7;
    }

    public boolean parseObstacle(String[] properties, WorldModel world) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = Obstacle.createObstacle(properties[1], pt, this.getImageList("obstacle"));
            world.tryAddEntity(entity);
        }

        return properties.length == 4;
    }

    public boolean parseCat(String[] properties, WorldModel world) {
        if (properties.length == 5) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = Cat.createCat(properties[1], pt, Integer.parseInt(properties[4]), this.getImageList(Cat.CAT_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == 5;
    }

    public boolean parseAtlantis(String[] properties, WorldModel world) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = MouseHole.createMouseHole(properties[1], pt, this.getImageList(MouseHole.MOUSEHOLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == 4;
    }

    public boolean parseSgrass(String[] properties, WorldModel world) {
        if (properties.length == 5) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = Cheese.createCheese(properties[1], pt, Integer.parseInt(properties[4]), this.getImageList(Cheese.CHEESE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == 5;
    }
}
