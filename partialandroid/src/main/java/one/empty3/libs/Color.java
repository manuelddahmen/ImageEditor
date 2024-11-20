package one.empty3.libs;

import one.empty3.libs.commons.IColorMp;

public class Color extends android.graphics.Color implements IColorMp {
    private android.graphics.Color colorObject;


    public Color(android.graphics.Color color) {
        this.colorObject = color;
    }

    public Color(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public static Color newCol(double r, double g, double b) {
        return new Color(android.graphics.Color.valueOf((float) r, (float) g, (float) b).toArgb());
    }


    public Color newCol(float r, float g, float b, float a) {
        return new Color(android.graphics.Color.valueOf(r, g, b).toArgb());

    }

    public float[] getColorComponents() {
        return colorObject.getComponents();
    }
    public static float[] getColorComponents(Color color) {
        return new float[]{color.getRed(), color.getGreen(), color.getBlue(), color.alpha()};
    }

    public static float[] getColorComponents(android.graphics.Color color) {
        return new float[]{color.red(), color.green(), color.blue(), color.alpha()};
    }

    @Override
    public IColorMp getColorObject() {
        return new Color(colorObject);
    }

    public int getColor() {
        return ((android.graphics.Color) colorObject).toArgb();
    }

    public void setColor(int i) {
        setRGB(i);
    }

    public void setColor(android.graphics.Color color) {
        this.colorObject = color;
    }

    public void setRGB(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public void setRGB(int r, int g, int b) {
        this.colorObject = android.graphics.Color.valueOf(
                android.graphics.Color.argb(0, r, g, b));
    }

    public void setRgb(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public int getRGB() {
        if(colorObject == null)
            return super.toArgb();
        return colorObject.toArgb();
    }

    public int getRgb() {
        if(colorObject == null)
            return super.toArgb();
        return colorObject.toArgb();
    }
    public int getRed() {
        return (int) (colorObject.red() * 255f);
    }

    public int getGreen() {
        return (int) (colorObject.green() * 255f);
    }

    public int getBlue() {
        return (int) (colorObject.blue() * 255f);
    }

    public int getAlpha() {
        return (int) (colorObject.alpha() * 255f);
    }
}
