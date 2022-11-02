package one.empty3.library.core.lighting;

import one.empty3.library.*;

public class Material {
    double Ka, Kd, Ks;
    Representable r;

    public Material(double Ka, double Kd, double Ks, Representable r) {
        this.r = r;
        this.Ka = Ka;
        this.Kd = Kd;
        this.Ks = Ks;
    }

    public double getKa() {
        return Ka;
    }

    public double getKd() {
        return Kd;
    }

    public double getKs() {
        return Ks;
    }

    public Representable getRe() {
        return r;
    }

}
