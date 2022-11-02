package one.empty3.feature20220726;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomialeBezier;

public class CourbeN11 extends CourbeParametriquePolynomialeBezier {
    private StructureMatrix<Integer> pointsCount = new StructureMatrix<>(0, Integer.class);
    private StructureMatrix<Boolean> closed = new StructureMatrix<>(0, Boolean.class);

    {
        pointsCount.setElem(20);
    }

    int INCR_SUBCURVE = 10;

    public CourbeN11(Point3D[] point3DS) {
        super(point3DS);
        closed.setElem(false);
    }

    public CourbeN11() {
        super();
        closed.setElem(false);
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("pointsCount/Nombre de points intermédiaires div 2", pointsCount);
        getDeclaredDataStructure().put("closed/Courbe fermee ou ouverte", closed);
    }

    @Override
    public Point3D calculerPoint3D(double t) {
        int size = coefficients.getData1d().size();
        double t2 = t;
        return calculerPoint3D(t2, (int) (t2 * size));
    }

    public Point3D calculerPoint3D_N(double t) {
        int N = 0;
        int size = coefficients.getData1d().size();
        Point3D sum = Point3D.O0;
        for (int j = -N; j <= N; j++) {
            double t2 = t + (1.0 / size) * (j);
            sum = sum.plus(calculerPoint3D(t2, (int) (t2 * size)));
        }
        return sum.mult(1. / (N * 2 + 1));
    }

    public Point3D calculerPoint3D(double t, int i) {
        if (closed.getElem())
            return calculerPoint3DClosedCurve(t, i);
        Point3D sum = new Point3D();
        int N = coefficients.getData1d().size();
        int start = Math.max(0, i - pointsCount.getElem());
        int end = Math.min(N, i + pointsCount.getElem());
        int n = end - start;
        double tFract = (t * N - (int) (t * N)); // tFract := t mod N
        if (getIncrU().getElem() != 1. / n / INCR_SUBCURVE)
            setIncrU(1. / n / INCR_SUBCURVE);
        for (i = start; i < end; i++) {
            sum = sum.plus(coefficients.getElem(i).mult(B(i - start, n - 1, tFract)));
        }
        return sum;
    }

    public Point3D calculerPoint3DClosedCurve(double t, int i) {
        Point3D sum = new Point3D();
        int N = coefficients.getData1d().size();
        int start = i - pointsCount.getElem();
        int end = Math.min(N, i + pointsCount.getElem());
        int n = end - start;
        int i0 = start;
        int i1 = end;
        if (start < 0) start = N + start;
        if (end > N - 1) end = end % (N);
        double tFract = (t * N - (int) (t * N)); // tFract := t mod N
        if (getIncrU().getElem() != 1. / n / INCR_SUBCURVE)
            setIncrU(1. / n / INCR_SUBCURVE);
        for (i = 0; i < n; i++) {
            int idxArr = (N + start + i) % N;
            sum = sum.plus(coefficients.getElem((N + idxArr) % N).mult(B(i, n - 1, tFract)));
        }
        return sum;
    }

    public StructureMatrix<Integer> getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(StructureMatrix<Integer> pointsCount) {
        this.pointsCount = pointsCount;
    }

    public StructureMatrix<Boolean> getClosed() {
        return closed;
    }

    public void setClosed(StructureMatrix<Boolean> closed) {
        this.closed = closed;
    }

    public int getINCR_SUBCURVE() {
        return INCR_SUBCURVE;
    }

    public void setINCR_SUBCURVE(int INCR_SUBCURVE) {
        this.INCR_SUBCURVE = INCR_SUBCURVE;
    }
}
