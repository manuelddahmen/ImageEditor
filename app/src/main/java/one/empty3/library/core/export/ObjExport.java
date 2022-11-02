
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

package one.empty3.library.core.export;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.TRIObjetGenerateur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class ObjExport {
    public class Object {
        private List<Face> faces;
    }

    public class Vertex {
        private double x, y, z;
    }

    public class VertexTexture {
        private double u, v;
    }

    public class VertexNormal {
        private double vx, vy, vz;
    }

    public class Face {
        private Vertex v;
        private VertexTexture vt;
        private VertexNormal vn;

    }


    public static void save(File file, Scene scene, boolean override)
            throws IOException {
        if (!file.exists() || file.exists() && override) {
            file.createNewFile();
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));

            pw.println("o " + scene.description);

            Iterator<Representable> it = scene.iterator();

            while (it.hasNext()) {
                Representable r = it.next();


                traite(r, pw);
            }

            pw.close();
        }
    }

    private static void traite(Polygon r, PrintWriter pw) {
        for (int s = 0; s < r.getPoints().getData1d().size(); s++) {
            write("v ", pw);
            for (int c = 0; c < 3; c++) {
                double A = r.getPoints().getData1d().get(s).get(c);
                if (Double.isNaN(A)) {
                    A = 0;
                }
                write(A + " ", pw);
            }
            traite(r.getIsocentre(), pw);


        }
        int size = r.getPoints().getData1d().size();
        for (int t = 0; t < size; t++) {
            write("f " + (t % size) + " " +
                    ((t + 1) % size) + " " + size + "\n", pw);
        }
    }

    private static void traite(Representable r, PrintWriter pw) {
        write("", pw);

        if (r instanceof RepresentableConteneur) {
            traite((RepresentableConteneur) r, pw);
        }
        if (r instanceof ParametricSurface) {
            traite((ParametricSurface) r, pw);
        }
        if (r instanceof TRIObject) {
            traite((TRIObject) r, pw);
        }
        if (r instanceof TRIGenerable) {
            traite((TRIGenerable) r, pw);
        }
        if (r instanceof Polygon) {
            traite((Polygon) r, pw);
        }
        if (r instanceof TRI) {
            traite((TRI) r, pw);
        }
        if (r instanceof TRIObjetGenerateur) {
            traite((TRIObjetGenerateur) r, pw);
        }
        if (r instanceof TRIConteneur) {
            traite((TRIConteneur) r, pw);
        }
    }

    private static void traite(ParametricSurface r, PrintWriter pw) {
        write("", pw);
        for (double u = 0; u < r.getEndU(); u += r.incr1())
            for (double v = 0; v < r.getEndV(); v += r.incr2())
                traite(r.getElementSurface(u,
                        u + r.getIncrU(),
                        v, v + r.getIncrV()), pw);

    }

    private static void traite(RepresentableConteneur r, PrintWriter pw) {
        write("", pw);
        Iterator<Representable> it = r.iterator();
        while (it.hasNext()) {
            Representable next = it.next();
            traite(next, pw);
        }
    }

    private static void traite(Point3D r, PrintWriter pw) {
        write("v " + r.get(0) + " " + r.get(1) + " " + r.get(2) + "\n", pw);
    }

    private static void traite(TRI r, PrintWriter pw) {
        for (int i = 0; i < 3; i++) {
            traite(r.getSommet().getElem(i), pw);
        }
        for (int s = 0; s < 3; s++) {
            write("f ", pw);
            for (int c = 0; c < 3; c++) {
                double A = r.getSommet().getElem(s).get(c);
                if (Double.isNaN(A)) {
                    A = 0;
                }
                write(A + " ", pw);
            }
            write("\n", pw);
        }


        write("f 1/1/2", pw);
        write(" 2/2/2", pw);
        write(" 3/3/3\n", pw);
    }

    public static void traite(TRIConteneur TC, PrintWriter pw) {
        write("", pw);

        Iterator<TRI> it = TC.iterable().iterator();

        while (it.hasNext()) {
            TRI t = it.next();

            traite(t, pw);
        }
    }

    private static void traite(TRIGenerable r, PrintWriter pw) {
        r.generate();
    }

    private static void traite(TRIObject r, PrintWriter pw) {
        String s = "";
        Iterator<TRI> it = r.getTriangles().iterator();
        while (it.hasNext()) {

            traite(it.next(), pw);
        }
    }

    private static void traite(TRIObjetGenerateur r, PrintWriter pw) {
        String s = "";
        int x = r.getMaxX();
        int y = r.getMaxY();
        TRI[] tris = new TRI[2];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                r.getTris(i, j, tris);
                traite(tris[0], pw);
                traite(tris[1], pw);

            }
        }
    }

    public static void write(String flowElement, PrintWriter pw) {
        pw.write(flowElement);
    }
}
