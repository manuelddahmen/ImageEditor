package one.empty3.feature20220726;

public class GaussFilterPixM extends FilterPixM {
    private final PixM in;
    public double sigma = 0.8;

    public GaussFilterPixM() {
        super(3, 3);
        in = null;
    }

    public GaussFilterPixM(PixM in, int squareSize) {
        super(squareSize, squareSize);
        this.in = in;
    }

    @Override
    public double filter(double x, double y) {
        double ret = 0.0;
        for (double i = x - in.getLines() / 2; i < x + in.getLines() / 2; i++)
            for (double j = y - in.getColumns() / 2; j < y + in.getColumns() / 2; j++) {
                ret += in.get((int) i, (int) j) * Math.exp(
                        -((x - i) * (x - i) + (y - j) * (y - j))
                                / 2 / sigma / sigma) / 2 / Math.PI / sigma / sigma;
            }
        return ret;
    }

    /*
     * Gaussian filter Matrix
     * @param halfSquareSizeMinus1 n*n square distribution
     * @param sigma gauss parameter
     * @param pixM image
     */
    public GaussFilterPixM(PixM pixM, int halfSquareSizeMinus1, double sigma) {
        this(pixM, halfSquareSizeMinus1 * 2 + 1);
        this.sigma = sigma;
        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            fill();
        }
        setCompNo(0);
    }

    public void fill() {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < lines; j++) {
                set(i, j, filter(i - columns / 2,
                        j - lines / 2)
                );
            }
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }
}
