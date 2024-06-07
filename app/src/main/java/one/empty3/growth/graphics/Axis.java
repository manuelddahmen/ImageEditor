/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.growth.graphics;

import one.empty3.library.Point3D;

public enum Axis {
    X("Axe X", Point3D.X),
    Y("Axe Y", Point3D.Y),
    Z("Axe Z", Point3D.Z);

    private String name = "";
    private Point3D axis = Point3D.O0;

    //Constructeur
    Axis(String name, Point3D axis) {
        this.name = name;
        this.axis = axis;
    }

    public Point3D getAxis() {
        return axis;
    }

    public String toString() {
        return name + " coordinates:" + getAxis().toString();
    }

}
