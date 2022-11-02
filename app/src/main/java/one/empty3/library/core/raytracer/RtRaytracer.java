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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.script.ImageIO2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javaAnd.awt.image.imageio.ImageIO;

public class RtRaytracer {
    public static double maxDistance = 999999.9f;            // La distance parcourue par le rayon avant de toucher la node


    /* [ Coeur du raytracer. L'algo du raytracing se trouve dans cette fonction, dont le r?le est de calculer ] */
    /* [ la couleur finale du pixel courant, en lui passant le rayon primaire ?mis.                           ] */
    public static RtColor rayTrace(RtScene scene, RtRay ray, int depth) {
        RtColor finalColor = new RtColor(0.0f, 0.0f, 0.0f, 0.0f);    // La couleur finale (noire au debut ... couleur de fond)
        double tmpDistance = maxDistance + 1;                    // Une distance temporaire
        double distance = tmpDistance;            // La distance parcourue par le rayon avant de toucher la node
        RtNode currentNode;
        // La node en cours de traitement
        RtNode closestNode = null;                // La node qui sera la plus proche
        RtIntersectInfo interInfo;                        // Les informations sur l'intersection
        RtIntersectInfo closestInterInfo = null;                // Les informations sur l'intersection de la node la plus proche


        // Eclairage
        boolean lightBlocked;        // Booleen qui nous permet de dire si le rayon de lumi?re est bloqu? sur son chemin ou non
        Point3D lightVec;            // Le vecteur allant de la source lumineuse vers le point d'intersection
        double lightToObjDist;        // La distance entre la source lumineuse et le point d'intersection
        double lightToInterDist;    // La distance entre la source lumineuse et le point d'intersection de la node courante
        RtRay lightRay = new RtRay();            // Le rayon lumineux
        RtIntersectInfo lightInterInfo = new RtIntersectInfo();        // Les informations sur l'intersection du rayon lumineux et d'une node

        interInfo = new RtIntersectInfo();                        // Les informations sur l'intersection
        // On parcoure toutes les nodes de notre scene (cameras, objets ...)
        for (int i = 0; i < scene.getNumNodes(); i++) {
            currentNode = scene.getNode(i);

            if (currentNode.intersectsNode(ray, interInfo)) {
                // On n'a pas besoin de comparer la longueur en elle meme (qui est la racine carr? de la somme des carr?s des coeeficients)
                // En evitant la racine carr? on obtient la meme comparaison, mais en une op?ration de moins (sqrt est tr?s gourmand).
                tmpDistance = interInfo.mIntersection.moins(ray.mVStart).norme();

                if (tmpDistance < distance) {
                    distance = tmpDistance;
                    closestNode = currentNode;
                    closestInterInfo = interInfo;
                }
            }
        }

        if (closestNode != null) {
            // On parcoure toute les sources lumineuses
            for (int i = 0; i < scene.getNumLights(); i++) {
                RtLight currentLight = scene.getLight(i);


                lightBlocked = false;

                // Calc the vec (normalized) going from the light to the intersection point
                lightVec = closestInterInfo.mIntersection.
                        moins(currentLight.getPosition());
                lightToObjDist = lightVec.norme();//??getMagnitude();
                lightVec = lightVec.norme1();

                lightRay.mVStart = currentLight.getPosition();
                lightRay.mVDir = lightVec;
                lightRay.distance = distance;
                // We go through all the objects to see if one
                // of them block the light coming to the dest object
                for (int j = 0; j < scene.getNumNodes(); j++) {
                    currentNode = scene.getNode(j);

                    // put away the case of the object itself
                    if (closestNode != closestInterInfo.mNode)
                        if (currentNode.intersectsNode(lightRay, lightInterInfo)) {
                            lightToInterDist = (lightInterInfo.mIntersection.moins(lightRay.mVStart).norme());///magnitude
                            if (lightToInterDist < lightToObjDist)
                                lightBlocked = true;

                        }
                }
                if (!lightBlocked)
                    finalColor = RtColor.add(finalColor, currentLight.getLightAt(closestInterInfo.mNormal, closestInterInfo.mIntersection, closestInterInfo.mMaterial));
                else
                    finalColor = RtColor.add(finalColor, new RtColor(0f, 0f, 0f, 0f));

            }
            // Clean non permanent material
        /*if (closestInterInfo.mMaterial.GetPermanency() == false)
            delete (closestInterInfo.mMaterial); closestInterInfo.mMaterial = NULL;
			*/
        }


        return finalColor; /*RtColor.normalizeColor()*/
    }


    /* [ Fonction de rendu. Parcoure tous les pixels de l'image, cr?e le rayon correpondant et lance le raytracing ] */
    /* [ avec ce rayon. Enregistre le rendu final dans un fichier image.                                           ] */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean Render(RtScene scene, int width, int height, String outputfilename) throws IOException {
        RtRay currentRay = new RtRay();            // Le rayon primaire ?mis courant (de l'oeil, ? travers un pixel, vers la sc?ne).
        Point3D vDir;                // Le vecteur directeur (unitaire) du rayon.
        PrintWriter mOutputFileRAW;    // Le fichier image destination (format RAW : rvbrvbrvbrvb....).
        RtColor tmpColor;            // La couleur finale du pixel courant.
        int tmpR;    // Les trois composantes de la couleur (Rouge Vert Bleu).
        int tmpG;
        int tmpB;
        int tmpA = 0;
        Bitmap bi2 = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        // On cree le fichier destination
        mOutputFileRAW = new PrintWriter(new FileOutputStream(new File(outputfilename + ".ppm")));
        mOutputFileRAW.println("P3");
        mOutputFileRAW.println("# Image genereted with Empty3 http://gitlab/Graphics3D/Empty3");
        mOutputFileRAW.println("" + width);
        mOutputFileRAW.println("" + height);
        mOutputFileRAW.println("" + 256);
        // On parcoure tous les pixels de l'image finale
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                // [---Creation du rayon ? emetrre---]
                // L'origine du rayon est la position de la camera
                currentRay.mVStart = scene.getActiveCamera().getPosition();

                // On calcule le veteur directeur gr?ce ? une m?thode de la classe RtCamera
                vDir = scene.getActiveCamera().calcDirVec(x, y, width, height);
                Point3D vDirX1 = scene.getActiveCamera().calcDirVec(x + 1, y, width, height);
                Point3D vDirX_1 = scene.getActiveCamera().calcDirVec(x - 1, y, width, height);
                Point3D vDirY1 = scene.getActiveCamera().calcDirVec(x, y + 1, width, height);
                Point3D vDirY_1 = scene.getActiveCamera().calcDirVec(x, y - 1, width, height);
                vDir.normalize();
                vDirX1.normalize();
                vDirX_1.normalize();
                vDirY1.normalize();
                vDirY_1.normalize();
                currentRay.mVDir = vDir;
                currentRay.mVDirX1 = vDir;
                currentRay.mVDirX_1 = vDir;
                currentRay.mVDirY1 = vDir;
                currentRay.mVDirY_1 = vDir;

                // On trace le rayon, et on recup?re la couleur finale du pixel
                tmpColor = rayTrace(scene, currentRay, 0);


                double zMin = maxDistance;
                Point3D choisi;
                choisi = Point3D.INFINI;
                for (Representable rep : scene.getRepresentables()) {
                    if (rep instanceof ParametricSurface) {
                        ParametricSurface surface = (ParametricSurface) rep;
                    }
                }


                if (zMin < currentRay.distance) {
                    tmpColor = new RtColor(Color.valueOf(choisi.getTexture().getColorAt(0.5, 0.5)));
                }

                // Affichage de notre "barre de progression" ;)
                if (x == 0 && y == 0.25f * height)
                    System.out.printf("25 percent completed !\n");

                if (x == 0 && y == 0.5f * height)
                    System.out.printf("50 percent completed !\n");

                if (x == 0 && y == 0.75f * height)
                    System.out.printf("75 percent completed !\n");

                if (x == 0 && y == height - 1)
                    System.out.printf("100 percent completed !\n");

                // On decompose la couleur dans les trois couleurs de base (Rouge Vert Bleu).
                //RtColor fc = RtColor.normalizeColor(tmpColor);
                tmpR = (int) (tmpColor.red() * 256);
                tmpG = (int) (tmpColor.green() * 256);
                tmpB = (int) (tmpColor.blue() * 256);
                tmpA = (int) (tmpColor.getAlpha() * 256);
                int elementCouleur = 0xFF000000 | ((tmpA << 24) | (tmpR << 16) | (tmpG << 8) | (tmpB << 0));
                bi2.setPixel(x, y, elementCouleur);

                // Et on ecrit finalement la couleur de ce pixel dans le fichier
                mOutputFileRAW.println(tmpR + " " + " " + tmpG + " " + tmpB + "\n");
            }

        System.out.print("+ppm");
        mOutputFileRAW.flush();
        mOutputFileRAW.close();

        File file = new File(outputfilename + ".jpg");
        System.out.print("+jpg: " + file.getAbsolutePath());
        ImageIO.write(bi2, "jpg", file);

        return true;
    }


}