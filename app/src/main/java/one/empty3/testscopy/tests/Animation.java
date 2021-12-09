package one.empty3.testscopy.tests;
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

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.StructureMatrix;
import one.empty3.tests.Path;

import java.util.HashMap;

public class Animation extends Representable {
    private final HashMap<Class, MoveCollection> animations = new HashMap<>();

    public Animation(Class<? extends Representable> anime, MoveCollection moveCollection) {
        this.animations.put(anime, moveCollection);
    }

    public Representable anime(Representable item, double tAnim, double fps) {
        MoveCollection moves = animations.get(item.getClass());
        if(moves!=null)
            for (Move move : moves.getMoves()) {
                if (move.getO() instanceof Representable) {
                    Path path = ((Representable) move.getO()).getPath(move.getProperty());
                    if(path!=null ) {
                        double t0 = move.getTime1();
                        double t1 = move.getTime2();

                        if (tAnim >= t0 && tAnim <= t1) {
                            double pcMove = (t1-t0)/fps;
                        Point3D plus = Point3D.O0;
                        if (path.getPathElemType() == Representable.PATH_ELEM_STRUCTURE_MATRIX) {
                            if(path.getDeclaredProperty()==null ) {
                                System.out.println("Error structureMatrix==null");
                                continue;
                            }
                            if (path.getDeclaredProperty().getDim()==0) {
                                if(move.getMoved() instanceof Point3D)  {
                                    plus = ((Point3D) (move.getMoved())).mult(pcMove).plus( // MOVE TYPE
                                            (Point3D) ((StructureMatrix<Object>) path.getDeclaredProperty()).getElem()

                                                    );
                                }

                                ((StructureMatrix<Object>) path.getDeclaredProperty()).setElem(plus);
                            }
                            if (path.getDeclaredProperty().getDim()==1) {
                                if(move.getMoved() instanceof Point3D) {
                                    plus = ((Point3D) (move.getMoved())).mult(pcMove).plus( // MOVE TYPE
                                            (Point3D) ((StructureMatrix<Object>) path.getDeclaredProperty()).getElem(path.getIndexI())
                                    );
                                }

                                ((StructureMatrix<Object>) path.getDeclaredProperty()).setElem(plus, path.getIndexI());
                            }
                            if (path.getDeclaredProperty().getDim()==2) {
                                if(move.getMoved() instanceof Point3D) {
                                    plus = ((Point3D) (move.getMoved())).mult(pcMove).plus( // MOVE TYPE
                                            (Point3D) ((StructureMatrix<Object>) path.getDeclaredProperty()).getElem(path.getIndexI(), path.getIndexJ())
                                    );
                                }
                                ((StructureMatrix<Object>) path.getDeclaredProperty()).setElem(plus, path.getIndexI()
                                        , path.getIndexJ());
                            }
                        }
                        if (path.getPathElemType() == Representable.PATH_ELEM_DOUBLE_VALUES) {

                        }
                        if (path.getPathElemType() == Representable.PATH_ELEM_REPRESENTABLE) {

                        }
                    } else {
                        System.out.println("catched error... Cannot invoke \"one.empty3.tests.Path.getPathElemType()\" because \"path\" is null");
                    }
                }
            }
            }
        else
            System.out.println("Animation anime error moves == null");
        return item;
    }


}

