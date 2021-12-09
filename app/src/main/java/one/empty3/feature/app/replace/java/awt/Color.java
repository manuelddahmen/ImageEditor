package one.empty3.feature.app.replace.java.awt;

import android.graphics.ColorSpace;

public class Color extends android.graphics.Color {

    public android.graphics.Color Color(int r, int g, int b) {
        return Color.valueOf(r, g, b);
    }

    public android.graphics.Color Color(int r, int g, int b, int a) {
        return Color.valueOf(r, g, b, a);
    }

    public android.graphics.Color Color(int rgb) {
        return Color.valueOf(rgb);
    }

    public android.graphics.Color Color(int rgba, boolean hasalpha) {
        return Color.valueOf(rgba);
    }

    public static android.graphics.Color Color(float r, float g, float b) {
        return Color.valueOf(r, g, b);
    }

    public static android.graphics.Color Color(float r, float g, float b, float a) {
        return Color.valueOf(r, g, b, a);
    }

    public android.graphics.Color Color(ColorSpace cspace, float[] components, float alpha) {
        return Color.valueOf(components[0], components[1], components[2]);
    }
}
