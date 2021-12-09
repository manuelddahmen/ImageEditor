package one.empty3.tests;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;

public class Move {

    private final double time2;
    private final Object moved;
    private final Object o;
    private double time1;
    private String moveName;
    private double time;
    private Point3D[] positions;
    private String property;
    private Point3D vGlobal;
    private Point3D vMembre;
    /***
     *
     * @param moveName
     * @param o
     * @param property
     * @param time1
     * @param time2
     * @param moved
     */
    public Move(String moveName, Object o, String property, double time1, double time2,
                Object moved) {
        this.moveName = moveName;
        this.o = o;
        this.property = property;
        this.time1 = time1;
        this.time2 = time2;
        this.moved = moved;
    }

    public String getProperty() {
        return property;
    }


    public double getTime2() {
        return time2;
    }

    public Object getMoved() {
        return moved;
    }

    public Object getO() {
        return o;
    }

    public double getTime1() {
        return time1;
    }

    public void setTime1(double time1) {
        this.time1 = time1;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Point3D[] getPositions() {
        return positions;
    }

    public void setPositions(Point3D[] positions) {
        this.positions = positions;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Point3D getvGlobal() {
        return vGlobal;
    }

    public void setvGlobal(Point3D vGlobal) {
        this.vGlobal = vGlobal;
    }

    public Point3D getvMembre() {
        return vMembre;
    }

    public void setvMembre(Point3D vMembre) {
        this.vMembre = vMembre;
    }
}