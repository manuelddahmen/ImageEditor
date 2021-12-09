/*
 *  This file is part of one.empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with one.empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.atlasgen;

import android.graphics.Color;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.library.*;
import one.empty3.library.core.nurbs.CameraInPath;
import one.empty3.library.core.nurbs.PcOnPs;

import javax.imageio.ImageIO;

import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import java.io.File;

/*__
 * G:\Apps\IdeaProjects\empty3\src\test\java\be\manudahmen\empty3\one.empty3.library\tests\TestSoS.java
 * Created by manue on 02-02-19.0
 */
public class TestSoS extends TestObjetSub {
    private CameraInPath camera;
    protected String[] list = new String[]{"2k_neptune.jpg", "2k_uranus.jpg", "8k_jupiter.jpg", "8k_mars.jpg",
            "8k_mercury.jpg", "8k_saturn.jpg", "8k_saturn_ring_alpha.png", "8k_venus_atmosphere.jpg", "8k_venus_surface.jpg"};
    private int planetNo;
    protected boolean quadrillage = true;

    protected static final double RADIUS = 6371000;
    private static final double HEIGHT_MAX_ALT = 8.870;
    protected static final double HEIGHT_MAX = HEIGHT_MAX_ALT * 10000;

    private static final int NSEG = 8;
    private static final int NSEG2 = 24;
    private Point3D pointsB;
    private Point3D pointsA;
    private RepresentableConteneur representableConteneur
            = new RepresentableConteneur();
    protected HeightMapSurfaceSphere heightMapSurfaceSphere;
    private Point3D sphereOrig = Point3D.O0;
    TextureCol textureCol = new TextureCol(Color.RED);
    private Point3D sphereDest = Point3D.Y;
    ITexture colorTextureSurface = new TextureCol(Color.GREEN);
    private double segemntSize = 1;
    private BufferedImage bufferedImageHeightMap;
    private BufferedImage bufferedImageTexture;
    TextureOpSphere textureOpSphere;
    public void ginit() {
        list = new File("res/img/planets").list();
        planetNo = 0;
        assert list != null;
        setMaxFrames(360 * list.length);
        try {
            bufferedImageHeightMap = ImageIO.read(new File("res/img/gebco_08_rev_elev_21600x10800.png"));
            bufferedImageTexture = ImageIO.read(new File("res/img/planets/" + list[planetNo]));
            TextureImg textureImg = new TextureImg(new ECBufferedImage(bufferedImageTexture));
             textureOpSphere = new TextureOpSphere(textureImg);

            if(frame()%361==360)
                planetNo++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void finit() {

        scene().getObjets().data1d.clear();

        heightMapSurfaceSphere = new HeightMapSurfaceSphere(new Axe(sphereOrig.moins(Point3D.X),
                sphereOrig.plus(Point3D.X)), RADIUS, bufferedImageHeightMap);
        heightMapSurfaceSphere.texture(colorTextureSurface);

        heightMapSurfaceSphere.setIncrU(0.1);
        heightMapSurfaceSphere.setIncrV(0.1);
        heightMapSurfaceSphere.texture(textureOpSphere);


        representableConteneur = new RepresentableConteneur();

        scene().add(representableConteneur);
        if (heightMapSurfaceSphere != null)
            scene().add(heightMapSurfaceSphere);

        if (isQuadrillage()) {
            for (int s = 0; s < NSEG;
                 s++) {

                pointsA = new Point3D(0d, 1.0 * s / NSEG, 0d);
                pointsB = new Point3D(1d, 1.0 * s / NSEG, 0d);
                LineSegment segmentDroite = new LineSegment(pointsA, pointsB);
                segmentDroite.texture(textureCol);
                pointsA.texture(textureCol);
                pointsB.texture(textureCol);

                PcOnPs pcOnPs = new PcOnPs(heightMapSurfaceSphere, segmentDroite);
                pcOnPs.getParameters().setIncrU(0.0001);
                representableConteneur.add(pcOnPs);

            }
            for (int s = 0; s < NSEG2;
                 s++) {

                pointsA = new Point3D(1.0 * s / NSEG2, 0d, 0d);
                pointsB = new Point3D(1.0 * s / NSEG2, 1d, 0d);
                LineSegment segmentDroite = new LineSegment(pointsA, pointsB);
                segmentDroite.texture(textureCol);
                pointsA.texture(textureCol);
                pointsB.texture(textureCol);

                PcOnPs pcOnPs = new PcOnPs(heightMapSurfaceSphere, segmentDroite);
                pcOnPs.getParameters().setIncrU(0.0001);
                representableConteneur.add(pcOnPs);

            }
         }
        camera = new CameraInPath(new Circle(
                new Axe(Point3D.O0.plus(Point3D.X), Point3D.O0.moins(Point3D.X)), RADIUS * 4));

        camera.setT(0.0);

        scene().cameraActive(camera);

        double t = 1.0 * frame() / (double) (getMaxFrames() / list.length);
        camera.setT(t);

        Point3D z = Point3D.O0.moins(camera.getCourbe().calculerPoint3D(t)).norme1();
        Point3D x = camera.getCourbe().tangente(t).norme1().mult(-1d);
        Point3D y = x.prodVect(z).norme1();
        //camera.setMatrix(x, y, z);
        //camera.calculerMatrice(y);

        System.out.println("Nombre d'objet: " + scene().getObjets().getData1d().size());
    }

    private boolean isQuadrillage() {
        return quadrillage;
    }

    protected void setQuadrillage(boolean q) {
        quadrillage = q;
    }

    public HeightMapSurfaceSphere getSphere() {
        return heightMapSurfaceSphere;
    }

    public void setSphere(HeightMapSurfaceSphere sphere) {

        heightMapSurfaceSphere = sphere;
    }

    public static void main(String[] args) {
        TestSoS testSoS = new TestSoS();
        new Thread(testSoS).start();
    }
}
