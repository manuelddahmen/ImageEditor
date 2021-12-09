/*
 *  This file is part of Empty3.
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
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
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

/*

 Vous êtes libre de :

 */
package one.empty3.library;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

public class TourDeRevolution extends Representable {

    private String id;
    private CourbeDeImage courbe;
    private TRIObject o;
    private PObjet op;

    public TourDeRevolution(File image, Axe axe) {
        try {
            this.courbe = new CourbeDeImage(ImageIO.read(image));
            courbe.anayliserImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] argss) {
        try {
            System.out.print(new File(".").getCanonicalPath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        TourDeRevolution tr = new TourDeRevolution(new File("tour.png"), new Axe(Point3D.Y.mult(-1.), Point3D.Y.mult(1.)));
        tr.generateB();
        PObjet o = tr.getPO();
        ZBuffer z = new ZBufferImpl(500, 500);
        Scene s = new Scene();
        s.add(tr);
        z.scene(s);
        z.draw();
        try {
            ImageIO.write(z.image(), "png",
                    ImageIO.createImageOutputStream(new File("result2TR.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @Override
    public void generateB() {

        Color[] colors = new Color[256];
        for (int i = 0; i < 255; i++) {
            double a = 1.0 * i / 255 * 2 * Math.PI;
            colors[i] = new Color(0.0f/*1*((float)Math.sin(a)+1)/2*/, 1 * (float) (Math.sin(a) + 1) / 2, 1 * (float) (Math.cos(a) + 1) / 2);
        }
        o = new TRIObject();
        op = new PObjet();

        int max = 1000;
        @SuppressWarnings("unchecked")
        ArrayList<Point3D>[] points = new ArrayList[courbe.getPoints().size()];
        for (int i = 0; i < courbe.getPoints().size(); i++) {
            points[i] = new ArrayList<Point3D>();
        }
        Enumeration<Point2D> en = courbe.getPoints().keys();
        while (en.hasMoreElements()) {
            Point2D p = en.nextElement();
            double diamx = p.getX();
            double diamy = p.getY();

            System.out.println(courbe.getPoints().size());
            int i = 0;
            for (i = 0; i < max; i++) {
                double a = 2 * Math.PI * i / max;

                Point3D p2d = new Point3D(diamx * Math.cos(a), diamy, -diamx * Math.sin(a));

                p2d.texture(new TextureCol(colors[(int) ((Math.cos(a) + 1) / 2 * 255)]));

                op.add(p2d);

                //points[j].add(p2d);
            }

        }

         for (int i = 0; i < max; i++)
             for (int j = 0; j < points[0].size(); j++) {
         if (i > 0 && j > 0) {
         o.add(new TRI(points[j].get(i), points[j-1].get(i), points[j-1].get(i-1), Color.red));
         o.add(new TRI(points[j].get(i), points[j].get(i-1), points[j-1].get(i-1), Color.red));
         }
         }

    }

    public PObjet getPO() {
        return op;
    }

    public TRIObject getTRI() {
        return o;

    }


}
