
final class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        //int result = result * 31 + this.x;
        result = result * 31 + this.y;
        return result;
    }

    public boolean adjacent(Point p2) {
        return this.x == p2.x && Math.abs(this.y - p2.y) == 1 || this.y == p2.y && Math.abs(this.x - p2.x) == 1;
    }

    public int distanceSquared(Point p2) {
        int deltaX = this.x - p2.x;
        int deltaY = this.y - p2.y;
        return deltaX * deltaX + deltaY * deltaY;
    }

    public int heuristic(Point other){
        return Math.abs(this.getX() - other.getX())
                + Math.abs(this.getY() - other.getY());
    }


}
