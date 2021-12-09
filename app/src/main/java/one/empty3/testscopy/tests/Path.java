package one.empty3.testscopy.tests;

import one.empty3.library.Representable;
import one.empty3.library.StructureMatrix;

public class Path {
    private Representable representable;
    private String propertyPathString;
    private int pathElemType;
    private int indexI;
    private int indexJ;
    private Object o;
    private StructureMatrix<Object> container;
    private StructureMatrix<Object> declaredProperty;


    public Path( StructureMatrix<Object> declaredProperty,
                 Object representable, String propertyPathString, int pathElemType, int indexI, int indexJ) {
        this.o = representable;
        this.representable = (Representable) representable;
        this.propertyPathString = propertyPathString;
        this.pathElemType = pathElemType;
        this.indexI = indexI;
        this.indexJ = indexJ;
        this.declaredProperty = declaredProperty;
    }


    public String getPropertyPathString() {
        return propertyPathString;
    }

    public void setPropertyPathString(String propertyPathString) {
        this.propertyPathString = propertyPathString;
    }

    public int getPathElemType() {
        return pathElemType;
    }

    public void setPathElemType(int pathElemType) {
        this.pathElemType = pathElemType;
    }

    public int getIndexI() {
        return indexI;
    }

    public void setIndexI(int indexI) {
        this.indexI = indexI;
    }

    public int getIndexJ() {
        return indexJ;
    }

    public void setIndexJ(int indexJ) {
        this.indexJ = indexJ;
    }

    public void setRepresentable(Representable representable) {
        this.representable = representable;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
    public StructureMatrix<Object> getDeclaredProperty() {
        return declaredProperty;
    }

    public void setDeclaredProperty(StructureMatrix<Object> declaredProperty) {
        this.declaredProperty = declaredProperty;
    }
}
