import processing.core.PImage;

import java.util.Random;

final class Functions {
    public static final Random rand = new Random();

    Functions() {
    }

    public static PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return (PImage)((Background)entity).images.get(((Background)entity).imageIndex);
        } else if (entity instanceof Entity) {
            return (PImage)((Entity)entity).images.get(((Entity)entity).imageIndex);
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", entity));
        }
    }

    public static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & 16777215;
        img.format = 2;
        img.loadPixels();

        for(int i = 0; i < img.pixels.length; ++i) {
            if ((img.pixels[i] & 16777215) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }

        img.updatePixels();
    }
}
