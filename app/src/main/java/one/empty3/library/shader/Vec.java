package one.empty3.library.shader;

import one.empty3.library.*;

public class Vec {
    private int dims;
    private StructureMatrix<Double> vecVal
            = new StructureMatrix(1, Double.class);

    private StructureMatrix<Vec> vec
            = new StructureMatrix(1, Vec.class);

    public Vec(Point3D p) {
        for (int i = 0; i < 3; i++) {

            vecVal.add(p.get(i));
        }
    }

    public Vec(Double d) {
        vecVal.getData1d().add(d);
    }

    public Vec(int d) {
        for (int i = 0; i < d; d++)
            vecVal.getData1d().add(0.0);
    }

    public Vec(Double... comps) {
        for (int i = 0; i < comps.length; i++) {
            Double d = comps[i];
            vecVal.add(1, d);
        }
    }

    public Vec(Vec... comps) {
        for (int i = 0; i < comps.length; i++) {
            vec.add(comps[i]);

            for (int j = 0; j < comps[i].size(); j++) {

                vecVal.add(1, comps[i].get(j));
            }

        }
    }

    public double get(int i) {
        return vecVal.getElem(i);
    }


    public int getDims() {
        int dims = 0;
        if (vecVal.getData1d().size() > 0) {
            this.dims += vecVal.getData1d().size();
        }
        return dims;


    }

    public String toString() {
        String s = "vec" + getDims() +
                "(";
        if (vecVal.getData1d().size() > 0)
            for (int i = 0; i < vecVal.getData1d().size();
                 i++)
                s += vecVal.
                        getElem(i) + ", ";
        else
            for (int i = 0; i < vec.getData1d().size();
                 i++)
                s += vec.
                        getElem(i).toString() + ", ";
        s += ")";
        return s;
    }

    public double norme() {
        double d = 0.0;
        for (int i = 0; i < vecVal.getData1d().size(); i++)
            d += vecVal.getData1d().get(i) * vecVal.getData1d().get(i);
        return Math.sqrt(d);
    }

    /*
       public Double value(int i, int j) {
            if(i>=0 && i<j && j<= getDims() )
                 return new Vec(i,j);
            return vecVal.getData1d().get(i);
    }
    */
    public Double values(int i) {
        return vecVal.getData1d().get(i);
    }

    /*
    public Double value() {
        
        Double [] da;
        if(vecVal.getData1d().size()>0) {
            da = new Double[getDims() ];
            int i = 0;
            for(i=0;i<vecVal.getData1d().get(i); i++) {
                Double a = vecVal.getElem(i);
                da[i] = a;
                i++;
            } 

        } else {
            da = new Double[getDims()];
            int i = 0; // TODO
            int j = 0;

//Double [] d = new Double[ vec. getData1d. size()] ;
            for(i=0; i<vec.getData1d().size(); i++) {
             Double [] d = (Double[] )( vec.getElem(i)) ;
                for(Double a : d) {
                    da[j] = a;
                    j++;
                } 

            } 
        } 
    return da;
    } 
    */
    public int size() {
        return vecVal.getData1d().size();
    }
} 
