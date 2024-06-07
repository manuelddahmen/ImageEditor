/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

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
import java.io.IOException;
import java.util.Objects;

public class InterpreteTexture implements Interprete {

    private String rep;
    private int position;

    public InterpreteConstants constant() {
        return null;
    }

    public int getPosition() {
        return position;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Object interprete(String text, int pos) throws InterpreteException {
        ITexture tc = null;

        boolean pass = false;
        try {
            InterpreteCouleur ic = new InterpreteCouleur();
            Color c = (Color) ic.interprete(text, pos);
            pos = ic.getPosition();
            pass = true;

            tc = new TextureCol(c);

        } catch (InterpreteException ex) {
        }
        if (!pass) {
            try {
                InterpreteNomFichier inf = new InterpreteNomFichier();
                inf.interprete(text, pos);
                pos = inf.getPosition();
                File f = (File) inf.interprete(text, pos);
                pos = inf.getPosition();
                pass = true;

                tc = new TextureImg(new ECBufferedImage(Objects.requireNonNull(ImageIO.read(f)).bitmap));
            } catch (InterpreteException ex) {
            }
        }

        this.position = pos;

        return tc;
    }

    public void setConstant(InterpreteConstants c) {

    }

    public void setRepertoire(String r) {
        this.setRep(r);

    }

}
