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
/*__
 *
 */
package one.empty3.library.core.script;

import android.graphics.Color;

import one.empty3.library.Point3D;
import one.empty3.library.core.extra.SimpleSphereAvecTexture;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author MANUEL DAHMEN
 *         <p>
 *         dev
 *         <p>
 *         21 oct. 2011
 */
public class InterpreteSimpleSphereTexture implements Interprete {

    private String repertoire;

    private int pos;
    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#interprete(java.lang.String,
     * int)
     */
    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#constant()
     */

    @Override
    public InterpreteConstants constant() {
        return null;
    }

    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#getPosition()
     */
    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public Object interprete(String text, int pos) throws InterpreteException {
        try {
            Point3D c;
            double r;
            Color col;

            InterpretesBase ib;
            InterpretePoint3D ip;
            InterpreteCouleur pc;
            ArrayList<Integer> patt;

            ib = new InterpretesBase();
            patt = new ArrayList<Integer>();
            patt.add(ib.BLANK);
            patt.add(ib.LEFTPARENTHESIS);
            patt.add(ib.BLANK);
            ib.compile(patt);
            ib.read(text, pos);
            pos = ib.getPosition();

            ip = new InterpretePoint3D();
            c = (Point3D) ip.interprete(text, pos);
            pos = ip.getPosition();

            ib = new InterpretesBase();
            patt = new ArrayList<Integer>();
            patt.add(ib.BLANK);
            patt.add(ib.DECIMAL);
            patt.add(ib.BLANK);
            ib.compile(patt);

            ib.read(text, pos);
            pos = ib.getPosition();
            r = (Double) ib.get().get(1);

            InterpreteNomFichier inf = new InterpreteNomFichier();

            inf.setRepertoire(repertoire);

            File f = (File) inf.interprete(text, pos);
            if (f == null) {
                throw new InterpreteException("Fichier non trouvé");
            }
            pos = inf.getPosition();

            ib = new InterpretesBase();
            patt = new ArrayList<Integer>();
            patt.add(ib.BLANK);
            patt.add(ib.RIGHTPARENTHESIS);
            patt.add(ib.BLANK);
            ib.compile(patt);

            ib.read(text, pos);
            pos = ib.getPosition();

            this.pos = pos;
            SimpleSphereAvecTexture s = null;
            s = new SimpleSphereAvecTexture(c, r, Color.WHITE, Objects.requireNonNull(ImageIO.read(f)).bitmap);
            s.fichier(f.getName());
            return s;
        } catch (InterpreteException ex) {
            Logger.getLogger(InterpreteSimpleSphereTexture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     * (non-Javadoc) @see
     * be.ibiiztera.md.pmatrix.pushmatrix.scripts.Interprete#setConstant(be.ibiiztera.md.pmatrix.pushmatrix.scripts.InterpreteConstants)
     */
    @Override
    public void setConstant(InterpreteConstants c) {
    }

    @Override
    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
