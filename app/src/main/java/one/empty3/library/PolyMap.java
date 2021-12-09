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

package one.empty3.library;

/*__
 * Represents polygons built on a up axis. Each point of one polygon is joint by
 * his neighbors left, right, up down '''
 *
 * @author DAHMEN Manuel
 */
//
//public class PolyMap extends Representable {
//
//    private List<Polygon> polys = new ArrayList<Polygon>();
//    private int size;
//
//    /*__
//     * *
//     * Constructor
//     *
//     * @param size Size of polygons
//     */
//   public PolyMap(int size) {
//
//        this.size = size;
//        if (!(size > 2)) {
//            this.size = 3;
//            throw new UnsupportedOperationException("Maillage de polygones");
//        }
//
//    }
//
//    /*__
//     * *
//     * Add point
//     *
//     * @param coordArr height
//     * @param p point to add
//     */
//    public void addPoint(int coordArr, Point3D p) {
//        if (coordArr >= 0) {
//
//            if (coordArr == polys.size()) {
//            }
//
//            polys.get(coordArr).getPoints().add(p);
//
//        }
//    }
//
//    /*__
//     * *
//     *
//     * @return maillage as an array
//     * @throws IllegalOperationException
//     */
//    public Point3D[][] getMaillage() throws IllegalOperationException {
//        if (polys.isEmpty()) {
//            throw new IllegalOperationException("Maillage incorrect");
//        }
//        int dim2 = 0;
//        for (int i = 0; i < polys.size(); i++) {
//            if (i == 0 || dim2 == polys.get(i).getPoints().size()) {
//                dim2 = polys.get(i).getPoints().size();
//            } else {
//                throw new IllegalOperationException("Maillage incorrect");
//            }
//        }
//        Point3D[][] pts = new Point3D[polys.size()][dim2];
//        for (int i = 0; i < polys.size(); i++) {
//            for (int j = 0; j < polys.get(i).getPoints().size(); j++) {
//                pts[i][j] = polys.get(i).getPoints().get(j);
//            }
//        }
//        return pts;
//    }
//
//    /*__
//     * *
//     * Gets points on a (coordArr,y) matrix;
//     *
//     * @param coordArr width coordArr>=0 && coordArr<polys.size() @para
//     *          m y height y>=0 && y< each poly.size() in polys @return
//     */
//    public Point3D getPoint(int coordArr, int y) {
//
//        return coordArr < size && y < polys.size()
//                ? polys.get(coordArr).getPoints().get(y)
//                : null;
//
//    }
//
//    /*__
//     * *
//     * Polygon's list Each polygon must have the same size than others
//     *
//     * @return polygones
//     */
//    public List<Polygon> getPolys() {
//        return polys;
//    }
//}
