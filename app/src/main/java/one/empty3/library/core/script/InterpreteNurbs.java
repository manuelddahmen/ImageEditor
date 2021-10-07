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
package one.empty3.library.core.script;

import one.empty3.library.Point3D;
import one.empty3.library.TextureCol;
import one.empty3.library.core.nurbs.NurbsSurface;

import java.util.ArrayList;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class InterpreteNurbs implements Interprete {

    private int position;

    public InterpreteConstants constant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getPosition() {
        return position;

    }

    public Object interprete(String text, int pos) throws InterpreteException {
        NurbsSurface nurbs = new NurbsSurface();

        /*__
         * ( m n (
         */
        InterpretesBase ib;
        ArrayList<Integer> pattern;

        pattern = new ArrayList<Integer>();
        ib = new InterpretesBase();

        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        pattern.add(ib.INTEGER);
        pattern.add(ib.INTEGER);
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);

        ArrayList<Object> o = ib.read(text, pos);
        pos = ib.getPosition();

        Integer m = (Integer) o.get(3);
        Integer n = (Integer) o.get(4);

        /*__
         * *
         * Tableau de points3D et poids;
         */
        Point3D[][] points = new Point3D[m][n];
        double[][] poids = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                InterpretePoint3D ip = new InterpretePoint3D();

                Point3D p = (Point3D) ip.interprete(text, pos);
                pos = ip.getPosition();

                pattern = new ArrayList<Integer>();
                ib = new InterpretesBase();

                pattern.add(ib.BLANK);
                pattern.add(ib.DECIMAL);
                pattern.add(ib.BLANK);

                ib.compile(pattern);

                Double poi = (Double) ib.read(text, pos).get(1);
                pos = ib.getPosition();

                poids[i][j] = poi;

                points[i][j] = p;
            }
        }
        InterpreteTColor itc = new InterpreteTColor();

        TextureCol tc = (TextureCol) itc.interprete(text, pos);
        pos = itc.getPosition();

        nurbs.setMaillage(points, poids);

        /*__
         * )
         */
        pattern = new ArrayList<Integer>();
        ib = new InterpretesBase();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);

        ib.read(text, pos);
        pos = ib.getPosition();

        /*__
         * i j (
         */
        //nurbs.texture(tc);
        pattern = new ArrayList<Integer>();
        ib = new InterpretesBase();
        pattern.add(ib.BLANK);
        pattern.add(ib.INTEGER);
        pattern.add(ib.BLANK);
        pattern.add(ib.INTEGER);
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);
        ArrayList<Object> read = ib.read(text, pos);
        pos = ib.getPosition();

        Integer k = (Integer) read.get(1);
        Integer l = (Integer) read.get(3);

        double[][] T = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {

                pattern = new ArrayList<Integer>();
                ib = new InterpretesBase();

                pattern.add(ib.BLANK);
                pattern.add(ib.DECIMAL);
                pattern.add(ib.BLANK);

                ib.compile(pattern);

                Double Tij = (Double) ib.read(text, pos).get(1);
                pos = ib.getPosition();

                T[k][l] = Tij;

            }
        }

        nurbs.setReseauFonction(T);

        /*__
         * )
         */
        pattern = new ArrayList<Integer>();
        ib = new InterpretesBase();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);

        ib.read(text, pos);
        pos = ib.getPosition();

        /*__
         * *
         * )
         */
        pattern = new ArrayList<Integer>();
        ib = new InterpretesBase();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);

        ib.compile(pattern);

        ib.read(text, pos);
        pos = ib.getPosition();

        nurbs.creerNurbs();

        this.position = pos;

        return nurbs;
    }

    public void setConstant(InterpreteConstants c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setRepertoire(String r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
