package one.empty3.library.core.lighting;

import java.util.*;

import android.graphics.Color;

public class ColorDist {
    public Color color;
    public double dist;

    /*
    public int compareTo(Object o) {
        if (o instanceof ColorDist) {
            ColorDist cd = (ColorDist) o;
            return dist<cd.dist?-1:(dist==cd.dist?0:1);
       } else
            return 0;//throw??
    }
    */
    public static void sort(ColorDist[] cd) {
        Arrays.sort(cd, new SortbyDist());
    }
}

class SortbyDist implements Comparator<ColorDist> {

    // Used for sorting in ascending order of 

    // roll number 

    public int compare(ColorDist a, ColorDist b) {

        int compare1 = Double.compare(a.dist, b.dist);
        int compareR = Double.compare(b.dist, a.dist);
        if (Math.signum(compare1) == -Math.signum(compareR))
            return (int) Math.signum(compare1);
        else
            throw new NullPointerException("Error comparator ColorDist c!=-c");


    }
} 
