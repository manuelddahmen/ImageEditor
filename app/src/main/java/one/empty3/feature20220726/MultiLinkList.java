package one.empty3.feature20220726;

import one.empty3.library.Point2D;

import java.util.Objects;

class P2P2 {
    private Point2D p0;
    private Point2D p1;

    public P2P2(Point2D p0, Point2D p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public Point2D getP0() {
        return p0;
    }

    public void setP0(Point2D p0) {
        this.p0 = p0;
    }

    public Point2D getP1() {
        return p1;
    }

    public void setP1(Point2D p1) {
        this.p1 = p1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof P2P2)) return false;
        P2P2 p2P2 = (P2P2) o;
        return Objects.equals(getP0(), p2P2.getP0()) &&
                Objects.equals(getP1(), p2P2.getP1());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP0(), getP1());
    }
}

public class MultiLinkList {
    static double[] xy = new double[300000000];
    static int maxIndex = 0;
    private int index;


    public void reset() {
        xy = new double[30000000];
        maxIndex = -4;
    }

    public P2P2 get(int i) {
        i = i * 4;
        return new P2P2(new Point2D(xy[i], xy[i + 1]), new Point2D(xy[i + 2], xy[3]));
    }

    public int add(P2P2 p2P2) {
        int i = maxIndex;
        xy[i] = p2P2.getP0().getX();
        xy[i + 1] = p2P2.getP0().getY();
        xy[i + 2] = p2P2.getP1().getX();
        xy[i + 3] = p2P2.getP1().getY();
        maxIndex += 4;
        return maxIndex / 4;
    }

    public int size() {
        return maxIndex / 4;
    }
    /*
    public void add(int i, MultiLinkList list, int listItemAt) {
        add(new P2P2(get(i).getP0(), list.get(listItemAt).getP0()));
        for(int j=listItemAt; j<list.size()-1; j++)
            add(new P2P2(list.get(j).getP1(), list.get(j+1).getP0()));
        add(new P2P2(list.get(list.size()-2).getP0(), list.get(list.size()-1).getP0()));

        add(new P2P2(get(i).getP0(), list.get(listItemAt-1).getP1()));
        for(int j=listItemAt; j>0; j--)
            add(new P2P2(list.get(j).getP1(), list.get(j-1).getP0()));
        add(new P2P2(list.get(0).getP1(), list.get(0).getP0()));

    }

     */

}
