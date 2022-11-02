package one.empty3.feature20220726;

import one.empty3.library.Point2D;

import java.util.Iterator;
import java.util.function.Consumer;

public class Line {
    private int size;
    static MultiLinkList xys = new MultiLinkList();
    private int index;

    public Line(int... xys) {
        int n = xys.length / 2;


        for (int n2 = 0; n2 <= xys.length - 4; n2 += 4
        ) {
            index =
                    this.xys.add(new P2P2(new Point2D(xys[n2], xys[n2 + 1]),
                            new Point2D(xys[n2 + 2], xys[n2 + 3])
                    ));
            size = xys.length / 2;
            /*
            this.xys.add(new MultiLinkList.P2P2(new Point2D(xys[n2], xys[n2 + 1]),
                    new Point2D(xys[n2 + 1], xys[n2 + 2])
            ));

             */
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double dist(Line l2) {
        return 10.0;
    }

    public int dist(Point2D pPlus) {
        final Point2D[] pCandidat = {null};
        int[] i = {0};
        final int[] x = {-1};
        new MyIterator(this).forEachRemaining(new Consumer<Point2D>() {

            @Override
            public void accept(Point2D point2D) {
                if (Point2D.dist(point2D, pPlus) < 1.1) {
                    pCandidat[0] = point2D;
                    x[0] = i[0]++;
                }
            }
        });
        return x[0];
    }

    public boolean join(Line l2) {
        int[] i = {0};
        new MyIterator(l2).forEachRemaining(new Consumer<Point2D>() {
            @Override
            public void accept(Point2D point2D) {
                int dist = dist(point2D);
                if (dist == 0 && dist == xys.size() - 1) {
                    //xys.add(dist, l2.xys, dist);
                }
            }
        });
        return false;
    }


}

class MyIterator<T> implements Iterator {

    private final Line line;
    private int current = -1;
    private boolean p1 = true;

    public MyIterator(Line line) {
        this.line = line;
    }

    @Override
    public boolean hasNext() {
        return !p1 || (current + 1) < line.xys.size();
    }

    @Override
    public Point2D next() {
        p1 = !p1;
        if (p1) {
            p1 = false;
            current++;
        } else if (!p1) {
            p1 = true;
        }
        if (p1) return line.xys.get(current).getP1();
        return line.xys.get(current).getP0();
    }


}
