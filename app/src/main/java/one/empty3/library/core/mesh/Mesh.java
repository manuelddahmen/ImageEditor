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

/*__
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.mesh;

import one.empty3.library.*;
import one.empty3.library.core.nurbs.*;
import one.empty3.library.core.tribase.TRIEllipsoide;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class Mesh extends ParametricSurface {

    public static final int MESH_SHAPE_CUBE = 0;
    public static final int MESH_SHAPE_ELLIPSOID = 1;
    public static final int MESH_SHAPE_PLANE = 2;
    public static final int MESH_SHAPE_BEZIER = 4;
    StructureMatrix<Integer> meshType = new StructureMatrix(0, Integer.class);
    public static final int MESH_ADD_TYPE_SUBDIVID = 8;
    public static final int MESH_ADD_TYPE_EXPAND = 16;
    public static final int MESH_POINT_ADD_STYLE_HEIGHT = 32;
    public static final int MESH_POINT_ADD_STYlE_SMOOTHED = 64;
    public static final int MESH_POINT_ADD_STYlE_ANGULAR = 128;
    private StructureMatrix<SurfaceParametriquePolynomialeBezier> baseBezier = new StructureMatrix<>(0, SurfaceParametriquePolynomialeBezier.class);
    private StructureMatrix<Sphere> baseSphere = new StructureMatrix<>(0, Sphere.class);
    private StructureMatrix<Cube> baseCube = new StructureMatrix<>(0, Cube.class);
    private StructureMatrix<TRIEllipsoide> baseEllipse = new StructureMatrix<>(0, TRIEllipsoide.class);

    private StructureMatrix<Point3D> pAdd = new StructureMatrix<>(1, Point3D.class);

    public Mesh() {
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("meshType/Mesh type", meshType);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Sphere'", baseSphere);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Cube'", baseCube);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Ellipse'", baseEllipse);
        getDeclaredDataStructure().put("baseSphere/Base of type 'Bezier Surface'", baseBezier);
        getDeclaredDataStructure().put("add Points At Location", pAdd);
        // Suggérer des points intermédiares -> confirmer ou proposer autre constellation
    }
}
