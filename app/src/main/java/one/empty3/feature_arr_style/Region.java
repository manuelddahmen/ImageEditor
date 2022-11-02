package one.empty3.feature_arr_style;

public class Region extends AreaDescriptor {
    public Region(int x, int y, int sizeX, int sizeY) {
        super(x, y, sizeX, sizeY);
    }

    @Override
    public FilterPixGMatrix getFilter() {
        return null;
    }

    @Override
    public double match() {
        return 0;
    }
}
