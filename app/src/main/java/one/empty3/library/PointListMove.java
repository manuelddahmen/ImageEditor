package one.empty3.library;

import one.empty3.library.core.nurbs.*;

import java.util.List;

public class PointListMove {
    private RepresentableT[] points;

    //private StructureMatrix<String> formula = new StructureMatrix (1, String.class);
    //private StructureMatrix<ParametricCurve> curves = new StructureMatrix (1, ParametricCurve.class);
    class Slice {
        int type;


        int[] ordIndexes;
    }

    class Vec {
        double dt;
        protected List<Slice> vecSlices;

        public Double[] result() {
            return null;
        }

    }

    class SliceFormula extends Slice {
    }

    class SliceCurve extends Slice {
    }

    class SliceFunction extends Slice {
    }

    // Slices of ord.ex (0,1), curve, curveOp
    // tan, norm, cross, add... ???
    // collide
    public PointListMove(double t, double dt, RepresentableT... p) {
        this.points = p;
        //this.t = t;
        //this.dt = dt;
    }

    public void customFunction(String property) {

    }

    public void curve(String property, ParametricCurve curve) {
        //this.curves.addElem(curve);
    }

    /***
     * @param property structurematrix property name
     * @param formula vec{3}(f(ord(0), ord(1), ...))
     */
    public void formulaOrd(String property, String formula) {
        //this.formula = formula;

    }

    public void next() {
        for (RepresentableT rt : points) {
            // rt getproperty
            // rt set T ++
            // 2 compute Vec change p3 to vec
            // 1 choose type
            // 3 vec0.changeto vec1
        }
    }

}
