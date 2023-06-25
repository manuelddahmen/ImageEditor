package one.empty3.apps.test;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

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
import one.empty3.library.ZBufferImpl;

public class TestZBufferAndroid {
    @Test
    public void testSphere() {
        RectF boundingRect;
        Sphere sphere = new Sphere(new Axe(new Point3D(10d, 10d, 1d), new Point3D(10d, 10d, -1d)), 20.);
        boundingRect = sphere.getBoundRect2d();
        System.out.println("w:"+boundingRect.width()+" ; h:"+boundingRect.height());
        ZBufferImpl zBuffer = new ZBufferImpl((int) boundingRect.width(), (int) boundingRect.height());
        Scene scene1 = new Scene();

        int transparent = Color.BLACK;


        Point3D middle = Point3D.n(boundingRect.left + boundingRect.width() / 2., boundingRect.top + boundingRect.height() / 2., 0);
        Camera camera = new Camera(Point3D.Z.mult(Math.max(boundingRect.width(), boundingRect.height())*2).plus(middle), middle, Point3D.Y);
        zBuffer.idzpp();
        scene1.cameraActive(camera);
        zBuffer.scene(scene1);
        zBuffer.camera(camera);

        sphere.texture(new ColorTexture(Color.RED));

        scene1.add(sphere);

        zBuffer.setTransparent(transparent);
        zBuffer.texture(new ColorTexture(transparent));
        zBuffer.couleurDeFond(new ColorTexture(transparent));

        zBuffer.draw(scene1);


        Bitmap bitmap = zBuffer.image();

        assert bitmap!=null;

        ImageIO.write(bitmap, "jpg", new File(System.getProperty("user.home")+"/EmptyCanvasTest/testSphere.jpg"));

    }
    public void testSphereTranslated() {
        Rect boundingRect = new Rect(0, 0, 20, 20);
        ZBufferImpl zBuffer = new ZBufferImpl(boundingRect.width(), boundingRect.height());
        Scene scene1 = new Scene();
        int transparent = Color.BLACK;

        Point3D middle = Point3D.n(boundingRect.left + boundingRect.width() / 2., boundingRect.top + boundingRect.height() / 2., 0);
        Camera camera = new Camera(Point3D.Z.mult(Math.max(boundingRect.width(), boundingRect.height())*2).plus(middle), middle, Point3D.Y);
        zBuffer.idzpp();
        scene1.cameraActive(camera);
        zBuffer.scene(scene1);
        zBuffer.camera(camera);

        zBuffer.setTransparent(transparent);
        zBuffer.texture(new ColorTexture(transparent));
        zBuffer.couleurDeFond(new ColorTexture(transparent));

        zBuffer.draw(scene1);


        ImageIO.write(zBuffer.imageInvX().getBitmap(), "jpg", new File(System.getProperty("user.home")+"/EmptyCanvasTest/testSphere.jpg"));
    }
}
