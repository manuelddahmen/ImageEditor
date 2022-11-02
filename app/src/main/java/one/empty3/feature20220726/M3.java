package one.empty3.feature20220726;

import javaAnd.awt.*;

import javaAnd.awt.Color;
import javaAnd.awt.image.BufferedImage;

import java.util.PrimitiveIterator;
import java.util.Random;

public class M3 {
    public static PrimitiveIterator.OfDouble r = new Random().doubles().iterator();
    public static final Double noValue = r.next();
    private double[] x;
    public int columns;
    public int lines;
    protected int columnsIn;
    protected int linesIn;
    protected int compNo;
    protected javaAnd.awt.image.BufferedImage image;
    private final int compCount = 4;
    private int currentX;
    private int currentY;
    private int savedY;
    private int savedX;
    private static int incrGetOut = 0;
    int incrOK = 0;

    public M3(int columns, int lines, int columnsIn, int linesIn) {
        this.lines = lines;
        this.columns = columns;
        this.linesIn = linesIn;
        this.columnsIn = columnsIn;
        init();
    }

    public M3(M3 original) {
        this(original.getColumns(), original.getLines(), original.getColumnsIn(), original.getLinesIn());
        for (int c = 0; c < getCompCount(); c++) {
            original.setCompNo(c);
            setCompNo(c);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    for (int ii = 0; ii < columnsIn; ii++)
                        for (int ij = 0; ij < linesIn; ij++) {
                            double d = original.get(i, j, ii, ij);
                            set(i, j, ii, ij, d);
                        }
                }
            }
        }
    }


    public M3(PixM pixM, int columnsIn, int linesIn) {
        this(pixM.getColumns(), pixM.getLines(), columnsIn, linesIn);
        for (int c = 0; c < getCompCount(); c++) {
            pixM.setCompNo(c);
            setCompNo(c);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < lines; j++) {
                    double d = pixM.get(i, j);
                    for (int ii = 0; ii < columnsIn; ii++)
                        for (int ij = 0; ij < linesIn; ij++) {
                            set(i, j, ii, ij, d);
                        }
                }
            }
        }
    }

    public M3(PixM pixM[][]) {
        this(pixM[0][0].getColumns(), pixM[0][0].getLines(), pixM.length, pixM[0].length);

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {

                for (int ii = 0; ii < columnsIn; ii++)
                    for (int ij = 0; ij < linesIn; ij++) {
                        for (int c = 0; c < getCompCount(); c++) {

                            pixM[ii][ij].setCompNo(c);

                            double d = pixM[ii][ij].get(i, j);

                            setCompNo(c);
                            set(i, j, ii, ij, d);
                        }
                    }
            }
        }

    }

    private void init() {
        x = new double[columns * lines * columnsIn * linesIn * compCount];
    }


    public M3(BufferedImage image, int columns, int lines, int columnsIn, int linesIn) {
        this(columns, lines, columnsIn, linesIn);
        float[] colorComponents = new float[getCompCount()];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                int rgb = image.getRGB((int) (1.0 * i / columns * image.getWidth()), (int) (1.0 * j / lines * image.getHeight()));
                colorComponents = Color.getColorComponents(Color.valueOf(rgb));
                for (int ii = 0; ii < columnsIn; ii++)
                    for (int ij = 0; ij < linesIn; ij++) {
                        for (int com = 0; com < getCompCount(); com++) {
                            setCompNo(com);
                            set(i, j, ii, ij, colorComponents[com]);
                        }
                    }
            }
        }
    }

    @Deprecated
    public M3(BufferedImage image, int columnsIn, int linesIn) {
        this(image.getWidth(), image.getHeight(), columnsIn, linesIn);
        this.image = image;

        float[] colorComponents = new float[getCompCount()];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                int rgb = image.getRGB(i, j);
                colorComponents = Color.getColorComponents(Color.valueOf(rgb));
                for (int ii = 0; ii < columnsIn; ii++)
                    for (int ij = 0; ij < linesIn; ij++) {
                        for (int com = 0; com < getCompCount(); com++) {
                            setCompNo(com);
                            set(i, j, ii, ij, colorComponents[com]);
                        }
                    }
            }
        }
    }


    public double get(int column, int line, int columnIn, int lineIn) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && columnIn >= 0 && columnIn < columnsIn
                && lineIn >= 0 && lineIn < linesIn && compNo >= 0 && compNo < compCount) {
            return x[index(column, line, columnIn, lineIn)];
        } else {
            incrGetOut++;
            return 0.0; // OutOfBound?
        }

    }

    public int index(int column, int line, int columnIn, int lineIn) {
        return compNo + compCount * (lineIn + linesIn * (columnIn + columnsIn * (line + lines * (column))));
    }

    public void set(int column, int line, int columnIn, int lineIn, double d) {
        if (column >= 0 && column < columns && line >= 0 && line < lines && columnsIn >= 0 && columnIn < columnsIn
                && lineIn >= 0 && lineIn < linesIn && compNo >= 0 && compNo < compCount) {
            x[index(column, line, columnIn, lineIn)] = d;
        } else {
            incrGetOut++;
            //System.out.println("Outs : " + incrGetOut);
        }
    }


    public M3 copy() {
        M3 copy = new M3(columns, lines, columnsIn, linesIn);

        for (int i = 0; i < copy.getColumns(); i++) {
            for (int j = 0; j < copy.getLines(); j++) {
                for (int ii = 0; ii < copy.getColumnsIn(); ii++) {
                    for (int ij = 0; ij < copy.getLinesIn(); ij++) {
                        for (int c = 0; c < copy.getCompCount(); c++) {
                            setCompNo(c);
                            copy.setCompNo(c);
                            copy.set(i, j, ii, ij, get(i, j, ii, ij));
                        }
                    }
                }
            }
        }
        return copy;
    }

    public int getCompCount() {
        return compCount;
    }

    protected double get(int ii, int ij) {
        return get(currentX(), currentY(), ii, ij);
    }

    private int currentY() {
        return currentY;
    }

    private int currentX() {
        return currentX;
    }

    protected void setXY(int x, int y) {
        currentX = x;
        currentY = y;
    }

    protected void restoreXY() {
        currentX = savedX;
        currentY = savedY;
    }

    protected void saveXY(int x, int y) {
        savedX = currentX;
        savedY = currentY;
        currentX = x;
        currentY = y;


    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }

    public int getCompNo() {
        return compNo;
    }

    public PixM[][] getImagesMatrix() {
        return normalize(0.0, 1.0);
    }

    public PixM[][] normalize(final double min, final double max) {
        PixM[][] res = new PixM[columnsIn][linesIn];

        double[][][] maxRgbai = new double[compCount][columnsIn][linesIn];
        double[][][] meanRgbai = new double[compCount][columnsIn][linesIn];
        double[][][] minRgbai = new double[compCount][columnsIn][linesIn];


        for (int ii = 0; ii < columnsIn; ii++) {
            for (int ij = 0; ij < linesIn; ij++) {
                for (int comp = 0; comp < getCompCount(); comp++) {
                    setCompNo(comp);
                    maxRgbai[comp][ii][ij] = max;
                    minRgbai[comp][ii][ij] = min;
                    meanRgbai[comp][ii][ij] = 0;
                }
                PixM image = new PixM(columns, lines);
                res[ii][ij] = image;
            }
        }


        for (int comp = 0; comp < getCompCount(); comp++) {
            setCompNo(comp);
            for (int ii = 0; ii < columnsIn; ii++) {
                for (int ij = 0; ij < linesIn; ij++) {
                    for (int i = 0; i < columns; i++) {
                        for (int j = 0; j < lines; j++) {
                            double gij = get(i, j, ii, ij);
                            if (gij > maxRgbai[comp][ii][ij]) {
                                maxRgbai[comp][ii][ij] = gij;
                            }
                            if (gij < minRgbai[comp][ii][ij]) {
                                minRgbai[comp][ii][ij] = gij;
                            }
                            meanRgbai[comp][ii][ij] += gij;
                        }
                    }
                    meanRgbai[comp][ii][ij] /= (lines * columns);
                    System.out.println("min/max/avg (ii, ij) (" + ii + ", " + ij + ")" + " " +
                            "min: " + minRgbai[comp][ii][ij] +
                            "max: " + maxRgbai[comp][ii][ij] +
                            "avg: " + meanRgbai[comp][ii][ij]);
                }
            }
        }

        for (int ii = 0; ii < columnsIn; ii++) {
            for (int ij = 0; ij < linesIn; ij++) {
                for (int i = 0; i < res[ii][ij].getColumns(); i++) {
                    for (int j = 0; j < res[ii][ij].getLines(); j++) {
                        for (int comp = 0; comp < res[ii][ij].getCompCount(); comp++) {
                            res[ii][ij].setCompNo(comp);
                            setCompNo(comp);
                            double v;
                            v = get(i, j, ii, ij);
                            float value = (float) v;
                            //value = (float) ((v - minRgbai[comp][ii][ij])
                            //        / (maxRgbai[comp][ii][ij] - minRgbai[comp][ii][ij])+1/256.0);
                            //value = (float) ((value - min) * (max - min));
                            //value = (float) Math.max(value, min);
                            //value = (float) Math.min(value, max);
                            if (comp == 3)
                                value = 1f;
                            res[ii][ij].set(i, j, v);
                            incrOK++;
                        }
                    }
                }

            }
        }
        //System.out.println("Outs : " + incrGetOut);
        System.out.println("Points ok " + incrOK);
        return res;
    }

    public M3 filter(FilterPixM filter1, int ii, int ij) {
        PixM matrix = getMatrix(ii, ij);
        matrix.applyFilter(filter1);
        setMatrix(ii, ij, matrix);
        return this;
    }

    public void setMatrix(int ii, int ij, PixM matrix) {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                for (int c = 0; c < getCompCount(); c++) {
                    setCompNo(c);
                    matrix.setCompNo(c);
                    set(i, j, ii, ij, matrix.get(i, j));
                }
            }
        }

    }

    public PixM getMatrix(int ii, int ij) {
        PixM matrix = new PixM(columns, lines);
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < lines; j++) {
                for (int c = 0; c < getCompCount(); c++) {
                    setCompNo(c);
                    matrix.setCompNo(c);
                    matrix.set(i, j, get(i, j, ii, ij));
                }
            }
        }
        return matrix;
    }

    public double get(int i, int j, int ii, int ij, int c) {
        double I = 0.0;
        setCompNo(0);
        if (c == 4) {
            for (int c1 = 0; c1 < 3; c1++) {
                setCompNo(c1);
                I += Math.abs(get(i, j, ii, ij)) / 3;
            }
            I = Math.sqrt(I);
        } else {
            setCompNo(c);
            I = get(i, j, ii, ij);
        }

        return I;
    }

    public void resizeSubmatrix(int ii0, int ij0, int i2, int j2) {

    }

    public double getIntensity(int column, int line, int ii, int ij) {
        return get(column, line, ii, ij, 4);
    }

    public int getColumns() {
        return columns;
    }

    public int getLines() {
        return lines;
    }

    public int getColumnsIn() {
        return columnsIn;
    }

    public int getLinesIn() {
        return linesIn;
    }
}
