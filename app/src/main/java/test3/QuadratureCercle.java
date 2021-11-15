package test3;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.CameraInPath;
import one.empty3.library.core.testing.TestObjetSub;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/*__
 * Created by manue on 15-06-19.
 */
public class QuadratureCercle extends TestObjetSub {
    private final int dimCube;
    private final int dimSphere;
    private double radius;
    private Sphere[] spheres;
    private int pointCount;
    private Point3D[] speed;
    private double maxSpeed;
    private double minSpeed;
    private ITexture[] textures;
    private Point3D[] p2;
    private RepresentableConteneur representableConteneur;
    private ITexture textureDefault;
    private int forme = 1;
    private int framesItere = 1;

    public QuadratureCercle() {
        pointCount = 1000;
        dimCube = 10;
        dimSphere = 10;
        maxSpeed = 20;
        minSpeed = 5;
        radius = dimCube/10.0;
        forme = 1;
    }


    public static void main(String[] args) {
        QuadratureCercle quadratureCercle = new QuadratureCercle();
        quadratureCercle.loop(true);
        quadratureCercle.setMaxFrames(200);
        new Thread(quadratureCercle).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void ginit() {
        representableConteneur = new RepresentableConteneur();
        spheres = new Sphere[pointCount];
        speed = new Point3D[pointCount];
        textures = new ITexture[pointCount];
        textureDefault = null;
      //try {
            textureDefault = new ColorTexture(Color.valueOf(Color.GREEN)); //new TextureImg(new ECBufferedImage(ImageIO.read(new File("samples/img/herbe.jpg"))));
        //} catch (IOException e) {
         //   e.printStackTrace();
     //}
        p2 = new Point3D[pointCount];
        for (int i = 0; i < pointCount; i++) {
            p2[i] = Point3D.random(100d);
            p2[i].texture(textureDefault);
            spheres[i] = new Sphere(new Axe(p2[i].plus(Point3D.random((double) dimCube)),

                    p2[i].plus(Point3D.random((double) dimCube))),
                    radius);

            textures[i] = textureDefault;
            representableConteneur.add(spheres[i]);
            spheres[i].texture(textures[i]);
            speed[i] = Point3D.random(maxSpeed);
            while (speed[i].norme() < minSpeed) {
                speed[i] = Point3D.random(maxSpeed);
            }
            //scene().add(spheres[i]);

        }
        scene().add(representableConteneur);
    }

    public void bounce(int i) {
        representableConteneur.remove(p2[i]);
        p2[i] = p2[i].plus(speed[i]);
        p2[i].texture(textureDefault);
        representableConteneur.add(p2[i]);
        
        
        scene().remove(spheres[i]);
        spheres[i] = new Sphere(new Axe(p2[i].plus(speed[i]),
                p2[i].moins(speed[i])),
                radius);
        scene().add(spheres[i]);

        spheres[i].texture(textures[i]);


        if (forme == 1) {
            if (p2[i].norme() > dimSphere && speed[i].prodScalaire(p2[i]) > 0)
                speed[i] = speed[i].mult(-1d);
        } else {
            if (p2[i].getX() > dimCube && speed[i].getX() > 0) {
                speed[i].setX(-speed[i].getX());
            }
            if (p2[i].getX() < -dimCube && speed[i].getX() < 0) {
                speed[i].setX(-speed[i].getX());
            }
            if (p2[i].getY() > dimCube && speed[i].getY() > 0) {
                speed[i].setY(-speed[i].getY());
            }
            if (p2[i].getY() < -dimCube && speed[i].getY() < 0) {
                speed[i].setY(-speed[i].getY());
            }
            if (p2[i].getZ() > dimCube && speed[i].getZ() > 0) {
                speed[i].setZ(-speed[i].getZ());
            }
            if (p2[i].getZ() < -dimCube && speed[i].getZ() < 0) {
                speed[i].setZ(-speed[i].getZ());
            }
        }
    }

    @Override
    public void finit() throws Exception {
        CameraInPath camera = new CameraInPath(new Circle(
                new Axe(Point3D.O0.plus(Point3D.X), Point3D.O0.moins(Point3D.X)), 800d));
        scene().cameraActive(camera);
        double t = 1.0 * frame() / (getMaxFrames());
        camera.setT(t);

        Point3D z = Point3D.O0.moins(camera.getCourbe().calculerPoint3D(t)).norme1();
        Point3D x = camera.getCourbe().tangente(t).norme1().mult(-1d);
        Point3D y = x.prodVect(z).norme1();
        camera.setMatrix(x, y, z);
        for (int j = 0; j < framesItere; j++)
            for (int i = 0; i < pointCount; i++)
                bounce(i);
    }

}
