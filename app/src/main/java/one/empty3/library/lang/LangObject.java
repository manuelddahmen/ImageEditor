package one.empty3.library.lang;

public class LangObject {
    private boolean objectType = false;
    private Scalar scalarType;
    private Object javaMainTypeObject;
    private Object javaClassObject;
    public LangObject(Scalar scalarType, Object javaMainTypeObject, int dim) {
        objectType = false;
        this.scalarType = scalarType;
        this.javaMainTypeObject = javaMainTypeObject;
        this.dim = dim;
    }
    public LangObject(Object javaClassObject, int dim) {
        objectType = true;
        this.javaClassObject = javaClassObject;
        this.dim = dim;
    }

    public enum Scalar {

        Double,
        Integer,
        Character,
        Boolean,

    }
    public int dim = 0;

    public Scalar getScalarType() {
        return scalarType;
    }

    public void setScalarType(Scalar scalarType) {
        this.scalarType = scalarType;
    }

    public Object getJavaMainTypeObject() {
        return javaMainTypeObject;
    }

    public void setJavaMainTypeObject(Object javaMainTypeObject) {
        this.javaMainTypeObject = javaMainTypeObject;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    @Override
    public String toString() {
        return "LangObject{" +
                "scalarType=" + scalarType +
                ", javaMainTypeObject=" + javaMainTypeObject +
                ", dim=" + dim +
                '}';
    }
}
