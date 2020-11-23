/* a Rectangle represents a rectangle which is given by two points (min and max) which can be seen as the two
corners of the rectangle that define its shape.
 upper right corner =min
 lower left corner = max
 (Note that the coordinate system used runs horizontally in x direction from left to right and vertically in y
 direction from up to down.
 There is a a scetch on the exam that should help with that.)
Every Rectangle also has an identifier String and a color.
 */

import java.util.List;

public class Rectangle {
    private Point min;
    private Point max;
    private ColorRGB col;


    public Rectangle(Point min, Point max, ColorRGB col){
        this.min=min;
        this.max=max;
        this.col=col;
    }

    public Point getMin() {
        return min;
    }

    public void setMin(Point min) {
        this.min = min;
    }

    public Point getMax() {
        return max;
    }

    public void setMax(Point max) {
        this.max = max;
    }

    public ColorRGB getCol() {
        return col;
    }

    public void setCol(ColorRGB col) {
        this.col = col;
    }


}

