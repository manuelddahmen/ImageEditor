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

public class RtPointLight extends RtLight {
    Point3D lightVector;
    RtColor lightDiffuseColor;
    RtColor lightSpecularColor;
    RtColor lightColor;

    public RtPointLight(Point3D myLightPos, RtColor myLightDiffuseColor, RtColor myLightSpecularColor, RtColor myLightColor) {
        super(LIGHT, "LIGHT");
        mPosition = myLightPos;
        lightDiffuseColor = myLightDiffuseColor;
        mColor = myLightDiffuseColor;
        lightSpecularColor = myLightSpecularColor;
        lightColor = myLightColor;
    }

    @Override
    public RtColor getLightAt(Point3D normal, Point3D intersectionPoint, RtMatiere material) {
        double angle;
        RtColor finalColor;

        lightVector = intersectionPoint.moins(getPosition());

        Point3D lightVector2 = lightVector.norme1();

        angle = normal.prodScalaire(lightVector2.mult(1 / Math.sqrt(lightVector.norme())));

        //if (angle <= 0)
        //  finalColor = new RtColor(0.0f, 0.0f, 0.0f);

        //else
        finalColor = RtColor.mult(getColor(), (RtColor.mult(material.GetDiffuse(), Math.abs(angle))));

        return RtColor.mult(finalColor, (float) mIntensite);
    }

    @Override
    public boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo) {
        return false;
    }
}