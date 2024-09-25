package one.empty3.feature.app.maxSdk29.pro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Test;

import java.io.File;

import javaAnd.awt.Color;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.library.Axe;
import one.empty3.library.Camera;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.Scene;
import one.empty3.library.Sphere;
import one.empty3.library.StructureMatrix;
import one.empty3.library.ZBufferImpl;

public class TestZBufferAndroid {
    @Test
    public void testSphere() {
        Sphere sphere = new Sphere(new Axe(new Point3D(10d, 10d, 1d), new Point3D(10d, 10d, -1d)), 10.);
        StructureMatrix<Point3D> boundingRect = sphere.getBoundRect2d();
        double left = boundingRect.getElem(0).get(0);
        double top = boundingRect.getElem(0).get(1);
        double right = boundingRect.getElem(1).get(0);
        double bottom = boundingRect.getElem(1).get(1);
        double width = right - left;
        double height = bottom - top;
        System.out.println("w:"+((int)width)+" ; h:"+((int)height));
        ZBufferImpl zBuffer = new ZBufferImpl((int)width, (int) height);
        Scene scene1 = new Scene();

        int transparent = -1;


        Point3D middle = Point3D.n(left + width / 2., top + height / 2., 0);
        Camera camera = new Camera(Point3D.Z.mult(-Math.max(width, height)*2).plus(middle), middle, Point3D.Y);
        zBuffer.setDisplayType(ZBufferImpl.SURFACE_DISPLAY_COL_QUADS);
        //zBuffer.idzpp();
        scene1.cameraActive(camera);
        zBuffer.scene(scene1);
        zBuffer.camera(camera);

        sphere.texture(new ColorTexture(Color.RED));

        scene1.add(sphere);

        zBuffer.setTransparent(transparent);
        zBuffer.texture(new ColorTexture(transparent));
        zBuffer.couleurDeFond(new ColorTexture(transparent));

        zBuffer.draw();


        Bitmap bitmap = zBuffer.image();

        assert bitmap!=null;
        if (!new File("temp").exists())
            new File("temp").mkdir();
        ImageIO.write(bitmap, "jpg", new File("\\temp\\testSphere.jpg"));

    }
}
