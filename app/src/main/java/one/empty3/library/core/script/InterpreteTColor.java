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

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.ITexture;
import one.empty3.library.TextureCol;
import one.empty3.library.TextureImg;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterpreteTColor implements Interprete {

    public boolean success;
    private String repertoire;
    private int pos;

    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getPosition() {
        return pos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Object interprete(String text, int pos) throws InterpreteException {

        success = false;

        ITexture tc = null;

        InterpretesBase ib;
        ib = new InterpretesBase();
        ArrayList<Integer> pattern;
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);

        pos = ib.getPosition();

        try {
            InterpreteCouleur ic = new InterpreteCouleur();

            Color c = (Color) ic.interprete(text, pos);

            tc = new TextureCol(c);

            pos = ic.getPosition();

            success = true;
        } catch (InterpreteException ex) {
            try {
                InterpreteNomFichier inf = new InterpreteNomFichier();

                inf.setRepertoire(repertoire);
                File f = (File) inf.interprete(text, pos);

                ECBufferedImage bi = new ECBufferedImage(Objects.requireNonNull(ImageIO.read(f)).bitmap);

                tc = new TextureImg(bi);

                pos = inf.getPosition();
                /*
                 InterpretePGM iPGM = new InterpretePGM();
				
                 ECBufferedImage ec = (ECBufferedImage) iPGM.interprete(text, pos);
				
                 tc = new TextureCol(ec);
				
                 this.pos = inf.getPosition();
                 */
                success = true;
            } catch (InterpreteException ex3) {
                Logger.getLogger(InterpreteTColor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Interprete Error");
            } catch (Exception ex4) {
                Logger.getLogger(InterpreteTColor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error");
            }
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);

        pos = ib.getPosition();

        this.pos = pos;

        return tc;

    }

    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    public void setRepertoire(String r) {
        this.repertoire = r;
    }

}
