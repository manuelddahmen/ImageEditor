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

import one.empty3.library.Point3D;
import one.empty3.library.utils.ConsoleUtils;

import java.io.IOException;

public class TestRayTracerMain extends RtRaytracer {
    public static void main(String[] args) {
        RtScene myScene = new RtScene();

        // Notre camera
        RtTargetCamera myCamera = null;
        Point3D myCameraPos = new Point3D(0.0d, 0.0d, -5.0d);    // Position de la camera
        Point3D myCameraLookAt = new Point3D(0.0d, 0.0d, 10.0d);    // Position du point regard�
        Point3D myCameraUpVec = new Point3D(0.0d, 1.0d, 0.0d);    // Vecteur haut de la cam�ra

        // Une sphere
        RtSphere mySphere = null;
        Point3D mySpherePos = new Point3D(0.0d, 0.0d, 9.0d);        // Position de la sphere
        double mySphereRadius = 4f;                // Rayon de la sphere

        // Un plan
        RtPlane myPlane = null;
        Point3D myPlanePos = new Point3D(0.0d, 0.0d, 5.0d);
        Point3D myPlaneNormal = new Point3D(0.0d, 0.0d, 1.0d);

        // Une premiere lumiere (rouge)
        RtPointLight myLight;
        Point3D myLightPos = new Point3D(10.0d, 5.0d, 6.0d);
        RtColor myLightDiffuseColor = new RtColor(1.0d, 1.0d, 0.5d);
        RtColor myLightSpecularColor = new RtColor(1.0d, 1.0d, 0.5d);
        RtColor myLightColor = new RtColor(1.0d, 1.0d, 0.0d);

        // Une deuxieme lumiere (bleue)
        RtPointLight myLight1;
        Point3D myLight1Pos = new Point3D(-10.0d, 5.0d, 6.0d);
        RtColor myLight1DiffuseColor = new RtColor(0.7f, 0.7f, 0.7d);
        RtColor myLight1SpecularColor = new RtColor(0.7f, 0.7d, 0.7d);
        RtColor myLight1Color = new RtColor(1.0d, 1.0d, 1.0d);

        // Deux materiaux
        RtMatiere myMaterial;
        RtMatiere myMaterial1;


        myCamera = new RtParameterCamera(myCameraPos, myCameraLookAt, myCameraUpVec);
        mySphere = new RtSphere(mySpherePos, mySphereRadius);
        myPlane = new RtPlane(myPlanePos, myPlaneNormal);
        myLight = new RtPointLight(myLightPos, myLightDiffuseColor, myLightSpecularColor, myLightColor);
        myLight1 = new RtPointLight(myLight1Pos, myLight1DiffuseColor, myLight1SpecularColor, myLight1Color);
        //myLight.setIntensite(100);
        //myLight1.setIntensite(100);
        myMaterial = new RtMatiere("myMaterial", new RtColor(1.0d, 0.0d, 0.0d), new RtColor(1.0d, 0.0d, 0.0d), new RtColor(1.0d, 0.0d, 0.0d), new RtColor(1.0d, 0.0d, 0.0d), 1.0d, 1.0d);
        myMaterial1 = new RtMatiere("myMaterial1", new RtColor(0.0d, 1.0d, 1.0d), new RtColor(0.7d, 1.0d, 0.7d), new RtColor(0.0d, 1.0d, 1.0d), new RtColor(1.0d, 1.0d, 1.0d), 1.0d, 1.0d);

        // On assigne les materiaux a nos objets
        mySphere.setMaterial(myMaterial);
        myPlane.setMaterial(myMaterial1);

        // On ajoute les
        // elements a notre scene
        myScene.addCamera(myCamera);
        myScene.addObject(mySphere);
        myScene.addObject(myPlane);
        myScene.addLight(myLight);
        myScene.addLight(myLight1);
        myScene.addMaterial(myMaterial);
        myScene.addMaterial(myMaterial1);
        myScene.setActiveCamera(0);
        // On lance le rendu
        try {
            Render(myScene, 1920, 1080, "tests-results/raytracer-base" + ConsoleUtils.currentDate());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

}