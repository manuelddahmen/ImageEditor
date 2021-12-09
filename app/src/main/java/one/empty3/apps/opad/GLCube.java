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

package one.empty3.apps.opad;


import com.jogamp.opengl.GL2;


public class GLCube {


    public static void draw(GL2 gl){

        final float DEMI_TAILLE_CUBE = 50f;


        // On s'apprete a dessiner des quadrilateres

        // Chaque groupe de 4 vertex definit 1 quadrilatere

        gl.glBegin(GL2.GL_QUADS);


        // les prochains vertex seront bleu

        gl.glColor3f(0f, 0f, 1f);


        // le quadrilatere de devant

        gl.glVertex3f(-DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);


        // les prochains vertex seront verts

        gl.glColor3f(0f, 1f, 0f);


        // le quadrilatere de derriere

        gl.glVertex3f(-DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);


        // les prochains vertex seront rouges

        gl.glColor3f(1f, 0f, 0f);


        // le quadrilatere de gauche

        gl.glVertex3f(-DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);


        // les prochains vertex seront oranges

        gl.glColor3f(1f, 0.5f, 0f);


        // le quadrilatere de droite

        gl.glVertex3f(+DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);


        // les prochains vertex seront blancs

        gl.glColor3f(1f, 1f, 1f);


        // le quadrilatere du haut

        gl.glVertex3f(-DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);


        // les prochains vertex seront jaunes

        gl.glColor3f(1f, 1f, 0f);


        // le quadrilatere de bas

        gl.glVertex3f(-DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);

        gl.glVertex3f(-DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE);

        gl.glVertex3f(+DEMI_TAILLE_CUBE, -DEMI_TAILLE_CUBE, +DEMI_TAILLE_CUBE);


        // On a fini

        gl.glEnd();

    }


}