package one.empty3.feature_arr_style;

public class DefineFilter extends FilterPixGMatrix {


    public DefineFilter(double[][] matrix, double divider) {
        super(matrix.length, matrix[0].length);
        fill(matrix);
    }

    @Override
    public double filter(double x, double y) {
        return 1.0 * get((int) x - columns / 2, (int) y - lines / 2);
    }

    /***
     * Gaussian filter Matrix
     * @param matrix 2d double
     */


    public void fill(double[][] matrix) {
        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    set(i, j, matrix[j][i]);
                }
            }
        }

    }
}
