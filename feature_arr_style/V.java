package one.empty3.feature_arr_style;

public class V extends GMatrix {
    public V(int l, int c) {
        super(l, c);
    }

    public V(int lc) {
        super(lc, 1);


    }

    public GMatrix outerProduct(V vec1, V vec2) {
        GMatrix GMatrix1 = new GMatrix(vec2.columns, vec1.columns);
        for (int m = 0; m < vec1.columns; m++) { // line incr

            for (int n = 0; n < vec2.columns; n++) {
                GMatrix1.set(m, n, vec1.get(1, m) * vec2.get(1, n));
            }

        }
        return GMatrix1;
    }
}
