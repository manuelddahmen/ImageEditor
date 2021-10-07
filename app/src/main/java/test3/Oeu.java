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

import one.empty3.library.Camera;
import one.empty3.library.Point3D;
import one.empty3.library.Sphere;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.testing.TestObjetSub;
import one.empty3.pointset.Gravity;
import one.empty3.pointset.Move;
import one.empty3.pointset.PCont;

import java.util.HashMap;

/*__
 *
 * Rassembler et améliorer les calculs sur des ensembles de points dans un package
 * utilisable et extensible.
 *
 * delta(x2+y2+z2-R) pente force 1
 * delta(mM(p2-p1)/d3) pente force 2
 * combiner et calibrer
 * accrocher les points sur la surface
 */
public class Oeu extends TestObjetSub{
    PCont<Gravity> point3DPCont = new PCont<Gravity>();
    Move move = new Move(point3DPCont);
    HashMap<String, Double> map;
    private int pointCount;

    public static void main(String [] args)
   {
       Oeu oeu = new Oeu();
       new Thread(oeu).start();
   }
    public void ginit()

    {
        map = new HashMap();
        move.setItereFrame(100);
        scene().add(point3DPCont);
        try {
            move.initMoveSurface("coordArr*coordArr+y*y+z*z-R*R", map);
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
        setPointCount(10);
        for (int i = 0; i < pointCount; i++) {

            Gravity gravity = new Gravity(Point3D.random(100d), 10d, Point3D.random(10.0));
            scene().add(new Sphere(gravity, 2));
            point3DPCont.add(gravity);
        }
        scene().cameraActive(new Camera(Point3D.Z.mult(-10d), Point3D.O0, Point3D.Y));
    }
   public void finit() {
       for (int i = 0; i < pointCount; i++) {
           move.computeMoveSurface(point3DPCont.get(i));
       }

   }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public HashMap<String, Double> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Double> map) {
        this.map = map;
    }

    public int getPointCount() {
        return pointCount;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
    }
}
