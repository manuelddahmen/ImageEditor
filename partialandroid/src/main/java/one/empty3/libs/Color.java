package one.empty3.libs;

import one.empty3.libs.commons.IColorMp;

public class Color extends android.graphics.Color implements IColorMp {
    private android.graphics.Color colorObject;


    public Color(android.graphics.Color color) {
        this.colorObject = color;
    }

    public Color(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb| 0xff000000);
    }

    public static Color newCol(double r, double g, double b) {
        return new Color(android.graphics.Color.valueOf((float) r, (float) g, (float) b).toArgb()| 0xff000000);
    }


    public Color newCol(float r, float g, float b, float a) {
        return new Color(android.graphics.Color.valueOf(r, g, b, a).toArgb());

    }

    public float[] getColorComponents() {
        return colorObject.getComponents();
    }
    public static float[] getColorComponents(Color color) {
        return new float[]{color.getRed(), color.getGreen(), color.getBlue(),1f};
    }

    public static float[] getColorComponents(android.graphics.Color color) {
        return new float[]{color.red(), color.green(), color.blue(), 1f};
    }

    @Override
    public IColorMp getColorObject() {
        return new Color(colorObject);
    }

    public int getColor() {
        return ((android.graphics.Color) colorObject).toArgb();
    }

    public void setColor(int i) {
        setRGB(i| 0xff000000);
    }

    public void setColor(android.graphics.Color color) {
        this.colorObject = color;
    }

    public void setRGB(int rgb) {
        rgb = rgb | 0xff000000;
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public void setRGB(int r, int g, int b) {
        this.colorObject = android.graphics.Color.valueOf(
                android.graphics.Color.argb(0, r, g, b));
    }

    public void setRgb(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb| 0xff000000);
    }

    public int getRGB() {
        if(colorObject == null)
            return super.toArgb();
        return colorObject.toArgb()| 0xff000000;
    }

    public int getRgb() {
        if(colorObject == null)
            return super.toArgb();
        return colorObject.toArgb() | 0xff000000;
    }
    public int getRed() {
        if(colorObject == null)
            return (int) (super.getComponents()[0]*255);
        return (int) (colorObject.red() * 255f);
    }

    public int getGreen() {

        if(colorObject == null)
            return (int) (super.getComponents()[1]*255);
        return (int) (colorObject.green() * 255f);
    }

    public int getBlue() {
        if(colorObject == null)
            return (int) (super.getComponents()[2]*255);

        return (int) (colorObject.blue() * 255f);
    }

    public int getAlpha() {
        if(colorObject == null)
            return (int) (super.getComponents()[3]*255);

        return (int) (colorObject.alpha() * 255f);
    }
}
