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

package one.empty3.library.core.raytracer;

import one.empty3.library.*;

/*__
 * Created by manuel on 03-08-16.
 */
public abstract class RtLight extends RtNode {
    protected float mDiffuseColor;
    protected float mSpecularColor;
    protected Point3D mPosition = new Point3D();
    protected RtColor mColor;
    protected int mLightType;
    protected double mIntensite = 1;

    public RtLight(Point3D position, float diffuseColor, float specularColor, RtColor CColor, int type) {
        super(LIGHT, "LIGHT");
        assert (position != null);
        mPosition = position;
        mDiffuseColor = diffuseColor;
        mSpecularColor = specularColor;
        mColor = CColor;
        mLightType = type;
    }

    public RtLight(int light, String light1) {
        mLightType = light;
        mNodeType = LIGHT;
    }

    /*public RtLight(int light, String light1) {
        super(light, light1);
    }*/

    // get
    public Point3D getPosition() {
        return mPosition;
    }

    public RtColor getColor() {
        return mColor;
    }

    public int getType() {
        return mLightType;
    }

    // La méthode virtuel pure d'éclairage.
    public abstract RtColor getLightAt(Point3D normal, Point3D intersectionPoint, RtMatiere material);

    public void setIntensite(int intensite) {
        this.mIntensite = intensite;
    }
}
