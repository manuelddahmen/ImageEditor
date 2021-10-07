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
package one.empty3.library.core.extra;

/*__
 * @author MANUEL DAHMEN
 * <p>
 * dev
 * <p>
 * 28 déc. 2011
 */
/*
 public class CopyOfTrianguleSphereAnimation extends Animation {
 private TRIObject o;
 int n = 2;

 public CopyOfTrianguleSphereAnimation(Scene s) {
 super(s);
 }

 public void modifier() {
 o = new TRIObject();
		
 Point3D lumiere = new Point3D(0,0,1);
		
		
		
 double a = 0, b = 0, R = 10;
 n++;
 Point3D[][] pcurrent = null;
 if (n > 1) {
 pcurrent = new Point3D[n][n];

 int i = 0;
 for (b = 0; b < 2*Math.PI; b += 2*Math.PI / n) {
 int j = 0;
 for (a = -Math.PI; a < Math.PI; a += Math.PI / n) {
 if (i < n && j < n)
 pcurrent[i][j] = new Point3D(R * Math.cos(a)
 * Math.cos(b), R * Math.cos(a) * Math.sin(b), R
 * Math.sin(a));
 j++;

 }
 i++;
 }
 }

 for (int i1 = 0; i1 < n; i1++)
 for (int j = 0; j < n; j++) {
 o.add(new TRI(pcurrent[(i1) % n][(j) % n],
 pcurrent[(i1) % n][(j + 1) % n],
 pcurrent[(i1 + 1) % n][(j + 1) % n], Color.blue));
				
 o.add(new TRI(pcurrent[(i1 + 1) % n][(j) % n],
 pcurrent[(i1 + 1) % n][(j) % n],
 pcurrent[(i1 + 1) % n][(j + 1) % n], Color.red));
 }
 Scene s = new Scene();
 s.add(o);
 scene(s);
 }

 }
 */
