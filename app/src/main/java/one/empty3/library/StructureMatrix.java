/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.library;


import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/*__
 * Created by manue on 07-09-19.
 */
public class StructureMatrix<T> implements Serializable, Serialisable {
    public static final int INSERT_ROW = 0;
    public static final int INSERT_COL = 1;
    private int dim;
    public T data0d;
    public List<T> data1d;
    public List<List<T>> data2d;
    private Class<?> classType;
    private StructureMatrix<Point3D> all;
    private Point3D center;

    public StructureMatrix() {
        dim = -1;

    }

    public void setClassType(Class T) {
        classType = T;
    }

    public StructureMatrix(int dim, Class classType) {
        init(dim, classType);
    }


    public void init(int dim, Class classType) {
        this.dim = dim;
        if (dim == 1)
            data1d = Collections.synchronizedList(new ArrayList<>());
        if (dim == 2)
            data2d = Collections.synchronizedList(new ArrayList<>());
        this.classType = classType;
    }

    public StructureMatrix<T> setElem(@NotNull T value) {
        dim = 0;
        if(value instanceof Point3D) {

        }
        this.data0d = value;
        if(value!=null)
            this.classType = value.getClass();
        listenersPropertyChanged(null, value, 0, 0, 0);
        return this;
    }

    public void setElem(T elem, int i) {
        if (i >= data1d.size()) {
            int j = data1d.size();
            while (j <= i) {
                if (getClassType().equals(Boolean.class)) {
                    data1d.add((T) Boolean.FALSE);
                } else if (getClassType().equals(Integer.class)) {
                    data1d.add((T) (Integer) 0);
                } else if (getClassType().equals(Double.class)) {
                    data1d.add((T) (Double) 0.);
                } else if (getClassType().equals(String.class)) {
                    data1d.add((T) "");
                } else {
                    data1d.add(null);
                }
                j++;
            }
        }
        getData1d().set(i, elem);
        listenersPropertyChanged(null, elem, 1, i, 0);
    }

    public void setElem(T elem, int i, int j) {

        this.classType = elem.getClass();
        if (data2d == null)
            data2d = Collections.synchronizedList(new ArrayList<>());
        while (i >= data2d.size()) {
            data2d.add(Collections.synchronizedList(new ArrayList<>()));
        }
        while (j >= data2d.get(i).size()) {
            data2d.get(i).add(elem);
        }
        data2d.get(i).set(j, elem);
        listenersPropertyChanged(null, elem, 2, i, j);
    }

    public T getElem(int[] indices) {
        if (dim == 0) {
            return this.data0d;
        }
        if (dim == 1) {
            return data1d.get(indices[0]);
        }
        if (dim == 2) {
            return data2d.get(indices[0]).get(indices[1]);
        }
        return null;
    }

    public T getElem() {

        if (dim == 0) {
            if(data0d!=null) {
                return this.data0d;
            } else {
                //System.out.println("null structureMatrix elem dim=0");
                return null;
            }
        }
        System.err.println("getElem dim= " + dim + "!=0");
        throw new UnsupportedOperationException("Error");
    }

    public T getElem(int i) {
        if (dim == 1) {
            return data1d.get(i);
        }
        System.err.println("getElem dim= " + dim + "!=1");
        return null;
    }

    public T getElem(int i, int j) {
        if (dim == 2) {
            return data2d.get(i).get(j);
        }
        System.err.println("getElem dim= " + dim + "!=2");
        return null;
    }

    public T getData0d() {
        return data0d;
    }

    public List<T> getData1d() {
        return data1d;
    }

    public List<List<T>> getData2d() {
        return data2d;
    }
    public boolean inBounds(int i, int j) {
        return dim==2 && i<getData2d().size() && j<getData2d().get(i).size();
    }
    public boolean inBounds(int i) {
        return dim==1 && i<getData1d().size();
    }
    public void insert(int pos, int rowCol, T value) {
        if (dim == 1)
            data1d.add(pos, value);
        if (dim == 2) {
            if (rowCol == INSERT_COL) {
                List<T> ins = Collections.synchronizedList(new ArrayList<T>());
                for (int i = 0; i < data2d.get(0).size(); i++) {
                    ins.add(value);
                    listenersPropertyChanged(null, value, dim, pos, i);
                }
                data2d.add(pos, ins);
                listenersPropertyChanged(null, value, dim, pos, 0);

            } else if (rowCol == INSERT_ROW) {
                for (int i = 0; i < data2d.size(); i++) {
                    data2d.get(i).add(pos, value);
                    listenersPropertyChanged(null, value, dim, i, pos);
                }
            }
        }
    }

    public void delete(int pos, int rowCol) {
        if (dim == 1)
            data1d.remove(pos);
        if (dim == 2) {
            if (rowCol == INSERT_ROW) {
                data2d.remove(pos);

            } else if (rowCol == INSERT_COL) {
                for (int i = 0; i < data2d.size(); i++) {
                    data2d.get(pos).remove(i);
                }
            }
        }
    }

    public void delete(int pos) {
        if (this.dim == 1) {
            this.data1d.remove(pos);
        }

    }

    public void insert(int i, T value) {
        if (dim == 1)
            data1d.add(i, value);
    }

    public void add(int dim, T value) {
        if (this.dim == 0) {
            data0d = value;
        }
        if (this.dim == 1) {
            data1d.add(value);
        }
        if (this.dim == 2) {
            int ind1 = data2d.size();
            data2d.get(ind1).add(value);
        }
    }

    public void add(T value) {
        if (this.dim == 0) {
            //data0d = value;
        }
        if (this.dim == 1) {
            data1d.add(value);
        }
        if (this.dim == 2) {
            //int ind1 = data2d.size();
            //data2d.get(ind1).add(value);
        }
    }

    public void addRow() {
        if (this.dim == 2) {
            data2d.add(Collections.synchronizedList(new ArrayList<T>()));
        }
    }

    boolean equals1(StructureMatrix<T> that) {
        if(data1d.size()==that.data1d.size()) {
            for (int i = 0; i < data1d.size(); i++) {
                if(!data1d.get(i).equals(that.data1d.get(i)))
                    return false;
            }
        }
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StructureMatrix<T> that = (StructureMatrix<T>) o;

        if (dim != that.dim) return false;
        if (dim == 0 && !data0d.equals(that.data0d)) return false;
        if ((dim == 1) && (data1d != null) && (that.data1d != null) &&
                (!equals1(that))) return false;
        if (dim == 2 && !data2d.equals(that.data2d)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = dim;
        result = 31 * result + data0d.hashCode();
        result = 31 * result + data1d.hashCode();
        result = 31 * result + data2d.hashCode();
        return result;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("structure(\n");
        s.append("(dim:" + dim + ")");
        switch (dim) {
            case 0:
                if(data0d !=null)
                    s.append("(data : {" + data0d.toString() + "} )");
                else
                    s.append("null 0d-data");
                break;
            case 1:
                s.append("(data : (");
                data1d.forEach(new Consumer<T>() {
                    @Override
                    public void accept(T t) {
                        s.append("(" + t.toString() + ")");
                    }
                });
                break;
            case 2:
                s.append("(data : (");
                data2d.forEach(new Consumer<List<T>>() {
                    @Override
                    public void accept(List<T> ts) {
                        s.append("( ");

                        ts.forEach(new Consumer<T>() {
                            @Override
                            public void accept(T t) {
                                s.append("(" + t.toString() + ")");
                            }
                        });

                        s.append(" )\n");

                    }
                });
                s.append("\n)\n");
                break;


        }
        return s.toString();
    }

    public String toStringLine() {
        StringBuilder s = new StringBuilder("structure(");
        s.append("(dim:" + dim + ")");
        switch (dim) {
            case 0:
                if(data0d !=null)
                    s.append("(data : {" + data0d.toString() + "} )");
                else
                    s.append("null 0d-data");
                break;
            case 1:
                s.append("(data : (");
                data1d.forEach(new Consumer<T>() {
                    @Override
                    public void accept(T t) {
                        s.append("(" + t.toString() + ")");
                    }
                });
                break;
            case 2:
                s.append("(data : (");
                data2d.forEach(new Consumer<List<T>>() {
                    @Override
                    public void accept(List<T> ts) {
                        s.append("( ");

                        ts.forEach(new Consumer<T>() {
                            @Override
                            public void accept(T t) {
                                s.append("(" + t.toString() + ")");
                            }
                        });

                        s.append(" )\n");

                    }
                });
                s.append(")");
                break;


        }
        return s.toString();
    }

    public Class getClassType() {
        return classType;
    }

    public void setAll(T[] all) {
        data1d = Collections.synchronizedList(new ArrayList<>());
        switch (dim) {
            case 1:
                data1d.clear();
                int i=0;
                for (T t : all) {
                    if (t != null)
                        setElem(t, i);
                    else
                        throw new NullPointerException("setAll elem == null");
                    i++;
                }
                break;
        }
    }

    public void setAll(T[][] all) {
        data2d = Collections.synchronizedList(new ArrayList<List<T>>());
        switch (dim) {
            case 2:
                for (int i = 0; i < all.length; i++) {
                    for (int j = 0; j < all[0].length; j++)
                        setElem(all[i][j], i, j);
                }

        }
    }

    public void setAll(ArrayList<T> all) {
        dim = 1;
        this.data1d = all;
    }

    public void reset() {
        data0d = null;
        if (dim == 1)
            data1d.clear();
        if (dim == 2)
            data2d.clear();
    }

    private void listenersPropertyChanged(Object oldValue, Object newValue, int dim, int posI, int posJ) {
        if (listeners.size() > 0) {
            listeners.forEach(listener ->
                    listener.actionOnChange(oldValue, newValue, dim, posI, posJ));
        }
    }

    List<StructureMatrixListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public void addListener(StructureMatrixListener listener) {
        listeners.add(listener);
    }

    public void deleteListener(StructureMatrixListener listener) {
        listeners.remove(listener);
    }

    private T cloneElement(T t) throws IllegalAccessException, CopyRepresentableError, InstantiationException {
        T t1 = null;
        if(t==null)
            return null;
        if (t instanceof StructureMatrix) {
            t1 = (T) ((StructureMatrix) t).copy();
        } else if(t instanceof Point3D) {
            t1 = (T) new Point3D(((Point3D)t).get(0),
                    ((Point3D)t).get(1),((Point3D)t).get(2));
        } else if (t instanceof MatrixPropertiesObject) {
            ((MatrixPropertiesObject) t).declareProperties();
            t1 = (T) ((MatrixPropertiesObject) t).copy();
        }
        return t1;
    }

    public StructureMatrix<T> copy() throws IllegalAccessException, CopyRepresentableError, InstantiationException {
        StructureMatrix<T> tStructureMatrix = new StructureMatrix<T>(this.getDim(), this.classType);
        switch (getDim()) {
            case 0:
                tStructureMatrix.data0d = cloneElement(data0d);
                break;
            case 1:
                tStructureMatrix.data1d = new ArrayList<>();
                if (data1d != null)
                    data1d.forEach(new Consumer<T>() {
                        @Override
                        public void accept(T t) {
                            try {
                                tStructureMatrix.add(1, cloneElement(t));
                            } catch (IllegalAccessException | CopyRepresentableError | InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                break;

            case 2:
                if (data2d != null) {

                    data2d.forEach(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> ts) {
                            ArrayList<T> objects = new ArrayList<>();
                            tStructureMatrix.data2d.add(objects);
                            ts.forEach(new Consumer<T>() {
                                @Override
                                public void accept(T t) {
                                    try {
                                        objects.add(cloneElement(t));
                                    } catch (IllegalAccessException | CopyRepresentableError |
                                             InstantiationException e) {
                                        e.printStackTrace();
                                    }


                                }
                            });

                        }

                    });
                }
                break;
        }
        return tStructureMatrix;

    }

    @Override
    public Serialisable decode(DataInputStream in) {
        StructureMatrix<T> structureMatrix = new StructureMatrix<>();

        try {
            structureMatrix.setDim(in.readInt());


            switch (getDim()) {

                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return structureMatrix;
    }

    @Override
    public int encode(DataOutputStream out) {

        try {
            out.write(getDim());
            switch (getDim()) {
                case 0: out.write(((Serialisable)data0d).type());
                    break;
                case 1:
                    getData1d().forEach(t -> {
                        try {
                            out.write(((Serialisable)t).type());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
                case 2:
                    getData2d().forEach(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> ts) {
                            ts.forEach(new Consumer<T>() {
                                @Override
                                public void accept(T t) {
                                    try {
                                        out.write(((Serialisable)t).type());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                        }
                    });

                    break;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int type() {
        return 2;
    }
}
