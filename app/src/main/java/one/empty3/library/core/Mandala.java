package one.empty3.library.core;

import one.empty3.library.Representable;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

/***
 * curve. rotation. straight, curved. turtle
 * motif repeat at angle, copy paste
 * ex from center flow rotate draw
 * borders in patterns 


 */
public class Mandala extends Representable {
    private final double resX;
    private final double resY;
    private ParametricSurface p;

    public Mandala(ParametricSurface plan, double resX, double resY) {
        this.p = plan;
        this.resX = resX;
        this.resY = resY;
    }

    public void addCurveX0_1__Y0_1(
            ParametricCurve pc, double radius1,
            double radius2, double angleRepeatDegree) {

    }
}
