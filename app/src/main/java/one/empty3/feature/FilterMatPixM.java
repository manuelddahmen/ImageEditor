package one.empty3.feature;

import one.empty3.feature.M3;

public abstract class FilterMatPixM {
/*
    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }
*/

    public abstract one.empty3.feature.M3 filter(one.empty3.feature.M3 original);

    public abstract void element(one.empty3.feature.M3 source, one.empty3.feature.M3 copy, int i, int j, int ii, int ij);

    public abstract one.empty3.feature.M3 norm(one.empty3.feature.M3 m3, M3 copy);
}
