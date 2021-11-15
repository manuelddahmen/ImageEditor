package test3;
/*__

 Empty3
 Copyright (C) 2010-2019  Manuel DAHMEN

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.*;
import one.empty3.library.core.physics.Bille;
import one.empty3.library.core.physics.Force;
import one.empty3.library.core.testing.TestObjetSub;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.feature.app.replace.java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/*__
 * Created by manue on 20-06-19.
 */
public class Bras extends TestObjetSub {
    public static final double longueur = 100;
    int nbrLines = 20;
    private int nbrChunck = 100;
    private Point3D lastPoint;
    private Force force;
    private ITexture textureDefault;
    private double speed = 10;

    public Bras() {

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void ginit() {
        getZ().couleurDeFond(new TextureCol(Color.WHITE));
        textureDefault = new TextureImg(
                new ECBufferedImage(
                        ImageIO.read(new File("samples/img/herbe.jpg"))));
        ArrayList<Bille> allPoints = new ArrayList<>();
        for (int i = 0; i < nbrLines; i++) {
            PolyLine polyLine = new PolyLine();
            polyLine.getParameters().setIncrU(0.0001);

            Point3D start = new Point3D(Point3D.O0);

            polyLine.add(start);
            Bille bille = new Bille();
            bille.setMasse(10);
            bille.setPosition(start);
            allPoints.add(bille);
            for (int j = 0; j < nbrChunck; j++) {
                lastPoint = polyLine.getPoints().get(polyLine.getPoints().size()- 1);
                lastPoint = lastPoint.plus(Point3D.random(longueur));
                polyLine.add(lastPoint);
                bille = new Bille();
                bille.setMasse(10);
                bille.setPosition(lastPoint);
                allPoints.add(bille);
            }
            scene().add(polyLine);
            polyLine.texture(textureDefault);
        }
    }

    public void finit() {
        Camera camera = new Camera(Point3D.Z.mult(
                longueur * 2 * nbrChunck / 10), Point3D.O0);
        scene().add(camera);
        scene().cameraActive(camera);
        double t = 1.0 * frame() / (getMaxFrames());


        Iterator<Representable> representableIterator = scene().iterator();
        while (representableIterator.hasNext()) {
            Representable r = representableIterator.next();
            if (r instanceof PolyLine) {
                PolyLine pl = (PolyLine) r;
                for (int i = 0; i < pl.getPoints().size(); i++)
                    pl.getPoints().set(i, pl.getPoints().get(i).plus(Point3D.random(speed)));
            }
        }
    }


    public static void main(String[] args) {
        Bras bras = new Bras();
        bras.setMaxFrames(10000);
        bras.loop(true);
        new Thread(bras).start();
    }

}
