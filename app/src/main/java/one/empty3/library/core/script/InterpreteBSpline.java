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

/*

 Vous êtes libre de :

 */
package one.empty3.library.core.script;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.BSpline;

import java.util.ArrayList;
import one.empty3.library.StructureMatrix;


public class InterpreteBSpline implements Interprete {

    private String repertoire;
    private int pos = 0;
    private int numPoints = 0;

    public InterpreteConstants constant() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getPosition() {
        return pos;
    }

    public Object interprete(String text, int pos) throws InterpreteException {
        BSpline b = new BSpline();

        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern;
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();


        ArrayList<Double> T = new ArrayList<>();
        boolean ok = true;
        while (ok) {
            InterpretePoint3D ifa = new InterpretePoint3D();
            try {
                Point3D p = (Point3D) ifa.interprete(text, pos);
                pos = ifa.getPosition();

                b.add(p);
                if (ifa.getPosition() > pos) {
                    pos = ifa.getPosition();
                    numPoints++;
                }
            } catch (Exception ex) {
                ok = false;
            }
        }
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = ib.getPosition();


        ok = true;
        while (ok) {
            try {
                InterpreteDouble ida = new InterpreteDouble();
                Double d = (Double) ida.interprete(text, pos);
                pos = ida.getPosition();
                b.getT().add(1, d);
            } catch (Exception ex) {
                ok = false;
            }
        }


        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        this.pos = ib.getPosition();
        return b;
    }

    public void setConstant(InterpreteConstants c) {
        // TODO Auto-generated method stub

    }

    public void setRepertoire(String r) {
        this.repertoire = r;
    }
}
