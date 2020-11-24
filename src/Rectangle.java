public class Rectangle {
    private Point min;
    private Point max;
    private String id;
    private ColorRGB col;

    public Rectangle(Point min, Point max, ColorRGB col) {
        this.min = min;
        this.max = max;
        this.id = id;
        this.col = col;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ColorRGB getCol() {
        return col;
    }

    public void setCol(ColorRGB col) {
        this.col = col;
    }
}
