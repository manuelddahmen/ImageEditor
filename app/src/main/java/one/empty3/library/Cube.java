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

public class Cube extends Representable implements TRIGenerable {
    private StructureMatrix<Double> cote = new StructureMatrix<>(0, Double.class);
    private StructureMatrix<Point3D> position = new StructureMatrix(0, Point3D.class);
    private TRIObject ts = new TRIObject();
    private static Double[][][] coordCube = new Double[][][]{
            {
                    {1.0, -1.0, -1.0},
                    {1.0, 1.0, -1.0},
                    {1.0, 1.0, 1.0},},
            {
                    {1.0, -1.0, -1.0},
                    {1.0, -1.0, 1.0},
                    {1.0, 1.0, 1.0},},
            {
                    {-1.0, -1.0, -1.0},
                    {-1.0, 1.0, -1.0},
                    {-1.0, 1.0, 1.0},},
            {{-1.0, -1.0, -1.0},
                    {-1.0, -1.0, 1.0},
                    {-1.0, 1.0, 1.0},}, {{-1.0, 1.0, -1.0},
            {1.0, 1.0, -1.0},
            {1.0, 1.0, 1.0}
    }, {{-1.0, 1.0, -1.0},
            {-1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0},}, {{-1.0, -1.0, -1.0},
            {1.0, -1.0, -1.0},
            {1.0, -1.0, 1.0}}, {
            {-1.0, -1.0, -1.0},
            {-1.0, -1.0, 1.0},
            {1.0, -1.0, 1.0}
    }, {{-1.0, -1.0, -1.0},
            {-1.0, 1.0, -1.0},
            {1.0, 1.0, -1.0}
    }, {{-1.0, -1.0, -1.0},
            {1.0, -1.0, -1.0},
            {1.0, 1.0, -1.0}
    }, {{-1.0, -1.0, 1.0},
            {-1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0}
    },
            {{-1.0, -1.0, 1.0},
                    {1.0, -1.0, 1.0},
                    {1.0, 1.0, 1.0}
            }
    };

    public Cube() {
        cote.setElem(1.0);
    }

    public Cube(ITexture t) {
        this();
        position.setElem(new Point3D(0.0, 0.0, 0.0));
        texture(t);
    }

    public Cube(double cote, Point3D position) {

        this();
        this.cote.setElem(cote);
        this.position.setElem(position);
    }

    public Cube(double cote, Point3D position, ITexture t) {
        this();
        this.position.setElem(position);
        this.cote.setElem(cote);
        texture(t);
    }

    public TRIObject generate() {
        ts.clear();

        for (int i = 0; i < 12; i++) {
            TRI t = new TRI(
                    new Point3D(coordCube[i][0], getTexture()),
                    new Point3D(coordCube[i][1], getTexture()),
                    new Point3D(coordCube[i][2], getTexture()),
                    getTexture());

            ts.add(t);

        }
        return ts;
    }


    public double getMlc() {
        return cote.getElem();
    }

    public void setMlc(double mlc) {
        this.cote.setElem(mlc);
    }

    public StructureMatrix<Point3D> getPosition() {
        return position;
    }

    public void setPosition(StructureMatrix<Point3D> position) {
        this.position = position;
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Barycentre position() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean supporteTexture() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Double[][][] getData() {
        return coordCube;
    }

    public StructureMatrix<Double> getCote() {
        return cote;
    }

    public void setCote(StructureMatrix<Double> cote) {
        this.cote = cote;
    }

    public TRIObject getTs() {
        return ts;
    }

    public void setTs(TRIObject ts) {
        this.ts = ts;
    }

    public static Double[][][] getCoordCube() {
        return coordCube;
    }

    public static void setCoordCube(Double[][][] coordCube) {
        Cube.coordCube = coordCube;
    }


}
