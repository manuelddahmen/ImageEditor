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
        int color = (( 0xff) << 24) | (((int)(r*255) & 0xff) << 16) | (((int)(g*255) & 0xff) << 8) | (((int)(b*255) & 0xff));
        return new Color(color);
    }


    public Color newCol(float r, float g, float b, float a) {
        int color = (( 0xff) << 24) | (((int)(r*255) & 0xff) << 16) | (((int)(g*255) & 0xff) << 8) | (((int)(b*255) & 0xff));
        return new Color(color);

    }

    public float[] getColorComponents() {
        if(colorObject!=null) {
            return colorObject.getComponents();
        }
        else {
            return super.getComponents();
        }
    }
    public static float[] getColorComponents(Color color) {
        return new float[]{color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f,1f};
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
        setRGB(i);
    }

    public void setColor(android.graphics.Color color) {
        this.colorObject = color;
    }

    public void setRGB(int rgb) {
        rgb = rgb ;
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public void setRGB(int r, int g, int b) {
        this.colorObject = android.graphics.Color.valueOf(
                android.graphics.Color.argb(255, r, g, b));
    }

    public void setRgb(int rgb) {
        this.colorObject = android.graphics.Color.valueOf(rgb);
    }

    public int getRGB() {
        return getRgb();
    }

    public int getRgb() {
        if(colorObject == null)
            return super.toArgb();
        int red = (int) (colorObject.red()*255f);
        int green = (int) (colorObject.green()*255);
        int blue = (int) (colorObject.blue()*255);
        int color = ((red << 16) | (green << 8) | blue)|0xFF000000;;
        return color;
    }
    public int getRed() {
        if(colorObject == null)
            return (int) (super.getComponents()[0]*255);
        return (int) (colorObject.getComponents()[0] * 255f);
    }

    public int getGreen() {

        if(colorObject == null)
            return (int) (super.getComponents()[1]*255);
        return (int) (colorObject.getComponents()[1] * 255f);
    }

    public int getBlue() {
        if(colorObject == null)
            return (int) (super.getComponents()[2]*255);

        return (int) (colorObject.getComponents()[2] * 255f);
    }

    public int getAlpha() {
        if(colorObject == null)
            return (int) (super.getComponents()[3]*255);

        return (int) (colorObject.alpha() * 255f);
    }
}
