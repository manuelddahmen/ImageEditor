package one.empty3.feature20220726.selection;

import one.empty3.library.Point3D;

public class RectColor {


    private Point3D color;
    private double x, y;
    private double width, height;

    public RectColor() {

    }

    public RectColor(double x, double y, double width, double height, Point3D color) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Point3D getColor() {
        return color;
    }

    public void setColor(Point3D color) {
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
