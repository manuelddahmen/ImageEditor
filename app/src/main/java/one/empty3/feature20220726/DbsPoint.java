package one.empty3.feature20220726;

public class DbsPoint implements DataPoint {
    private double[] point = new double[2];
    private int clusterId;

    public DbsPoint(double[] d) {
        this.point = d;
    }

    @Override
    public double distance(DataPoint datapoint) {
        return Math.sqrt(
                (datapoint.getX() - point[0]) * (datapoint.getX() - point[0])
                        + (datapoint.getY() - point[1]) * (datapoint.getY() - point[1])
        );
    }

    @Override
    public void setCluster(int id) {
        clusterId = id;
    }

    @Override
    public int getCluster() {
        return clusterId;
    }

    @Override
    public int getX() {
        return (int) point[0];
    }

    @Override
    public int getY() {
        return (int) point[1];
    }
}
