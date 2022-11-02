package one.empty3.library;

public class RepresentableT {
    protected double t, dt;
    private PointListMove propertiesMoves;

    public double getTD() {
        return t;
    }

    public void setTD(double t) {
        this.t = t;
    }

    public void next() {
        propertiesMoves.next();
    }
}
