package one.empty3.feature20220726;

public abstract class FilterMatPixM {
/*
    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }
*/

    public abstract M3 filter(M3 original);

    public abstract void element(M3 source, M3 copy, int i, int j, int ii, int ij);

    public abstract M3 norm(M3 m3, M3 copy);
}
