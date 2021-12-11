package one.empty3.atlasgen;

import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import one.empty3.library.*;



class HeightMapSurfaceSphere extends HeightMapSurface {
    ITexture heightMap;

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        throw new UnsupportedOperationException("Calculer point de la sphere + bitmap");
    }

    public HeightMapSurfaceSphere() {
        super();
    }

    public HeightMapSurfaceSphere(Axe axe, double radius, BufferedImage bi) {
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
                                image.getElem().getImage().getElem().getBitmap().getPixel(i, j)&0x00FF0000>>16)/256.);
        return mult


                ;
    }
}
