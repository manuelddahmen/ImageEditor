package javaAnd.awt;

public class Point {

    public double x;
    public double y;

    public Point(int x2, int y2) {
        this.x = x2;
        this.y = y2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point(" +
                "x=" + x +
                ", y=" + y +
                ')';
    }
}
