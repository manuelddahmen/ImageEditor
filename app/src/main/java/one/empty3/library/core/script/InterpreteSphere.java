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
package one.empty3.library.core.script;

import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.Point3D;
import one.empty3.library.TextureImg;
import one.empty3.library.core.tribase.TRISphere;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 */
public class InterpreteSphere implements Interprete {

    private String repertoire;

    private int position;


    public InterpreteConstants constant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public int getPosition() {
        return position;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Object interprete(String text, int pos) throws InterpreteException {
        InterpretesBase base = new InterpretesBase();
        InterpretePoint3D point3D = new InterpretePoint3D();
        InterpreteNomFichier nomFichier = new InterpreteNomFichier();
        ArrayList<Integer> pattern = new ArrayList<Integer>();

        pattern.add(base.BLANK);
        pattern.add(base.LEFTPARENTHESIS);
        pattern.add(base.BLANK);
        base.compile(pattern);
        base.read(text, pos);
        pos = base.getPosition();

        Point3D centre = null;
        centre = (Point3D) point3D.interprete(text, pos);
        pos = point3D.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(base.BLANK);
        base.compile(pattern);
        base.read(text, pos);
        pos = base.getPosition();

        File file = null;
        file = (File) nomFichier.interprete(text, pos);
        pos = nomFichier.getPosition();

        pattern = new ArrayList<Integer>();
        pattern.add(base.BLANK);
        pattern.add(base.RIGHTPARENTHESIS);
        pattern.add(base.BLANK);
        base.compile(pattern);
        base.read(text, pos);
        pos = base.getPosition();

        this.position = pos;

        TRISphere sphere = new TRISphere(centre, pos);
        sphere.texture(
                new TextureImg(new ECBufferedImage(Objects.requireNonNull(ImageIO.read(file)).bitmap)));

        return sphere;
    }


    public void setConstant(InterpreteConstants c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
