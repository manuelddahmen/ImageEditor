package one.empty3.feature;

public class V extends M {
    public V(int l, int c) {
        super(l, c);
    }

    public V(int lc) {
        super(lc, 1);


    }

    public M outerProduct(V vec1, V vec2) {
        M m1 = new M(vec2.columns, vec1.columns);
        for (int m = 0; m < vec1.columns; m++) { // line incr

            for (int n = 0; n < vec2.columns; n++) {
                m1.set(m, n, vec1.get(1, m) * vec2.get(1, n));
            }

        }
        return m1;
    }
}
