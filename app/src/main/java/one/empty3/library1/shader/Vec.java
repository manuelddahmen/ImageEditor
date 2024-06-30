/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.library1.shader;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Vec {
    private int dims;
    private final StructureMatrix<Double> vecVal
            = new StructureMatrix<>(1, Double.class);

    private final StructureMatrix<Vec> vec
            = new StructureMatrix<>(1, Vec.class);

    public Vec(Point3D p) {
        for (int i = 0; i < 3; i++) {

            vecVal.add(p.get(i));
        }
    }

    public Vec(Double d) {
        vecVal.getData1d().add(d);
    }

    public Vec(int d) {
        for (int i = 0; i < d; i++)
            vecVal.getData1d().add(0.0);
    }

    public Vec(Double... comps) {
        for (Double d : comps) {
            vecVal.add(1, d);
        }
    }

    public Vec(double[] comps) {
        for (Double d : comps) {
            vecVal.add(1, d);
        }
    }

    public Vec(Vec... comps) {
        int i = 0;
        for (Vec comp : comps) {
            vec.add(comp);

            for (int j = 0; j < comp.size(); j++) {

                vecVal.setElem(comp.get(j), i);
                i++;
            }
        }
    }

    public double get(int i) {
        return vecVal.getElem(i);
    }


    public int getDims() {
        int dims = 0;
        if (!vecVal.getData1d().isEmpty()) {
            dims += vecVal.getData1d().size();
        }
        return dims;


    }

    @NotNull
    public String toString() {
        StringBuilder s = new StringBuilder("vec (" + getDims() + ") " +
                "(");
        if (!vecVal.getData1d().isEmpty())
            for (int i = 0; i < vecVal.getData1d().size();
                 i++)
                s.append(vecVal.
                        getElem(i)).append(", ");
        else
            for (int i = 0; i < vec.getData1d().size();
                 i++)
                s.append(vec.
                        getElem(i).toString()).append(", ");
        s.append(")");
        return s.toString();
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

    public void set(int i, Double eval) {
        vecVal.setElem(eval, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec vec1 = (Vec) o;
        return dims == vec1.dims && Objects.equals(vecVal, vec1.vecVal) && Objects.equals(vec, vec1.vec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dims, vecVal, vec);
    }

    public void setDims(int dims) {
        this.dims = dims;
    }

    public StructureMatrix<Double> getVecVal() {
        return vecVal;
    }

    public StructureMatrix<Vec> getVec() {
        return vec;
    }

    public void setValues(Double... values) {
        for (int i = 0; i < values.length; i++) {
            Double v = values[i];
            vecVal.setElem(v, i);
        }
    }

    public Vec add(Vec v) {
        Vec ret = new Vec(this);
        for (int i = 0; i < ret.vecVal.getData1d().size(); i++) {
            ret.set(i, ret.get(i) + v.get(i));
        }
        return ret;
    }

    public Vec multiply(Double d) {
        Vec ret = new Vec(this);
        for (int i = 0; i < ret.vecVal.getData1d().size(); i++) {
            ret.set(i, ret.get(i) * d);
        }
        return ret;
    }
}
