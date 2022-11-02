package javaAnd.awt;

public class Color extends android.graphics.Color/*android.graphics.Color*/ {

    public Color(android.graphics.Color color) {
    }

    public static android.graphics.Color color(int colorAt) {
        return android.graphics.Color.valueOf(colorAt);
    }

    public static android.graphics.Color color(double v, double v1, double v2) {
        return Color.valueOf((float) v, (float) v1, (float) v2);

    }

    public static android.graphics.Color random() {
        android.graphics.Color random = color((float) Math.random(), (float) Math.random(), (float) Math.random());
        return random;
    }

    public static android.graphics.Color color(float r, float r1, float r2) {
        return Color.valueOf(r, r1, r2);
    }

    public static android.graphics.Color color(int r, int g, int b) {

        return Color.valueOf(r, g, b);
    }


    public static float[] getColorComponents(android.graphics.Color rgba) {
        float[] aRgba = new float[]{rgba.red(), rgba.green(), rgba.blue(), rgba.alpha()};
        return aRgba;
    }

    public static int intConv(android.graphics.Color color) {
        return color.toArgb();
    }

    public static float[] intConvToFloatArray(int color) {
        android.graphics.Color color1 = Color.valueOf(color);
        return new float[]{color1.red(), color1.green(), color1.blue()};
    }

    public static int floatArrayConvToIntcolorComponents(float[] colorComponents) {
        return android.graphics.Color.valueOf(colorComponents[0], colorComponents[1], colorComponents[2]).toArgb();
    }

    public android.graphics.Color Color(int rgb) {
        return Color.valueOf(rgb);
    }

}
