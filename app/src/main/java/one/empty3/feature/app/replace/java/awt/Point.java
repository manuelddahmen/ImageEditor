package one.empty3.feature.app.replace.java.awt;

public class Point {

    public double x;
    public double y;

    public Point(int x2, int y2) {
        this.x = x2;
        this.y = y2;
    }

    public Point(Point p1) {
        this.x = p1.x;
        this.y = p1.y;
    }

    public static double distance(double x, double y, double x1, double y1) {
        return Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void set(double v, double v1) {
        this.x = v;
        this.y = v1;
    }
}
