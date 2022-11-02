package one.empty3.feature20220726;

import android.graphics.Bitmap;

public interface InterfaceMatrix {

    public void init(int columns, int lines);
//    public void init(Bitmap bitmap);
//    public void init(PixM bitmap);


    public void setCompNo(int no);

    public int getCompNo();

    public void set(int column, int line, double values);

    public void set(int column, int line, double... values);

    public double get(int column, int line);

    public double[] getValues(int column, int line);

    Bitmap getBitmap();
}
