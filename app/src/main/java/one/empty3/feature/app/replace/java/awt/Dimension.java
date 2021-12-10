package one.empty3.feature.app.replace.java.awt;

public class Dimension {
    private double width;
    private double height;

    public Dimension(double width, double height) {
        this.width = width;
        this.height = height;
    }
    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
