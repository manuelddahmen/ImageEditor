package atlasgen;

import one.empty3.library.*;

import java.awt.*;
import android.graphics.Bitmap;

class HeightMapSurfaceSphere extends HeightMapSurface {
    ITexture heightMap;


    public HeightMapSurfaceSphere(Axe axe, double radius, Bitmap bi) {
        super(new Sphere(axe, radius), bi);
    }

    public Point3D height(double u, double v) {
        int i = (int) (u * (image.getElem().getImage().getElem().getWidth() ));
        int j = (int) (v * (image.getElem().getImage().getElem().getHeight()));
        if(i<0) i = 0;
        if(j<0) j = 0;
        if(i>=image.getElem().getImage().getElem().getWidth()) i = image.getElem().getImage().getElem().getWidth()-1;
        if(j>=image.getElem().getImage().getElem().getHeight()) j = image.getElem().getImage().getElem().getHeight()-1;

        Point3D mult = surface.getElem().calculerPoint3D(u, v).moins(((Sphere) surface.getElem()).getCircle().getCenter()).norme1().
                mult((
                                image.getElem().getImage().getElem().getPixel(i, j)&0x00FF0000>>16)/256.);
        return mult


                ;
    }
}
